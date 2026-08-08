# 12주차 — 그래픽

`paintComponent()`와 `Graphics` 클래스로 직접 그리기, 이미지 출력, `repaint()`.

실습 코드: [`example/src`](example/src) (E1~E11) · [`practice/src`](practice/src) (P1~P4)

## 강의 예제 (example)

| 파일 | 주제 |
|------|------|
| E1 | **`paintComponent(Graphics g)` 오버라이딩** — `JPanel`을 상속해 `drawRect()`로 사각형 그리기 |
| E2 | `drawString()` — 좌표에 문자열 출력 |
| E3 | `setColor()` / `setFont()` — `Color(255,0,0)`, `0x0000ff00` 형태, `Font("Arial", Font.ITALIC, 30)` |
| E4 | `drawLine()` (주석에 `drawOval`/`drawRect`/`drawRoundRect` 비교) |
| E5 | **`fillXXX` 계열** — `fillRect`, `fillOval`, `fillRoundRect`, `fillArc`(원호), `fillPolygon`(폐다각형) |
| E6 | `drawImage(img, x, y, this)` — **원본 크기**로 그리기 |
| E7 | `drawImage(img, 0, 0, getWidth(), getHeight(), this)` — 패널 크기에 **맞춰 확대/축소** |
| E8 | 10개 인자 `drawImage()` — 원본의 **일부 영역**을 잘라 지정 위치에 크기 조절해 그리기 |
| E9 | **클리핑** `setClip(x,y,w,h)` — 지정 영역 밖은 그려지지 않음 |
| E10 | **마우스로 선 그리기** — `Vector<Point>` 두 개에 시작/끝점 누적, `mouseReleased`에서 `repaint()` |
| E11 | **컴포넌트 커스터마이징** — `JButton`을 상속해 `paintComponent()`에서 빨간 원 덧그리기 |

## 실습문제 (practice)

| 파일 | 문제 |
|------|------|
| P1 | 버튼으로 이미지 **숨기기/보이기** — `boolean showflag` 토글 후 `repaint()` |
| P1to1 | 마우스 좌/우 버튼으로 하위 컴포넌트 폰트 크기 재귀 변경 (`getComponents()` 순회) |
| P1to2 | P1과 같은 문제를 카운터 홀짝으로 푼 버전 |
| P2 | 이미지 위에서 드래깅하면 녹색 원이 따라옴 (`fillOval` + `mouseDragged`) |
| P3 | `JLabel`을 상속해 `paintComponent()`에서 이미지를 드래그 위치에 그리기 |
| P3lab | 같은 드래깅을 **레이블 자체를 `setLocation()`으로 이동**시켜 구현 (교재 방식) |
| P4 | 패널 안 이미지 드래깅 |

## 핵심 정리

- **그리기는 `paintComponent(Graphics g)` 안에서만** 한다. 직접 호출하지 않고,
  화면 갱신이 필요하면 **`repaint()`** 를 부르면 스윙이 알아서 호출한다.
- **첫 줄은 항상 `super.paintComponent(g)`** — 배경을 지우는 작업이다. 빼먹으면 이전 그림이 남는다.
- **`draw` vs `fill`**: 테두리만 vs 내부 채움. 색은 `setColor()`로 **그리기 직전에** 설정.
- **좌표계**는 왼쪽 위가 (0,0), 아래·오른쪽이 양수. `drawRect(x, y, width, height)`.
- **상태 → 화면**의 흐름을 지킨다: 이벤트에서 **필드를 바꾸고 `repaint()`** →
  `paintComponent()`는 그 필드를 읽어 그리기만. P1의 `showflag`, P2/P3의 `currentPoint`가 그 형태.
- **드래깅 구현 두 방식**
  - 직접 그리기: 좌표를 저장하고 `paintComponent()`에서 그 위치에 그린다 (P2, P3, P4).
  - 컴포넌트 이동: `setLocation()`으로 레이블 자체를 옮긴다 (P3lab).
- `ImageIcon("파일").getImage()`로 `Image`를 얻어 `drawImage()`에 넘긴다.
  마지막 인자 `this`는 `ImageObserver` — 로딩 중 갱신 통지를 받는 컴포넌트다.

⚠️ P4는 `paintComponent()` **안에서** `addMouseMotionListener()`를 호출한다.
`paintComponent`는 매번 다시 호출되므로 리스너가 계속 쌓인다 — 생성자에서 한 번만 등록하는 게 맞다
(P2가 올바른 형태).

⚠️ P1의 `img.getWidth(this)`는 이미지 로딩 전이면 `-1`을 리턴할 수 있다.
E7처럼 패널 크기(`getWidth()`)를 쓰는 편이 안전하다.
