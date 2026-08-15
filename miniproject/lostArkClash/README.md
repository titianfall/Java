# lostArkClash — 객체지향언어 미니프로젝트 복원

> 출처: `객체지향언어2_미니프로젝트_보고서.pdf` (2271344 박지호 / 담당교수 황기태)
> 보고서 **4장 "프로그램 소스 코드"** 에 실린 코드를 그대로 되살려 IntelliJ에서 바로 실행되도록 정리한 폴더다.

떨어지는 단어를 타이핑해 보스(카멘)의 HP를 깎는 Swing 타자 게임.
보스 HP가 2/3, 1/3 지점에 닿으면 **격돌** 미니게임이, 암흑 게이지가 다 차면 **암흑 패턴**이 발동한다.

---

## 패키지 두 개

| 패키지 | 무엇 | 진입점 |
|--------|------|--------|
| `original` | 보고서 복원본. **읽기 위한 코드** — 손대지 않고 기준점으로 둔다. | `original.GameMain` |
| `practice` | **여기서부터 직접 다시 짠다.** 지금은 Hello World 하나뿐. | `practice.Main` |

> 📘 참고 문서 (성격이 달라 따로 둔다)
> · **[스윙 기초 레퍼런스 01~08](src/practice/docs/README.md)** — Swing 문법을 찾아보는 범용 문서. 시작하기 · 컴포넌트 · 레이아웃 · 이벤트 · 그래픽 · 대화상자 · **스레드와 EDT** · **함정 모음**
> · **[lostArkClash 다시 짜기](docs/lostArkClash%20다시%20짜기.md)** — 이 게임의 설계와 단계별 로드맵

```
miniproject/lostArkClash/
├── src/
│   ├── original/     ← 복원본 16개 클래스
│   └── practice/     ← Main.java (Hello World)
│       └── docs/     ← 스윙 기초 레퍼런스 01~08 (범용)
├── docs/             ← 이 게임 설계 가이드 (프로젝트 문서)
├── images/  music/  data/     ← 두 패키지가 공유하는 리소스
├── tools/GenerateAssets.java  ← 자리표시 리소스 생성기
└── run.bat  runPractice.bat  assets.bat
```

리소스는 두 패키지가 같이 쓴다. `practice` 에서도 `new ImageIcon("images/karmen.jpg")` 가 그대로 동작한다.

---

## 실행 방법

### 1) IntelliJ — 저장소 전체(`D:\Java`)를 연 경우
`.idea`에 모듈과 실행 구성 2개를 등록해 두었다. 상단에서 골라 ▶ 실행.

| 실행 구성 | 실행되는 것 |
|-----------|-------------|
| `lostArkClash (original)` | 복원본 게임 |
| `lostArkClash (practice)` | 내가 짜는 쪽 |

> 실행 구성의 작업 디렉토리가 `miniproject/lostArkClash` 로 잡혀 있어야 한다.
> 코드가 `images/...`, `data/word.txt` 처럼 **상대경로**로 리소스를 읽기 때문이다.

### 2) IntelliJ — 이 폴더만 따로 연 경우
`File > Open` 으로 `miniproject/lostArkClash` 를 열면 `src` 가 소스 루트로 잡힌다.
`main()` 을 실행하면 작업 디렉토리가 프로젝트 루트가 되므로 그대로 동작한다.

### 3) IDE 없이
```
run.bat            # 복원본 실행 (original.GameMain)
runPractice.bat    # 내가 짜는 쪽 실행 (practice.Main)
assets.bat         # 없는 리소스만 다시 생성
```

**필요 JDK: 17** (특정 버전에 의존하는 문법은 없어서 11 이상이면 동작한다)

---

## 리소스에 대해 ⚠️

보고서 PDF에는 소스 코드만 들어 있고 `images/`, `music/`, `data/` 의 실제 파일은 없다.
코드가 이 파일들을 **상대경로로 직접 읽기 때문에** 없으면 화면이 텅 비고 음악에서 예외가 난다.

그래서 `tools/GenerateAssets.java` 로 **자리표시(placeholder) 리소스**를 만들어 넣어 두었다.

| 폴더 | 내용 |
|------|------|
| `images/` | 그라디언트 배경 + 글자만 있는 임시 이미지 25개 |
| `music/` | 2초짜리 무음 wav 2개 (`lobby.wav`, `backgroundMusic.wav`) |
| `data/` | `word.txt` (영단어 30개), `ranking.txt` (빈 파일) |

> 원본 이미지·음악이 남아 있다면 **같은 파일명으로 덮어쓰기만** 하면 된다.
> `assets.bat` 은 이미 있는 파일은 건너뛰므로 진짜 리소스를 지우지 않는다.

