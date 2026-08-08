# 10주차 — 자바 이벤트 처리

이벤트 리스너 작성 4가지 방법, 마우스·키 이벤트, 어댑터 클래스.

실습 코드: [`example/src`](example/src) (E1~E10) · [`practice/src`](practice/src) (P1~P5)

## 강의 예제 (example)

| 파일 | 주제 |
|------|------|
| E1 | **독립 클래스** 리스너 — `class MyActionListener implements ActionListener` |
| E2 | **내부(멤버) 클래스** 리스너 — 바깥 클래스 멤버 접근 가능. `E2.this.setTitle()` |
| E3 | **익명 클래스** 리스너 — `new ActionListener(){ ... }`, 바깥 메소드를 그냥 `setTitle()`로 호출 |
| E4 | **`MouseListener` 직접 구현** — 안 쓰는 4개 메소드도 전부 빈 구현 필요 |
| E5 | **`MouseAdapter` 상속** — 필요한 `mousePressed()`만 오버라이딩 (E4와 같은 기능) |
| E6 | **`KeyAdapter`** — `getKeyCode()`/`getKeyChar()`/`getKeyText()` 비교 출력 |
| E7 | 키에 따라 배경색 변경 — `'%'`는 `getKeyChar()`, `F1`은 `getKeyCode()==KeyEvent.VK_F1` |
| E8 | 상하좌우 키로 레이블 이동. 포커스를 잃었을 때 대비해 **마우스 클릭으로 포커스 재획득** |
| E9 | **`MouseListener` + `MouseMotionListener` 동시 구현** — `mouseDragged`/`mouseMoved` 좌표 출력 |
| E10 | `getClickCount() == 2` 로 더블클릭 판정, 배경을 랜덤 색으로 |

## 실습문제 (practice)

| 파일 | 문제 |
|------|------|
| P1 | 마우스가 레이블에 들어오고 나갈 때 텍스트 변경 (`mouseEntered`/`mouseExited`) |
| P2 | 드래깅 중에만 배경 노랑, 떼면 초록 (`MouseMotionAdapter` + `MouseAdapter`) |
| P3 | ← 키로 문자열 한 글자씩 회전 — `substring`으로 앞글자를 뒤로 |
| P4 | ⚠️ **미완성** — `c.setLayout(new );` 문법 오류로 컴파일 불가. E8이 같은 문제의 완성본 |
| P5 | 클릭하면 레이블이 컨테이너 안 랜덤 위치로 도망가기 — `getParent()`로 부모 크기 얻어 범위 계산 |

## 핵심 정리

- **이벤트 처리 3요소**: 이벤트 소스(버튼) — 이벤트 객체(`ActionEvent`) — 리스너(`ActionListener`).
  `btn.addActionListener(리스너)`로 연결한다.
- **`e.getSource()`** 로 이벤트가 발생한 컴포넌트를 알아낸다. 리턴 타입이 `Object`라 캐스팅 필요.
- **리스너 작성 4방식** (E1~E3) — 코드가 길면 독립 클래스, 바깥 멤버를 써야 하면 내부 클래스,
  짧으면 익명 클래스. 실무에선 익명 클래스/람다가 가장 흔하다.
- **어댑터를 쓰는 이유**: `MouseListener`는 인터페이스라 5개 메소드를 **전부** 구현해야 한다.
  `MouseAdapter`는 그걸 빈 구현으로 미리 제공하는 클래스라 필요한 것만 오버라이딩하면 된다.
  단 인터페이스는 `implements`, 어댑터는 **`extends`** (E4 vs E5).
- ⚠️ **키 이벤트에는 포커스가 필요하다** — `setFocusable(true)` + `requestFocus()`를 반드시 호출.
  컨텐트팬은 기본적으로 포커스를 받지 못한다 (E6~E8, P3).
- **`MouseListener`와 `MouseMotionListener`는 별개** — 드래그/이동을 잡으려면
  `addMouseMotionListener()`도 따로 등록해야 한다 (E9, P2).
