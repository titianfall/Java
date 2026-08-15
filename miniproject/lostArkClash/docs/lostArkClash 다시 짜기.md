# lostArkClash 를 처음부터 다시 짜기

> 근거: `src/original` 복원본 코드와 보고서 5장 결론(「상태 플래그가 과도하게 증가한 구조」, 「EDT에서 처리되어야 한다는 점을 직접 경험」)
>
> 📚 **Swing 문법 자체**는 [스윙 기초 레퍼런스 01~08](../src/practice/docs/README.md) 에서 찾아본다. 이 문서는 **"이걸 어떤 구조로 짜지?"** 만 다룬다.
>
> 관련 수업 정리: [09주차 스윙 기초](../../../college/9week/README.md) · [10주차 이벤트 처리](../../../college/10week/README.md) · [11주차 스윙 컴포넌트](../../../college/11week/README.md) · [12주차 그래픽](../../../college/12week/README.md) · [08주차 입출력](../../../college/8week/README.md) · [07주차 컬렉션](../../../college/7week/README.md)

이 문서는 "무엇을 알아야 이 게임을 다시 짤 수 있는가"를 정리한 것이다.
설명마다 **원본이 실제로 그 지점에서 뭘 했고 어디서 무너졌는지**를 붙였다. 원본은 [`src/original`](../src/original) 에 그대로 있으니 열어 놓고 비교하면서 읽으면 좋다.

---

## 0. 읽는 순서

빡세게 다 읽을 필요는 없다. 막힌 곳부터 봐도 된다. 다만 **3장(EDT)과 6장(스레드)은 먼저** 읽는 걸 권한다. 원본이 가장 크게 고생한 곳이고, 여기 설계를 잘못 잡으면 나중에 전부 뜯어야 한다.