---

## 클래스 구조 (`original` 패키지)

| 클래스 | 역할 |
|--------|------|
| `GameMain` | 프로그램 진입점 |
| `GameFrame` | CardLayout 화면 전환, 메뉴/툴바, BGM 관리 |
| `GameContainerPanel` | JSplitPane 으로 게임 화면 / 점수 / 아이템 배치 |
| `StartPanel` | 닉네임 입력·난이도 선택 |
| `EditPanel` | 단어 추가 (`data/word.txt` 에 append) |
| `TextStore` | 단어 Vector 관리 · 랜덤 단어 제공 |
| `LeaderboardPanel` | TOP 10 랭킹 로드/정렬/저장 (+ `Rangking`) |
| `GamePanel` | 게임 핵심 로직 총괄 (+ `MakeWordThread`, `FallingThread`, `GroundPanel`, `InputPanel`) |
| `ClashPanel` | 격돌 미니게임 ※ **보고서에 코드 없음 — 사양 기반 재작성** |
| `DarkModeController` | 암흑 패턴 진행/판정/종료 |
| `DarkWord` | 단어를 글자 단위 JLabel 배열로 분해 |
| `DarkTimeBar` | 암흑 패턴 제한시간 바 |
| `DarkGage` | 원호 모양 암흑 게이지 (`Runnable`) |
| `ScorePanel` | 점수·에스더 게이지 (+ `fillThread`, `MyLabel`) |
| `BattleItemPanel` | 포션 / 아드로핀 / 파괴폭탄 + 상태 아이콘 |
| `HealthPoint` | HP 바 커스텀 컴포넌트 (+ `HealthThread`) |

### 화면 전환 흐름

```
StartPanel ──시작──> GameContainerPanel ──보스HP 66%,33%──> ClashPanel
    │  └──추가──> EditPanel                     │                  │
    │  └──랭킹──> LeaderboardPanel              └<─── 격돌 종료 ────┘
    └<────────── 사망 / 보스 처치 ──────────────┘
```

### 스레드 구성

| 스레드 | 하는 일 | 제어 방식 |
|--------|---------|-----------|
| `MakeWordThread` | 2초마다 단어 라벨 하나 생성 | `running` / `pause` 플래그 + `wait/notify` |
| `FallingThread` × 20 | 라벨 하나를 아래로 이동 | 동일 |
| `HealthThread` × 2 | 유저·보스 HP 주기 처리 | 동일 |
| `fillThread` | 에스더 게이지 충전 | 동일 |
| `DarkGage`(Runnable) | 암흑 게이지 100ms마다 1% 충전 | 동일 |
| Swing `Timer` | 암흑 제한시간, 격돌 원 축소 | EDT에서 동작 |

---

## 원본과 달라진 점

`original` 패키지는 **순수 복원본이다.** 로직·주석은 보고서 원문 그대로이고, 주석의 오탈자(`만듭닏`, `인터럽스`, `DEALY`, `interrput`, `Grphics` …)까지 원문을 유지했다.
원본에 있던 버그도 **고치지 않고 그대로 두었다.** 아래 4가지만 예외다.

### 1. 코드가 없어서 새로 쓴 것 — `ClashPanel` 뿐

보고서에 **제목만 있고 본문 코드가 통째로 누락**되어 있어, 1장(작품 개요)·3장(실행 과정)의 사양대로 다시 작성했다.
→ 지정 문자 8개(`Q W E R A S D F`) 중 랜덤 출력, 줄어드는 원으로 `PERFECT / GOOD / BAD` 판정,
3세트 × 1~3회(최소 3 최대 9회), BAD 시 HP 60(전체 200의 30%) 피해, BAD 2회면 즉시 종료.
`GameFrame` 이 호출하는 규격(생성자, `startClashSequence()`, `setShrinkSpeed()`)은 원본 그대로 맞췄다.

> 이 클래스만 성격이 다르므로, 나머지 15개와 같은 눈으로 보지 말 것.

### 2. 해상도 호환 — 2곳 (`// [해상도 호환]` 로 표시)

원본은 **2560 폭 모니터 기준 절대좌표**라 FHD(1920)에서는 UI가 화면 밖으로 밀려난다.

| 위치 | 원본 | 변경 |
|------|------|------|
| `GameContainerPanel` | `setDividerLocation(2300)` | `화면폭 - 560` 으로 계산 |
| `EditPanel` | 입력창 x=1000, 저장 x=1900, 목록 1400×900 @(700,100) | 화면 중앙 기준 상대 배치 |

### 3. 패키지 선언 한 줄

원본은 패키지 없이 기본 패키지에 있었다. `practice` 와 나란히 두기 위해 각 파일 맨 위에 `package original;` 만 추가했다.
그 아래 내용은 손대지 않았다.

