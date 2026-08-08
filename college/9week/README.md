# 09주차 — 자바 GUI: 스윙 기초

`JFrame`, 컨텐트팬, 배치관리자(`FlowLayout`/`BorderLayout`/`GridLayout`), `JPanel`.

실습 코드: [`example/src`](example/src) (E1~E7) · [`practice/src`](practice/src) (P1~P6)

## 강의 예제 (example)

| 파일 | 주제 |
|------|------|
| E1 | 최소 스윙 프레임 — `setTitle()`, `setSize()`, **`setVisible(true)`**(기본값은 invisible) |
| E2 | **컨텐트팬** — `getContentPane()`으로 얻어 배경색·배치관리자 설정, 버튼 3개 추가. `setDefaultCloseOperation(EXIT_ON_CLOSE)` |
| E3 | **`FlowLayout`** — `new FlowLayout(FlowLayout.LEFT, 30, 40)` 정렬·간격 지정 |
| E4 | **`BorderLayout`** — `NORTH`/`SOUTH`/`EAST`/`WEST`/`CENTER` 5개 영역 |
| E5 | **`GridLayout(4,2)`** — 격자 배치, `setVgap()`. 레이블+텍스트필드 폼 |
| E6 | **배치관리자 없이(`setLayout(null)`)** — 컴포넌트마다 `setLocation()`+`setSize()` 직접 지정 |
| E7 | **`JPanel` 중첩** — `NorthPanel`/`CenterPanel` 내부 클래스를 만들어 `BorderLayout`에 배치. `super("제목")`으로 타이틀 설정 |

## 실습문제 (practice)

| 파일 | 문제 |
|------|------|
| P1 | 컨텐트팬 배경 노랑 프레임 |
| P2 | `BorderLayout(5,7)` 5개 버튼 |
| P3 | `GridLayout(1,10)`으로 버튼 10개 |
| P4 | P3 + 버튼마다 다른 배경색 (`Color` 배열) |
| P5 | `GridLayout(4,4)` 16색 격자 |
| P6 | `setLayout(null)` + 랜덤 위치에 색 레이블 20개 — **`setOpaque(true)`** 필수 |

## 핵심 정리

- **스윙 프레임의 기본 틀**: `JFrame`을 상속 → 생성자에서 제목/닫기동작/컨텐트팬/크기 설정 →
  마지막에 `setVisible(true)`.
- **컴포넌트는 프레임이 아니라 컨텐트팬에 붙인다** — `getContentPane().add(...)`.
- **배치관리자 요약**

  | | 배치 방식 | 크기 |
  |---|---|---|
  | `FlowLayout` | 왼→오, 넘치면 다음 줄 | 컴포넌트 선호 크기 |
  | `BorderLayout` | 5개 영역, 각 영역 1개 | 영역에 꽉 채움 |
  | `GridLayout` | 격자, 추가 순서대로 | 칸에 꽉 채움 |
  | `null` | 안 함 | `setSize()`로 직접 |

- ⚠️ **`setLayout(null)`이면 `setSize()`와 `setLocation()`을 반드시 둘 다** 호출해야 한다.
  하나라도 빠지면 크기 0이라 화면에 안 보인다 (E6, P6).
- ⚠️ **`JLabel`의 배경색은 기본적으로 안 보인다** — `setOpaque(true)`를 해야 `setBackground()`가 반영된다 (P6).
- `setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)`가 없으면 창을 닫아도 프로세스가 안 죽는다.
- P3은 스윙 `JButton`이 아니라 AWT `Button`을 쓴다 — `setBackground` 동작이 P4(`JButton`)와 다르다.

💡 E7처럼 화면을 `JPanel` 단위로 쪼개는 방식이 실전 구조다.
[11주차](../11week)의 `MyPanel`, [12주차](../12week)의 `paintComponent()`로 이어진다.