| 순서 | 장 | 왜 먼저인가 |
|------|-----|-------------|
| 1 | [3장 EDT](#3-edt--이-문서에서-제일-중요한-장) | 이걸 모르면 "가끔 화면이 안 그려지는" 버그를 평생 못 잡는다 |
| 2 | [6장 스레드](#6-스레드--원본이-가장-크게-넘어진-곳) | 원본이 스레드 25개를 쓴 이유와, 안 써도 되는 이유 |
| 3 | [12장 설계](#12-설계--원본의-아쉬움을-어떻게-넘을까) | 코드 쓰기 전에 정해야 하는 것 |
| 4 | [13장 로드맵](#13-구현-로드맵) | 실제로 어디서부터 손댈지 |
| 나머지 | 2·4·5·7~11장 | 필요할 때 찾아보기 |

---

## 1. 전체 그림 — 이 게임이 요구하는 기술 지도

```
                        ┌─────────────────────────────┐
                        │        JFrame (창)          │
                        │  메뉴바 · 툴바 · CardLayout │
                        └──────────────┬──────────────┘
             화면 전환                 │
   ┌───────────┬───────────┬───────────┼───────────┬─────────────┐
   │  시작화면  │ 단어편집  │  게임화면  │  격돌화면 │   랭킹화면   │
   └───────────┴─────┬─────┴─────┬─────┴─────┬─────┴──────┬──────┘
                     │           │           │            │
              파일 입출력    커스텀 드로잉  키 이벤트   정렬·파일
              (word.txt)   (paintComponent) (KeyListener)(ranking.txt)
                                 │
                          시간에 따른 변화
                     (스레드 or Swing Timer) ← 여기가 핵심
```

필요한 지식을 묶으면 6덩어리다.

| 덩어리 | 게임에서 쓰이는 곳 | 이 문서 |
|--------|-------------------|---------|
| 컨테이너·배치 | 화면 5개 전환, 좌우/상하 분할 | 2장 |
| **EDT (이벤트 디스패치 스레드)** | 전부. 모든 UI 갱신 | **3장** |
| 커스텀 드로잉 | HP바, 게이지, 격돌 원, 배경 | 4장 |
| 이벤트·포커스 | 단어 입력, 아이템 클릭, 격돌 키 | 5장 |
| **시간 흐름 제어** | 단어 낙하, 게이지 충전, 제한시간 | **6·7장** |
| 파일·컬렉션 | 단어 목록, 랭킹 TOP 10 | 8·9장 |

---

## 2. 컨테이너와 배치

### 2.1 계층 구조

Swing은 **컨테이너 안에 컨테이너**를 넣는 구조다. 원본의 계층은 이렇다.

```
JFrame
└── contentPane (JPanel, BorderLayout)
    ├── NORTH  : JToolBar
    └── CENTER : container (JPanel, CardLayout)   ← 화면 5장을 겹쳐 두고 하나만 보여줌
        ├── "START"       StartPanel
        ├── "EDIT"        EditPanel
        ├── "GAME"        GameContainerPanel
        │                 └── JSplitPane(좌우)
        │                     ├── 좌 : GamePanel (BorderLayout)
        │                     │        ├── CENTER : GroundPanel  ← 단어가 떨어지는 곳
        │                     │        └── SOUTH  : InputPanel   ← 입력창
        │                     └── 우 : JSplitPane(상하)
        │                              ├── 상 : ScorePanel
        │                              └── 하 : BattleItemPanel
        ├── "CLASH"       ClashPanel
        └── "LEADERBOARD" LeaderboardPanel
```

### 2.2 배치관리자

| 배치관리자 | 규칙 | 게임에서 |
|-----------|------|---------|
| `BorderLayout` | NORTH/SOUTH/EAST/WEST/CENTER 5칸. CENTER가 남는 공간을 다 먹는다 | 툴바 위 + 게임화면 가운데 |
| `CardLayout` | 여러 컴포넌트를 겹쳐 두고 `show(container, "이름")` 으로 하나만 | 화면 5장 전환 |
| `JSplitPane` | 둘로 쪼갠다. `setDividerLocation(px)`, `setEnabled(false)` 로 고정 | 게임 / 점수+아이템 |
| `FlowLayout` | 넣은 순서대로 가로로. JPanel 기본값 | 입력창 하나 놓을 때 |
| `null` (절대배치) | 내가 좌표를 직접 지정 | 시작화면, 점수판 등 |

### 2.3 절대배치 `setLayout(null)`

원본은 화면 대부분을 절대배치로 짰다.

```java
setLayout(null);                       // 배치관리자 제거
title.setSize(500, 200);               // 크기를 내가 정하고
title.setLocation(250, 80);            // 위치도 내가 정한다
add(title);                            // 둘 다 안 하면 크기 0 → 화면에 안 보임
// setBounds(250, 80, 500, 200) 로 한 번에 써도 된다
```

⚠️ **절대배치의 대가**: 창 크기가 바뀌어도 안 따라온다. 원본은 2560 폭 모니터에서 짜서 FHD(1920)에서는 UI가 화면 밖으로 나갔다. 복원본에서 `GameContainerPanel`, `EditPanel` 두 곳만 화면 폭 기준으로 고쳤다(`// [해상도 호환]`).

💡 다시 짤 때 선택지:
- **A. 그냥 절대배치 + 창 크기 고정** (`setResizable(false)`, `setSize(1280, 800)`) — 제일 단순. 학습용이면 이걸 추천.
- **B. 배치관리자 조합** — 정석이지만 픽셀 단위로 맞추기 번거롭다.
- **C. 논리 해상도 + 스케일링** — `Graphics2D.scale()` 로 전체를 배율 조정. 재밌지만 마우스 좌표도 같이 변환해야 해서 일이 커진다.

### 2.4 ⚠️ `getWidth()` 는 처음에 0이다

```java
public class LeaderboardPanel extends JPanel {
    private int rightX = getWidth() - 520;   // ← 원본 코드. 이 시점 getWidth()==0 이라 -520
}
```

필드 초기화는 **생성자 시점**에 돈다. 그때는 아직 레이아웃이 계산되기 전이라 `getWidth()` 가 0이다.
원본도 이 필드는 결국 안 쓰고 `paintComponent()` 안에서 지역변수로 다시 계산했다 — 그게 맞는 방법이다.

**화면 크기에 의존하는 값은 `paintComponent()` 나 `componentResized` 에서 계산하라.**

---

## 3. EDT — 이 문서에서 제일 중요한 장

### 3.1 EDT가 뭔가

자바 GUI 프로그램은 `main` 스레드 말고 **EDT(Event Dispatch Thread)** 라는 스레드가 하나 더 돈다.
이 스레드는 큐에서 할 일을 하나씩 꺼내 처리한다.

```
[이벤트 큐]  버튼클릭 → 키입력 → 화면그리기 → repaint요청 → ...
                 │
                 ▼
        ┌──────────────────┐
        │       EDT        │  ← 이 스레드 하나가 전부 순서대로 처리
        └──────────────────┘
   · 모든 리스너(actionPerformed, keyPressed…)가 여기서 호출된다
   · 모든 paintComponent 가 여기서 호출된다
   · Swing Timer 의 콜백도 여기서 호출된다
```

### 3.2 규칙 두 줄

> **① Swing 컴포넌트는 EDT에서만 건드린다.**
> **② EDT를 오래 붙잡지 않는다.**

**①을 어기면**: Swing 컴포넌트는 thread-safe 하지 않다. 다른 스레드에서 `setText()`, `setLocation()` 을 부르면 화면이 안 갱신되거나, 절반만 그려지거나, 엉뚱한 좌표에 그려진다. **에러가 안 나고 조용히 이상해지는 게 최악이다.** 보고서 5장에서 말한 그 문제가 이거다.

**②를 어기면**: EDT가 큐를 못 비우니 창 전체가 얼어붙는다. 버튼도 안 눌리고 화면도 안 그려진다.
→ **리스너 안에서 `Thread.sleep()`, 무거운 파일 읽기, 무한루프 금지.**

### 3.3 EDT로 넘기는 법

```java
// 다른 스레드에서 UI를 건드려야 할 때 — 큐에 넣고 바로 돌아온다 (비동기)
SwingUtilities.invokeLater(() -> {
    label.setText("갱신");
});

// 끝날 때까지 기다려야 할 때 (동기). EDT 안에서 부르면 예외가 나니 주의
SwingUtilities.invokeAndWait(() -> { ... });

// 지금 내가 EDT인가?
if (SwingUtilities.isEventDispatchThread()) { ... }
```

원본이 제대로 한 부분:

```java
// MakeWordThread(일반 스레드) 안에서 암흑 모드를 켤 때
// EDT에 다크모드를 실행을 요청한다. 절대 mwThread가 실행하면 안된다.
javax.swing.SwingUtilities.invokeLater(() -> {
    darkModeController.start();
});
```

### 3.4 예외: `repaint()` 와 `revalidate()`

이 둘은 **아무 스레드에서 불러도 안전하다.** "다시 그려 달라"는 요청만 큐에 넣고 바로 돌아오기 때문이다. 실제 `paintComponent()` 는 나중에 EDT가 부른다.

```java
hp -= damage;   // 데이터만 바꾸고
repaint();      // 그리기는 EDT에게 맡긴다  ← 이 패턴이 안전하다
```

💡 **그래서 커스텀 드로잉이 유리하다.** `JLabel.setText()` 를 다른 스레드에서 부르는 건 위험하지만, "필드 값만 바꾸고 `repaint()`" 는 안전하다. 원본의 `HealthPoint`, `MyLabel`, `DarkGage` 가 이 구조다.

### 3.5 ⚠️ 원본이 규칙 ①을 어긴 곳

```java
// FallingThread(일반 스레드)가 직접 라벨을 움직인다
public boolean move(JLabel la, int speed) {
    y += speed;
    la.setLocation(x, y);   // ← EDT 아닌 곳에서 Swing 컴포넌트 조작
}
```

단어 20개가 각자 자기 스레드에서 `setLocation()` 을 부른다. 대부분 잘 돌아가지만 원리적으로는 위험하고, 원본이 겪은 "화면이 정상적으로 갱신되지 않는" 증상의 원인 중 하나다.

**다시 짤 때는 이렇게 안 만들면 된다** → 6장·7장.

---

## 4. 커스텀 드로잉

### 4.1 `paintComponent` 규칙

```java
class HpBar extends JComponent {
    private int hp = 100, maxHp = 100;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);            // ① 반드시 먼저. 배경 정리를 해 준다
        Graphics2D g2 = (Graphics2D) g;     // ② 두께·안티앨리어싱을 쓰려면 캐스팅
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        int w = (int) (getWidth() * (hp / (double) maxHp));
        g2.setColor(Color.RED);
        g2.fillRect(0, 0, w, getHeight());
    }

    public void setHp(int hp) {
        this.hp = hp;
        repaint();        // ③ paintComponent 를 직접 부르지 않는다. repaint() 로 요청
    }
}
```

| 규칙 | 이유 |
|------|------|
| `super.paintComponent(g)` 먼저 | 안 하면 이전에 그려진 게 남아 잔상이 생긴다 |
| `paintComponent()` 직접 호출 금지 | `Graphics` 객체는 시스템이 만들어 준다. `repaint()` 로 요청만 |
| 그리기 안에서 무거운 일 금지 | EDT가 여기서 도니까. 이미지 로딩은 미리 해 두고 그리기만 |
| 상태는 필드에, 그리기는 필드를 읽기만 | 그려야 할 값을 `paintComponent` 안에서 계산·변경하면 꼬인다 |

### 4.2 `JLabel` vs `JComponent` 상속

원본은 HP바와 게이지를 **`JLabel` 을 상속해서** 만들었다.

```java
class HealthPoint extends JLabel { ... }   // 원본
class DarkGage extends JComponent { ... }  // 원본 (이쪽이 더 맞다)
```

`JLabel` 을 쓰면 글자·아이콘 기능이 딸려 오는데 안 쓸 거면 군더더기다. **그릴 것만 있으면 `JComponent` 상속이 정석.**

⚠️ `JLabel`/`JPanel` 에 배경색을 칠하려면 `setOpaque(true)` 를 해야 한다. 안 하면 `setBackground()` 가 무시된다. 원본 곳곳에 이 호출이 있는 이유다.

### 4.3 자주 쓰는 Graphics API

```java
g.drawImage(img, x, y, w, h, this);            // 이미지 (마지막은 ImageObserver)
g.fillRect(x, y, w, h);   g.drawRect(...);     // 사각형
g.fillOval(x, y, w, h);   g.drawOval(...);     // 원/타원 (x,y는 좌상단!)
g.drawArc(x, y, w, h, startAngle, arcAngle);   // 원호. 각도는 도(°), 반시계가 +
g.drawString("텍스트", x, y);                   // y는 baseline(글자 아랫선)

g2.setStroke(new BasicStroke(6));               // 선 두께
g2.setFont(new Font("Arial", Font.BOLD, 40));
int w = g2.getFontMetrics().stringWidth(s);     // 글자 폭 → 가운데 정렬에 필요
```

💡 원을 **중심 기준**으로 그리고 싶으면 `drawOval(cx - r, cy - r, r*2, r*2)`. 격돌의 줄어드는 원이 이 계산이다.

💡 Swing은 기본적으로 **더블 버퍼링**이 켜져 있어서 깜빡임 걱정은 안 해도 된다.

---

## 5. 이벤트 처리와 포커스

### 5.1 리스너 종류

| 인터페이스/어댑터 | 언제 | 게임에서 |
|------------------|------|---------|
| `ActionListener` | 버튼 클릭, 텍스트필드에서 **Enter** | 시작/정지 버튼, 단어 입력 확정 |
| `KeyListener` / `KeyAdapter` | 키를 누를 때 | 격돌, 암흑 패턴 |
| `MouseListener` / `MouseAdapter` | 클릭 | 아이템 사용, 난이도 아이콘 |

**어댑터(`XxxAdapter`)** 는 인터페이스의 메소드를 빈 몸통으로 미리 구현해 둔 추상 클래스다. `KeyListener` 를 직접 구현하면 `keyPressed`/`keyReleased`/`keyTyped` 3개를 다 써야 하지만, `KeyAdapter` 를 상속하면 필요한 것만 오버라이드하면 된다.

```java
// 익명 클래스
btn.addActionListener(new ActionListener() {
    @Override public void actionPerformed(ActionEvent e) { start(); }
});

// 람다 — 메소드가 하나뿐인 인터페이스(함수형)만 가능. KeyAdapter는 클래스라 불가
btn.addActionListener(e -> start());
```

### 5.2 텍스트필드에서 Enter 받기

원본의 단어 입력이 이 방식이다. `JTextField` 에 `ActionListener` 를 달면 **Enter 칠 때** 호출된다.

```java
inputField.addActionListener(e -> {
    JTextField tf = (JTextField) e.getSource();   // 이벤트를 일으킨 컴포넌트
    String text = tf.getText().trim();            // trim() 안 하면 공백 때문에 안 맞는다
    if (text.isEmpty()) return;
    // ... 단어 맞추기 ...
    tf.setText("");                                // 다음 입력을 위해 비우기
});
```

### 5.3 ⚠️ 포커스 — 키 이벤트가 안 오는 이유 1순위

**키 이벤트는 "포커스를 가진 컴포넌트"에게만 간다.** `JPanel` 은 기본적으로 포커스를 못 받는다.

```java
panel.setFocusable(true);          // ① 포커스를 받을 수 있게
panel.requestFocusInWindow();      // ② 실제로 달라고 요청
```

⚠️ **화면이 보이기 전에 요청하면 실패한다.** CardLayout으로 막 전환한 직후가 딱 그 상황이라, 한 박자 미뤄야 한다.

```java
// 카드 전환 직후 → EDT 큐 뒤로 미룬다
SwingUtilities.invokeLater(() -> requestFocusInWindow());
```

복원본 `ClashPanel.startClashSequence()` 가 이 처리를 한다.

💡 **더 나은 방법: Key Binding.** 포커스 문제를 아예 피할 수 있다.

```java
panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
     .put(KeyStroke.getKeyStroke("SPACE"), "jump");
panel.getActionMap().put("jump", new AbstractAction() {
    @Override public void actionPerformed(ActionEvent e) { ... }
});
```
`WHEN_IN_FOCUSED_WINDOW` 는 **창만 활성화돼 있으면** 동작한다. 격돌처럼 "화면 전환 직후 바로 키를 받아야" 하는 곳에 잘 맞는다.

### 5.4 ⚠️ 키 이벤트 함정

```java
char c = e.getKeyChar();     // 문자. Shift 조합 등 반영. 화살표키는 의미 없는 값
int  code = e.getKeyCode();  // 키 자체(VK_LEFT, VK_A…). keyPressed에서 신뢰할 수 있는 쪽
```

- 키를 **누르고 있으면** `keyPressed` 가 반복 발생한다(auto-repeat). 원본은 `keyLocked` 플래그로 막았다.
- `keyTyped` 는 문자 입력에만 오고 화살표·F키에는 안 온다.
- `KeyListener` 는 등록을 **중복**하면 한 번 눌러도 여러 번 처리된다. 원본이 `removeKeyListener()` 를 먼저 부르는 이유다.

---

## 6. 스레드 — 원본이 가장 크게 넘어진 곳

### 6.1 원본이 만든 스레드

| 스레드 | 개수 | 하는 일 |
|--------|------|---------|
| `MakeWordThread` | 1 | 2초마다 단어 라벨 하나 생성 |
| `FallingThread` | **최대 20** | 라벨 하나를 0.1초마다 아래로 이동 |
| `HealthThread` | 2 | 유저·보스 HP 주기 처리 (실은 아무것도 안 함) |
| `fillThread` | 1 | 에스더 게이지 충전 |
| `DarkGage` (Runnable) | 1 | 암흑 게이지 충전 |
| | **최대 25개** | |

각 스레드는 `running` / `pause` 플래그 2개씩 + `startThread` / `pauseThread` / `resumeThread` / `stopThread` 4개씩을 갖는다.
**같은 코드가 5번 복붙되어 있다.** 보고서 5장의 "상태 플래그가 과도하게 증가한 구조"가 이 얘기다.

### 6.2 스레드 상태

```
   new Thread()          start()              스케줄러가 선택
      NEW      ────────► RUNNABLE ◄──────────────────┐
                            │                        │
       sleep(ms) / wait(ms) │                        │ 시간 만료 / notify()
                            ▼                        │
                     TIMED_WAITING ──────────────────┤
                            │                        │
                     wait() │                        │
                            ▼                        │
                        WAITING ────────────────────-┘
                            │
              run() 끝 / 예외│
                            ▼
                        TERMINATED   ← 한 번 죽으면 다시 start() 못 한다
```

⚠️ **끝난 스레드는 재사용 불가.** `IllegalThreadStateException` 이 난다.
원본이 `exit()` 에서 `mwThread = null` 로 참조를 지우고 `start()` 에서 새로 만드는 이유다.

### 6.3 `synchronized` / `wait` / `notify`

```java
synchronized (lock) {          // lock 객체의 자물쇠를 잡는다. 다른 스레드는 대기
    while (조건이 아직 아님) {  // ← if 가 아니라 while !!
        lock.wait();           // 자물쇠를 놓고 잠든다
    }
    // 조건 만족. 일한다
}

synchronized (lock) {
    조건 = true;
    lock.notifyAll();          // 자고 있는 스레드를 깨운다
}
```

| 실수 | 결과 |
|------|------|
| `synchronized` 밖에서 `wait()`/`notify()` | `IllegalMonitorStateException` |
| **`if (조건) wait()`** | 가짜 깨어남(spurious wakeup)·경합 시 조건이 아닌데도 진행 |
| `notify()` 를 `notifyAll()` 대신 | 여럿 대기 중이면 엉뚱한 하나만 깨어 나머지가 영원히 잠듦 |
| `wait()` 전에 이미 `notify()` 가 지나감 | 신호를 놓쳐 영원히 대기 (lost wakeup) |

⚠️ **원본이 `if` 를 쓴다:**

```java
// MakeWordThread, FallingThread — 원본
synchronized (this) {
    if (pause) {          // ← while 이어야 한다
        try { wait(); } catch (InterruptedException e) { return; }
    }
}
```
```java
// fillThread, HealthThread, DarkGage — 원본. 이쪽이 맞다
synchronized (this) {
    while (pause) {
        try { wait(); } catch (InterruptedException e) { return; }
    }
}
```

같은 프로그램 안에서 두 방식이 섞여 있다. `while` 이 맞다.

### 6.4 ⚠️ 플래그 가시성 — `volatile`

```java
private boolean running = false;      // 원본

@Override public void run() {
    while (running) { ... }           // 이 스레드가 읽는 값
}
public void stopThread() {
    running = false;                  // 다른 스레드가 쓰는 값
}
```

각 스레드는 성능을 위해 변수를 **자기 캐시에 들고 있을 수 있다.** 그래서 `running = false` 로 바꿔도 도는 쪽이 **영원히 못 볼 수 있다.**

```java
private volatile boolean running = false;   // "항상 메인 메모리에서 읽어라"
```

원본은 `interrupt()` 를 같이 불러서 우연히 빠져나온다. 하지만 정석은 `volatile` 이다.

### 6.5 `interrupt()`

"멈춰 달라"는 **신호**일 뿐 강제 종료가 아니다.

```java
th.interrupt();
// · sleep()/wait() 중이면 → InterruptedException 발생, 인터럽트 플래그는 해제됨
// · 그냥 계산 중이면    → 아무 일도 안 일어나고 플래그만 켜짐

try {
    Thread.sleep(100);
} catch (InterruptedException e) {
    return;              // 원본 방식: 즉시 종료
}
```

⚠️ `Thread.stop()` 은 **쓰지 마라.** deprecated이고 자물쇠를 잡은 채 죽어서 상태가 깨진다.

### 6.6 💡 그런데, 스레드가 정말 필요한가?

**대부분 필요 없다.** 이 게임에서 스레드가 하는 일은 결국 "일정 시간마다 값을 조금씩 바꾸고 다시 그리기"다. 그건 **Swing Timer** 가 훨씬 잘한다.

| | 스레드 20개 (원본) | Timer 1개 (권장) |
|---|---|---|
| 단어 이동 | 스레드마다 `setLocation()` | 리스트 순회하며 `y += speed` |
| EDT 안전성 | ❌ 위험 (3.5절) | ✅ 콜백이 EDT에서 실행 |
| 일시정지 | 플래그 20개 + `wait/notify` | `timer.stop()` 한 줄 |
| 재개 | 플래그 20개 + `notify()` | `timer.start()` 한 줄 |
| 종료 | `interrupt()` + 참조 제거 | `timer.stop()` |
| 코드량 | 스레드 클래스 5개 | 없음 |

**이 하나만 바꿔도 원본 코드의 절반이 사라진다.** 보고서 결론에서 말한 "간결하고 유지보수가 쉬운 구조"의 실체가 이거다.

그래도 스레드를 배우고 싶다면: 파일 로딩·네트워크처럼 **오래 걸리는 작업**에 쓰면 된다. 그게 원래 용도다. (`SwingWorker` 가 그 목적의 클래스다)

---

## 7. Swing Timer — 시간 흐름의 정석

```java
import javax.swing.Timer;   // ⚠️ java.util.Timer 아님!

// 16ms 마다 = 초당 약 60번 호출. 콜백은 EDT에서 실행된다
Timer timer = new Timer(16, e -> {
    update();      // 상태 갱신
    repaint();     // 다시 그리기
});

timer.start();               // 시작
timer.stop();                // 정지
timer.restart();             // 처음부터
timer.setRepeats(false);     // 한 번만 실행 (지연 실행용)
timer.setDelay(30);          // 주기 변경
```

| | `javax.swing.Timer` | `java.util.Timer` |
|---|---|---|
| 콜백 실행 스레드 | **EDT** | 별도 스레드 |
| Swing 컴포넌트 조작 | 안전 | 위험 |
| 용도 | GUI 애니메이션 | 백그라운드 작업 |

**GUI에서는 항상 `javax.swing.Timer`.** import를 헷갈리면 3장의 문제가 그대로 재현된다.

### 7.1 게임 루프 한 개로 통합하기

```java
public class GamePanel extends JPanel {
    private final List<Word> words = new ArrayList<>();   // 떨어지는 단어들
    private final Timer loop = new Timer(16, e -> tick());
    private long lastSpawn;

    private void tick() {
        long now = System.currentTimeMillis();

        // 1) 스폰
        if (now - lastSpawn >= 2000) {
            words.add(Word.random(getWidth()));
            lastSpawn = now;
        }

        // 2) 이동 + 바닥 도달 처리 — 순회 중 제거는 Iterator 로
        Iterator<Word> it = words.iterator();
        while (it.hasNext()) {
            Word w = it.next();
            w.y += fallSpeed;
            if (w.y >= getHeight()) {
                onMissed(w);
                it.remove();
            }
        }

        // 3) 게이지 충전 등 나머지 상태도 여기서
        darkGauge = Math.min(100, darkGauge + 1);

        repaint();   // 4) 한 번만 그린다
    }
}
```

⚠️ **순회하면서 리스트를 고치면** `ConcurrentModificationException` 이 난다. `Iterator.remove()` 나 `removeIf()` 를 쓰거나, 인덱스로 **뒤에서부터** 돈다.

💡 `Word` 는 그냥 `int x, y; String text;` 를 가진 작은 클래스면 된다. **JLabel 20개를 미리 만들어 두고 재활용하는 원본 방식보다 훨씬 단순하고, `paintComponent` 에서 `drawString()` 으로 직접 그리면 컴포넌트 자체가 필요 없다.**

---

## 8. 파일 입출력

### 8.1 읽기 / 쓰기

```java
// 읽기 — try-with-resources 를 쓰면 close() 를 안 잊는다
try (BufferedReader br = new BufferedReader(new FileReader("data/word.txt"))) {
    String line;
    while ((line = br.readLine()) != null) {
        list.add(line);
    }
} catch (IOException e) {
    e.printStackTrace();
}

// 쓰기 — 두 번째 인자 true 면 append(이어쓰기), 없으면 덮어쓰기
try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/word.txt", true))) {
    bw.write(text);
    bw.newLine();
}
```

### 8.2 ⚠️ `close()` 를 빠뜨리면 파일이 비어 있다

`BufferedWriter` 는 성능을 위해 **버퍼에 모았다가 한꺼번에** 쓴다. `close()`(또는 `flush()`)를 안 부르면 버퍼 내용이 파일에 안 들어간다.

```java
// 원본 LeaderboardPanel.save() — close() 가 없다. 그래서 랭킹이 저장되지 않는다
BufferedWriter bw = new BufferedWriter(new FileWriter(f));
for (Rangking e : list) {
    bw.write(e.name + "," + e.score);
    bw.newLine();
}
// ← 여기서 끝. 버퍼 내용이 사라진다
```

**try-with-resources 를 쓰면 이 실수 자체가 불가능해진다.**

### 8.3 ⚠️ 문자 인코딩

`FileReader`/`FileWriter` 는 **플랫폼 기본 인코딩**을 쓴다(JDK 17, 한국어 Windows = MS949). 읽기·쓰기를 둘 다 이걸로 하면 왕복은 맞지만, 파일 자체는 UTF-8이 아니다. 다른 도구로 열면 깨진다.

```java
// 인코딩을 못 박는 방법
new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));
new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8));

// 더 짧게 (Java 11+)
List<String> lines = Files.readAllLines(Path.of("data/word.txt"), StandardCharsets.UTF_8);
Files.write(Path.of("data/ranking.txt"), lines, StandardCharsets.UTF_8);
```

### 8.4 ⚠️ 깨진 줄 방어

```java
String[] tok = line.split(",");
list.add(new Rangking(tok[0], Integer.parseInt(tok[1])));   // 원본
```
빈 줄 하나만 있어도 `ArrayIndexOutOfBounds` 나 `NumberFormatException` 으로 **게임이 아예 안 뜬다.** 파일은 언제든 손상될 수 있다고 가정하고 `tok.length` 검사와 `try/catch` 를 넣어라.

---

## 9. 컬렉션

### 9.1 `Vector` vs `ArrayList`

```java
Vector<String> v = new Vector<>();      // 메소드마다 synchronized
List<String> list = new ArrayList<>();  // 동기화 없음. 대신 빠름
```

원본은 `Vector` 를 썼다(주석: "멀티스레딩이 실행되면 벡터에 접근이 안되기때문에").

⚠️ 정확히 짚으면 — `Vector` 는 메소드 **하나하나**는 안전하지만 **여러 개를 묶은 동작은 안전하지 않다.**

```java
if (!v.contains(x)) v.add(x);   // contains 와 add 사이에 다른 스레드가 끼어들 수 있다
```

💡 **6.6절대로 Timer 하나만 쓰면 모든 게 EDT에서 도니까 동기화 자체가 필요 없다.** 그냥 `ArrayList` 를 쓰면 된다.

### 9.2 정렬

```java
list.sort((a, b) -> b.score - a.score);              // 내림차순 (원본)
list.sort(Comparator.comparingInt(r -> -r.score));   // 같은 뜻
list.sort(Comparator.comparingInt(Rangking::score).reversed());
```

⚠️ `b.score - a.score` 는 값이 아주 클 때 **오버플로**로 부호가 뒤집힐 수 있다. 점수 정도면 괜찮지만 습관은 `Integer.compare(b, a)` 가 안전하다.

```java
// TOP 10 자르기
if (list.size() > 10) list.subList(10, list.size()).clear();
```

---

## 10. 리소스와 상대경로

### 10.1 상대경로의 기준은 "작업 디렉토리"

```java
new ImageIcon("images/karmen.jpg");   // ← 클래스 위치가 아니라 실행 시 작업 디렉토리 기준
```

**작업 디렉토리(`user.dir`)** 는 프로그램을 어디서 실행했느냐로 정해진다.

| 실행 방법 | 작업 디렉토리 |
|-----------|--------------|
| IntelliJ 실행 구성 | 설정된 `WORKING_DIRECTORY` (이 프로젝트는 `miniproject/lostArkClash`) |
| `run.bat` | 배치 파일이 `cd` 한 폴더 |
| 터미널에서 `java ...` | 명령을 친 그 폴더 |

```java
System.out.println(new File(".").getAbsolutePath());   // 안 보일 때 이걸로 확인
```

### 10.2 ⚠️ 이미지가 없어도 예외가 안 난다

```java
ImageIcon icon = new ImageIcon("없는파일.jpg");   // null 아님! 예외도 없음
Image img = icon.getImage();                     // null 아님!
g.drawImage(img, 0, 0, w, h, this);              // 조용히 아무것도 안 그려짐
```

`ImageIcon` 은 로딩 실패를 **조용히 삼킨다.** "왜 화면이 까맣지?" 의 원인 1순위다.

```java
if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
    System.err.println("이미지 로딩 실패: " + path);
}
```

💡 **더 나은 방법 — 클래스패스에서 읽기.** 작업 디렉토리에 안 휘둘리고, 나중에 jar로 묶어도 그대로 동작한다.

```java
URL url = getClass().getResource("/images/karmen.jpg");   // src 아래에 두면 out/ 으로 복사된다
ImageIcon icon = new ImageIcon(url);
```

⚠️ 단, **쓰기가 필요한 파일(word.txt, ranking.txt)은 클래스패스에 두면 안 된다.** 읽기 전용이기 때문. 리소스는 클래스패스, 저장 파일은 실제 경로로 나누는 게 맞다.

---

## 11. 사운드

```java
Clip clip = AudioSystem.getClip();
clip.open(AudioSystem.getAudioInputStream(new File("music/bgm.wav")));
clip.start();                          // 한 번 재생
clip.loop(Clip.LOOP_CONTINUOUSLY);     // 무한 반복
clip.stop();                           // 일시정지 (위치 유지)
clip.setFramePosition(0);              // 처음으로
clip.close();                          // 자원 해제
```

| 항목 | 내용 |
|------|------|
| 지원 형식 | **WAV, AU, AIFF만.** MP3는 기본 지원 안 됨 |
| 메모리 | `Clip` 은 파일 전체를 메모리에 올린다. 긴 음악은 부담 |
| 예외 | `LineUnavailableException`, `UnsupportedAudioFileException`, `IOException` 3종 |

⚠️ **원본이 죽는 지점:**

```java
loadAudio("music/lobby.wav");   // 실패하면 clip 은 null 인 채로
clip.start();                   // → NullPointerException. 프로그램이 아예 안 뜬다
```

파일이 없거나 형식이 안 맞으면 `catch` 로 들어가고 `clip` 은 그대로 null이다. 리소스 없이 실행하면 **창도 못 보고 끝난다.** null 검사 한 줄이면 막힌다.

---

## 12. 설계 — 원본의 아쉬움을 어떻게 넘을까

보고서 5장에서 스스로 남긴 진단이 정확하다. 그걸 그대로 과제로 삼자.

### 12.1 상태 플래그를 상태 하나로

**원본**: `running`, `pause`(×5 스레드), `active`, `resolved`, `inClash`, `clashActive`, `clash66Triggered`, `clash33Triggered`, `dark`, `atropineFlag`, `keyLocked`, `clashActive` …

플래그가 N개면 조합이 2^N개다. 그중 **말이 안 되는 조합**(정지 상태인데 격돌 중, 암흑인데 단어가 떨어짐)이 대부분이고, 버그는 거기서 나온다.

**대안**: 동시에 하나만 참인 것들을 `enum` 하나로 묶는다.

```java
enum GameState {
    READY,      // 시작 전
    PLAYING,    // 진행 중
    PAUSED,     // 일시정지
    CLASH,      // 격돌 미니게임
    DARK,       // 암흑 패턴
    OVER        // 종료
}

private GameState state = GameState.READY;

private void tick() {
    switch (state) {
        case PLAYING -> updatePlaying();
        case CLASH   -> updateClash();
        case DARK    -> updateDark();
        default      -> { }            // READY/PAUSED/OVER 는 아무것도 안 함
    }
    repaint();
}
```

**"격돌 중에는 단어가 안 떨어진다"가 코드로 보장된다.** 원본은 이걸 플래그 검사로 매번 확인해야 했다.

⚠️ 상태 전환은 한 곳에서만 하도록 모아라. 여기저기서 `state = ...` 를 하면 플래그 시절로 돌아간다.

```java
private void setState(GameState next) {
    System.out.println(state + " → " + next);   // 로그 한 줄이 디버깅을 살린다
    state = next;
}
```

### 12.2 책임 분리

**원본 `GamePanel` 이 하는 일** — UI 배치, 스레드 5종 관리, 난이도 파라미터, 단어 생성, 낙하 판정, 입력 처리, 격돌 트리거, 아이템 효과, 사망 처리, 랭킹 저장. **한 클래스 850줄.**

```
┌──────────────────────────────────────────────┐
│  나눠 본다면                                  │
├──────────────────────────────────────────────┤
│  GameModel     게임 규칙과 데이터             │
│                (HP, 점수, 단어 목록, 상태)     │
│                ※ Swing을 import 하지 않는다   │
│                                              │
│  GameView      GameModel 을 읽어서 그리기만    │
│                (paintComponent)              │
│                                              │
│  GameController 입력을 받아 Model 을 바꾸고    │
│                Timer 를 돌린다                │
└──────────────────────────────────────────────┘
```

💡 **판별법: `GameModel` 이 `import javax.swing.*` 없이 컴파일되는가?** 되면 분리가 된 것이다. 이러면 게임 규칙만 따로 테스트할 수도 있다.

### 12.3 양방향 참조를 단방향으로

**원본의 참조 관계** — 서로가 서로를 붙잡고 있다.

```
GameFrame ⇄ GamePanel ⇄ BattleItemPanel
                ⇅              ⇅
           HealthPoint ────────┘
                ⇅
          ScorePanel ⇄ DarkGage
```

`HealthPoint`(HP바 컴포넌트)가 `GamePanel` 을 들고 있다가 HP가 0이 되면 `gamePanel.onDeath()` 를 부른다. **화면 부품이 게임 진행을 지시하는 구조**다. 그래서 `GamePanel` 없이는 `HealthPoint` 를 못 만든다.

**대안 — 콜백(리스너)으로 뒤집는다:**

```java
class HealthPoint {
    private Runnable onDeath = () -> { };            // 기본은 아무것도 안 함

    public void setOnDeath(Runnable onDeath) { this.onDeath = onDeath; }

    public void decrease(int damage) {
        hp = Math.max(0, hp - damage);
        if (hp == 0) onDeath.run();                  // "누가" 처리할지는 모른다
    }
}

// 조립하는 쪽에서 연결
userHp.setOnDeath(() -> setState(GameState.OVER));
```

이제 `HealthPoint` 는 `GamePanel` 을 몰라도 된다. 화살표가 한 방향이 된다.

### 12.4 매직 넘버를 난이도 객체로

```java
// 원본 — if/else 안에 숫자가 흩어져 있다
if (difficulty == 2) { wordFallSpeed = 16; bossMaxHp = 600; missDamage = 10; ... }
else                 { wordFallSpeed = 10; bossMaxHp = 400; missDamage = 5;  ... }
```
```java
// 대안 — enum 에 값을 붙인다
enum Difficulty {
    NORMAL(10, 400, 5, 1),
    HARD  (16, 600, 10, 2);

    final int fallSpeed, bossHp, missDamage, clashSpeed;

    Difficulty(int fallSpeed, int bossHp, int missDamage, int clashSpeed) { ... }
}
```
난이도를 하나 더 추가할 때 **고칠 곳이 한 군데**가 된다.

---

## 13. 구현 로드맵

한 번에 다 만들려 하면 반드시 막힌다. **각 단계마다 실행되는 상태를 유지**하면서 쌓아라.

| 단계 | 목표 | 새로 배우는 것 | 완료 기준 |
|------|------|---------------|-----------|
| 0 | 창 띄우기 | `JFrame`, `setDefaultCloseOperation` | 창이 뜨고 X로 닫힌다 |
| 1 | 단어 **하나** 떨어뜨리기 | `Timer`, `paintComponent`, `drawString` | 글자가 위에서 아래로 내려온다 |
| 2 | 입력해서 지우기 | `JTextField` + `ActionListener` | 맞게 치면 사라진다 |
| 3 | 여러 개 + 자동 생성 | `List`, `Iterator.remove()` | 2초마다 하나씩 늘어난다 |
| 4 | HP·점수 | 커스텀 드로잉, 상태 관리 | 놓치면 HP가 줄고 0이면 게임오버 |
| 5 | 화면 전환 | `CardLayout` | 시작화면 → 게임 → 결과 |
| 6 | 파일 입출력 | `BufferedReader/Writer`, try-with-resources | 단어를 파일에서 읽고, 랭킹이 **저장된다** |
| 7 | 일시정지/재개 | `GameState` enum | stop/start 가 정확히 동작 |
| 8 | 격돌 | `KeyListener` 또는 Key Binding, 포커스 | 원 크기로 판정이 갈린다 |
| 9 | 암흑 패턴 | 제한시간 Timer, 상태 전환 | 게이지가 차면 발동하고 끝나면 복귀 |
| 10 | 아이템·이미지·사운드 | `MouseAdapter`, `ImageIcon`, `Clip` | 원본과 비슷한 모양새 |

💡 **1~3단계가 이 게임의 심장**이다. 여기까지 Timer 하나로 깔끔하게 나오면 나머지는 붙이기다.
💡 각 단계 끝에 **커밋**해라. 4단계에서 망가지면 3단계로 돌아갈 수 있다.

### 13.1 0~1단계 뼈대

```java
package practice;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Swing 객체 생성도 EDT에서 하는 것이 정석이다
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("lostArkClash");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new GamePanel());
            f.pack();                     // 컴포넌트 선호 크기에 맞춰 창 크기 결정
            f.setLocationRelativeTo(null); // 화면 가운데
            f.setVisible(true);
        });
    }
}

class GamePanel extends JPanel {
    private int y = 0;
    private final String word = "hello";

    GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.DARK_GRAY);

        new Timer(16, e -> {
            y += 2;
            if (y > getHeight()) y = 0;
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(word, 100, y);
    }
}
```

이게 돌면 1단계 끝이다. 여기서부터 늘려 가면 된다.

---

## 14. 자주 밟는 지뢰 모음

| 증상 | 원인 | 해결 |
|------|------|------|
| 화면이 아무것도 안 보인다 | `setLayout(null)` 인데 크기·위치 미지정 | `setBounds()` 또는 배치관리자 사용 |
| 배경색이 안 먹는다 | `JLabel`/`JPanel` 이 투명 | `setOpaque(true)` |
| 이미지가 안 그려지는데 에러도 없다 | 경로 오류 (`ImageIcon` 은 조용히 실패) | 작업 디렉토리 확인, `getImageLoadStatus()` |
| 키를 눌러도 반응이 없다 | 포커스가 없다 | `setFocusable(true)` + `requestFocusInWindow()`, 또는 Key Binding |
| 화면 전환 직후에만 키가 안 먹는다 | 아직 화면이 안 보임 | `SwingUtilities.invokeLater()` 로 포커스 요청 미루기 |
| 창이 통째로 얼었다 | EDT에서 `sleep`/무한루프 | Timer로 쪼개거나 다른 스레드로 |
| 값은 바뀌는데 화면이 안 바뀐다 | `repaint()` 안 부름 | 상태 변경 뒤 `repaint()` |
| 잔상이 남는다 | `super.paintComponent(g)` 누락 | 맨 앞에 추가 |
| 가끔 이상하게 그려진다 | EDT 밖에서 컴포넌트 조작 | `invokeLater()` 또는 Timer 구조로 |
| 파일에 아무것도 안 써졌다 | `close()`/`flush()` 누락 | try-with-resources |
| 리스트 순회 중 예외 | `ConcurrentModificationException` | `Iterator.remove()` / `removeIf()` |
| 한 번 누른 키가 여러 번 처리 | 키 auto-repeat, 리스너 중복 등록 | 잠금 플래그, `removeKeyListener()` 먼저 |
| `IllegalThreadStateException` | 끝난 스레드를 다시 `start()` | 새 객체 생성 (또는 Timer 사용) |
| 정지 후 재개가 안 된다 | `notify()` 신호를 놓침 / `if(pause) wait()` | `while` 로 검사, `notifyAll()`, 또는 Timer |
| 스레드가 안 멈춘다 | 플래그 가시성 | `volatile` + `interrupt()` |

---

## ✅ 핵심 요약

1. **EDT가 전부다.** UI는 EDT에서만 건드리고, EDT는 절대 붙잡지 않는다. 예외는 `repaint()`/`revalidate()` 뿐.
2. **시간 흐름은 `javax.swing.Timer` 하나로.** 스레드 25개가 Timer 1개로 줄고, 플래그와 `wait/notify` 가 통째로 사라진다. 이 결정 하나가 코드 절반을 없앤다.
3. **상태는 `enum` 하나로.** 플래그 12개 대신 `GameState` 하나. 불가능한 조합이 아예 안 생긴다.
4. **그리기는 "필드를 읽어서 그리기만".** 상태 변경 → `repaint()` 요청. 이 순서를 지키면 스레드 문제 대부분이 사라진다.
5. **의존은 한 방향으로.** 화면 부품이 게임 진행을 지시하지 않게. 필요하면 콜백으로 뒤집는다.
6. **파일은 try-with-resources.** `close()` 를 잊을 수 없는 구조로 만든다. 그리고 파일 내용은 항상 의심한다.
7. **단계별로 돌아가는 상태를 유지하며 쌓는다.** 1~3단계(단어 하나 → 입력 → 여러 개)가 심장이다.

> 막히면 `original` 패키지를 열어 보되, **베끼기 전에 "왜 저렇게 했을까 / 나라면?"** 을 먼저 생각하자.
> 보고서 5장에 이미 답이 절반쯤 적혀 있다.