### 4. PDF 조판이 깨져 위치를 정해야 했던 한 줄

`GamePanel.damageBoost()` 의 `battleItemPanel.changeImageToNormal();` 이 보고서에서는 **메소드 밖(클래스 본문)에 떠 있어** 그대로는 컴파일되지 않는다.
문맥상 아드로핀 효과가 끝난 뒤 아이콘을 되돌리는 자리로 판단해 10초 뒤 복구 블록 안에 넣었다. 코드에 그 사유를 주석으로 적어 두었다.

> 그 밖에 PDF에서 사라진 `-` 기호(`System.currentTimeMillis() - timePaused` 등)와 줄바꿈·들여쓰기는 복구했다.
> 원본 파일의 실제 포맷은 PDF로 알 수 없으므로 **들여쓰기와 줄 나눔은 재구성한 것**이다.

---

## 원본에 남아 있는 버그 (일부러 안 고침) 🐞

직접 재현해 보고 고쳐 보라고 그대로 뒀다. 정상적으로 플레이하면 대부분 안 만난다.

| # | 위치 | 재현 방법 | 증상 |
|---|------|-----------|------|
| 1 | `LeaderboardPanel.save()` | 게임을 끝내고 랭킹 화면을 본다 | `BufferedWriter.close()` 누락 → 버퍼가 flush되지 않아 **랭킹이 파일에 저장되지 않는다** |
| 2 | `GamePanel.exit()` | 게임을 시작하지 않고 `exit` 버튼 | `userHpThread` 가 null → NPE |
| 3 | `ScorePanel.pause()` | (2번과 같은 경로) | `th` 가 null → NPE |
| 4 | `GameFrame` 생성자 | `music/lobby.wav` 를 지우고 실행 | `clip` 이 null → NPE로 **프로그램이 아예 안 뜬다** |
| 5 | `TextStore.get()` | `data/word.txt` 를 비우고 실행 | `v.size()` 가 0 → 인덱스 예외 |
| 6 | `LeaderboardPanel.loadRanking()` | `ranking.txt` 에 빈 줄을 넣는다 | 파싱 예외 |
| 7 | `InputPanel` / `FallingThread` | 폭탄과 입력이 같은 단어에 겹칠 때 | 이미 null인 `fThread[i]` 에 `interrupt()` → NPE 가능 |
| 8 | `GamePanel.exit()` → 재시작 | 게임을 끝내고 다시 시작 | 점수가 초기화되지 않고 **이어진다** |
| 9 | `DarkModeController.finish()` | 암흑 패턴을 실패한다 | 보고서 1장에는 *"사용자 HP 및 점수를 감소"* 라고 적혀 있으나 **점수만 깎이고 HP는 안 깎인다** |
| 10 | `HealthThread` | — | `pause \|\| isFull()` 로 대기하는데 HP가 줄어도 깨워 주는 쪽이 없다. 사실상 아무 일도 하지 않는 스레드 |

---

## 다시 짜 볼 때 생각해 볼 것 💡

보고서 5장 결론에서 스스로 남긴 아쉬움 그대로가 좋은 출발점이다.

1. **상태 플래그가 너무 많다** — `running`, `pause`, `active`, `resolved`, `inClash`, `clashActive`, `clash66Triggered`, `dark`, `atropineFlag` … 스레드마다 같은 플래그 쌍이 반복된다.
   → 공통 `GameThread` 상위 클래스 하나로 묶거나, `enum GameState { READY, PLAYING, PAUSED, CLASH, DARK, OVER }` 하나로 대체할 수 있는지 따져 보기.
2. **책임이 겹친다** — `GamePanel` 이 UI · 스레드 관리 · 난이도 · 격돌 트리거 · 사망 처리를 전부 들고 있다.
   → "화면을 그리는 것"과 "게임 규칙"을 분리하면 어디까지 쪼갤 수 있을까?
3. **양방향 참조가 많다** — `GameFrame ↔ GamePanel ↔ BattleItemPanel ↔ HealthPoint` 가 서로를 붙잡고 있다.
   → 이벤트/리스너(콜백)로 한 방향으로 만들 수 있는 지점 찾기.
4. **`wait/notify` 대신** — `Thread.interrupt()` 와 플래그를 섞어 쓰고 있다. 한 판이 끝난 뒤 스레드를 새로 만드는 지금 구조를, Swing `Timer` 하나로 대체하면 어떻게 달라질까?
5. **에스더 스킬 3종(니나브 / 웨이 / 이난나)** 은 아이콘과 게이지만 있고 미구현이다. 게이지가 꽉 찼을 때 실제로 발동시켜 보기.
