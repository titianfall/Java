# 05주차 — 상속

`extends`, `super`, 오버라이딩, `protected`, `instanceof`, 추상 클래스, 인터페이스.

실습 코드: [`example/src`](example/src) (ex1, ex5) · [`practice/src`](practice/src) (P1~P13)

## 강의 예제 (example)

| 파일 | 주제 |
|------|------|
| ex1 | `ColorPoint extends Point` — 서브 클래스에서 상속받은 `showPoint()` 호출 |
| ex5 | **`instanceof`와 상속 계층** — `Person ← Student` / `Person ← Researcher ← Professor`. `Professor` 객체는 `Person`이자 `Researcher`이기도 하다 |

## 실습문제 (practice)

| 파일 | 주제 |
|------|------|
| P1 | `ColorTV extends TV` — `super(size)`로 부모 생성자 호출, `protected getSize()` 접근 |
| P2 | **3단 상속** `TV ← ColorTV ← SmartTV` — `super(size,color)`가 연쇄 호출됨 |
| P3 | `ColorPoint` + `toString()` 오버라이딩 |
| P4 | `ColorPoint2` — 생성자 3개 오버로딩, `set()` 오버로딩, `getDistance()`에 `Math.sqrt`/`Math.pow` |
| P5 | `Point3D` — `move(x,y)`(상속) vs `move(x,y,z)`(오버로딩), 내부에서 `move(x,y)` 재사용 |
| P6 | **오버라이딩으로 동작 제한** — `PositivePoint.move()`가 양수일 때만 `super.move()` 호출 |
| p7 | `Point3DColor` — `equals(Point3DColor)`, `toString()` |
| P8 | **추상 클래스** `Box` (`abstract consume()`, `abstract print()`) → 커피 자판기 |
| P9 / P10 | 같은 부모 `BaseArray`를 서로 다르게 확장 — 이진화(`BinaryArray`) / 버블 정렬(`SortedArray`) |
| P11 | **인터페이스** `IStack` → `StringStack implements IStack` (배열 기반 스택) |
| P13 | 추상 클래스 `Calc` → `Add`/`Sub`/`Mul`/`Div` + `Calculator.run()` — **다형성으로 연산자 분기** |

## 핵심 정리

- **`super(...)`는 생성자 첫 줄**. 안 쓰면 컴파일러가 부모의 기본 생성자를 자동 호출하는데,
  부모에 기본 생성자가 없으면 컴파일 오류가 난다 (P1의 `TV(int size)`).
- **`protected`** = 같은 패키지 + 자식 클래스에서 접근 가능. `private` 필드를 자식에게 열어줄 때 쓴다.
- **오버라이딩 vs 오버로딩**
  - 오버라이딩: 시그니처가 **같고** 부모 메소드를 덮어씀 (P6의 `move(int,int)`).
  - 오버로딩: 시그니처가 **다른** 별개의 메소드 (P5의 `move(int,int,int)`).
- **추상 클래스**: 미완성 설계도. `abstract` 메소드를 자식이 반드시 구현.
  **인터페이스**: 구현이 아예 없는 규격. `implements`로 약속을 지킨다.
- **다형성의 실전 형태**가 P13에 있다 — `Calc op;` 한 변수에 `Add`/`Sub`/`Mul`/`Div`를 담고
  `op.calculate()` 한 줄로 호출. `switch`가 객체 생성에만 쓰이고 계산 로직은 각 클래스가 갖는다.

💡 P9/P10은 **같은 부모, 다른 확장**의 좋은 예다. `BaseArray`는 그대로 두고
자식만 갈아끼워 기능을 바꾼다 — 김영한 기본편 `12. 다형성과 설계`와 같은 주제.
