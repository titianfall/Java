# 11주차 — 스윙 컴포넌트 활용

`JLabel`·`JButton`·`JCheckBox`·`JRadioButton`·`JTextField`·`JTextArea`·`JList`·`JComboBox`·`JSlider`,
그리고 `ItemEvent`/`ChangeEvent`.

실습 코드: [`example/src`](example/src) (E1~E15) · [`example/practice/src`](example/practice/src) (P1, P2, P3, P5)

> 📁 실습문제가 `example/practice/`로 한 단계 더 들어가 있다 — 다른 주차와 구조가 다르다.

## 강의 예제 (example/src)

| 파일 | 주제 |
|------|------|
| E1 | **`JComponent` 공통 속성** — `setBackground`/`setForeground`/`setFont`/`setEnabled(false)`. `getTopLevelAncestor()`로 최상위 프레임 얻기. 컴포넌트 삭제 후 **`revalidate()`+`repaint()`** |
| E2 | `JLabel` 3종 — 문자열 / 이미지(`ImageIcon`) / 문자열+이미지+정렬 |
| E3 | **이미지 버튼** — `setPressedIcon()`, `setRolloverIcon()` |
| E4 | `JCheckBox` — 초기 선택 상태, 이미지 체크박스, `setSelectedIcon()`, `setBorderPainted(true)` |
| E5 | **`ItemEvent`** — 체크박스 선택/해제에 따라 금액 누적. `getStateChange()==ItemEvent.SELECTED` |
| E6 | `JRadioButton` + **`ButtonGroup`** — 그룹에 넣어야 하나만 선택된다 |
| E7 | 라디오버튼 `ItemEvent`로 이미지 전환. 해제 이벤트는 `DESELECTED`로 걸러 무시 |
| E8 | `JTextField` — `setEditable(false)`, `setText()`, `setFont()` |
| E9 | `JTextArea` + **`JScrollPane`** — 텍스트필드에서 Enter 치면 `append()` |
| E10 | `JList<String>` / `JList<ImageIcon>` / 스크롤 달린 리스트 (`setListData()`) |
| E11 | `Vector`로 리스트 내용 동적 변경 — `setVisibleRowCount()`, `setFixedCellWidth()` |
| E12 | `JComboBox` — 배열로 생성 vs `addItem()`으로 하나씩 추가 |
| E13 | 콤보박스 선택에 따라 이미지 변경 — `getSelectedIndex()` |
| E14 | `JSlider` — `setPaintLabels`/`setPaintTicks`/`setMajorTickSpacing`/`setMinorTickSpacing` |
| E15 | **`ChangeEvent`** — R/G/B 슬라이더 3개로 레이블 배경색 실시간 변경 |

## 실습문제 (example/practice/src)

| 파일 | 문제 |
|------|------|
| P1 | 체크박스 2개로 버튼 **비활성화(`setEnabled`)** / **감추기(`setVisible`)** 제어 |
| P2 | 텍스트필드에 입력한 문자열을 콤보박스에 추가 |
| P3 | **Money Changer** — 금액 입력 → 5만원~1원 단위별 개수 계산. `JPanel` + `setLayout(null)` 절대 배치 |
| P5 | `JSlider`(100~200) 값을 레이블에 표시 — `ChangeListener` |

## 핵심 정리

- **이벤트 종류로 구분하기**
  - `ActionEvent` — 버튼 클릭, 텍스트필드 Enter, 콤보박스 선택
  - `ItemEvent` — 체크박스·라디오버튼의 **선택/해제** (`SELECTED`/`DESELECTED` 둘 다 발생)
  - `ChangeEvent` — 슬라이더 값 변화
- ⚠️ **`ItemEvent`는 선택과 해제 모두** 리스너를 호출한다. 라디오버튼은 하나 고르면
  이전 것의 `DESELECTED`까지 오므로 E7처럼 걸러내야 중복 처리가 안 생긴다.
- **`ButtonGroup`은 화면 배치와 무관** — 배타 선택만 담당한다. 화면에 붙이는 건 별도로 `c.add()`.
- **`JTextArea`는 스크롤바가 없다** — `new JScrollPane(ta)`로 감싸야 생긴다 (E9, E10).
- **런타임에 컴포넌트를 추가/삭제**했으면 `revalidate()`(재배치) + `repaint()`(다시 그리기) (E1).
- `getSelectedIndex()`로 선택 항목의 **인덱스**를 얻어 병렬 배열(이미지 등)과 짝지어 쓴다 (E13, E7).

⚠️ 이미지 파일 경로가 예제마다 다르다 — E2~E6, E10, E13은 `sul2.jpg`~`sul4.jpg`(프로젝트 폴더 기준),
E7은 `images/apple.jpg` 등 존재하지 않는 경로다. `ImageIcon`은 파일이 없어도 예외 없이
빈 아이콘이 되므로 **화면에 안 보이면 경로부터 확인**한다.
