# 06주차 — 자바 기본 API와 `Object` 메소드

`String` 조작, `StringTokenizer`, `Calendar`, 그리고 `toString()`/`equals()` 오버라이딩.

실습 코드: [`example/src`](example/src) (Example) · [`practice/src`](practice/src) (EX1, Ex2, Ex5~Ex7, Ex12)

## 파일별 요약

| 파일 | 주제 |
|------|------|
| [Example](example/src/Example.java) | **`StringTokenizer`** — `"a=3,b=5,c=6"`을 `,=` 두 문자로 분리, `hasMoreTokens()`/`nextToken()` 루프. 숫자만 골라 합산 (`NumberFormatException`으로 구분) |
| [EX1](practice/src/EX1.java) | `Student` 클래스에 **`toString()` / `equals()` 오버라이딩** |
| [Ex2](practice/src/Ex2.java) | `Book` 클래스 — 같은 패턴을 필드 3개로 확장 |
| [Ex5](practice/src/Ex5.java) | `String.split(" ")` + `switch`로 학점(A~F) → 점수 환산, 평균 계산 |
| [Ex6](practice/src/Ex6.java) | **`substring()`** 두 형태 — `substring(i)`, `substring(0,i)`로 문자열 회전 출력 |
| [Ex7](practice/src/Ex7.java) | **`Calendar`** — `getInstance()`, `set(YEAR/MONTH/DAY_OF_MONTH)`, `get(DAY_OF_WEEK)`, `getActualMaximum()`으로 1년치 달력 출력 |
| [Ex12](practice/src/Ex12.java) | ⚠️ **미완성** — 숫자 맞히기 게임. 아래 참고 |

## 핵심 정리

- **`String`은 불변(immutable)** — `trim()`, `split()`, `substring()`, `toUpperCase()`는 원본을 바꾸지 않고
  **새 문자열을 리턴**한다. 리턴값을 받지 않으면 아무 일도 안 일어난다.
- **문자열 분리 두 방법**
  - `split(정규식)` → `String[]` 리턴. 간단할 때 (Ex5).
  - `StringTokenizer(문자열, 구분자들)` → 하나씩 꺼내 쓰기. 구분자를 여러 개 줄 수 있다 (Example).
- **`Object`의 메소드 오버라이딩**
  - `toString()` — `System.out.println(객체)`가 자동으로 호출한다 (EX1에서 `println(a)`가 동작하는 이유).
  - `equals()` — 오버라이딩하지 않으면 `Object`의 기본 구현(주소 비교)이 쓰인다.
- ⚠️ EX1/Ex2의 `equals`는 **오버라이딩이 아니다**. `Object.equals`의 시그니처는
  `equals(Object)`인데 여기선 `equals(Student)` / `equals(Book)`이라 **오버로딩**이다.
  `@Override`를 붙였다면 컴파일 오류로 잡혔을 것 (EX1에 `//@Override`로 주석 처리되어 있음).
  올바른 형태는 `public boolean equals(Object obj)` + 내부에서 캐스팅.
- **`Calendar`는 `MONTH`가 0부터** — 1월이 `0`. 출력할 때 `+1` 해야 한다 (Ex7).

## ⚠️ Ex12 — 컴파일되지 않음

작성 중 멈춘 파일이다. 남아 있는 문제:

- `playerCount` 배열이 **선언되지 않았는데** `playerGamble()`과 `main()`에서 사용됨
- `playerCountInit()`이 `int[]`를 선언했는데 **`return` 문이 없음**
- `run()`에서 `int[] playerCount`를 **두 번 선언**, 존재하지 않는 `printGamble()` 호출
- `main()`에서 `run()` 밖의 지역변수(`playerArray`, `playerIndex`)를 참조

`selectNumber = {}`(길이 0 배열)에 인덱스로 넣는 부분도 런타임 예외가 난다 —
`new int[playerArray.length]`로 잡아야 한다.
