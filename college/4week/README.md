# 04주차 — 클래스와 객체

클래스 설계, 생성자, `this`, 접근 제어자, 메소드 오버로딩, `static` 멤버.

실습 코드: [`example4/src`](example4/src) (교재 확인 예제) · [`practice/src`](practice/src) (P1~p12)

## 강의 예제 (example4)

| 파일 | 주제 |
|------|------|
| p208 | `Human` 클래스 — 생성자 오버로딩(기본/이름), **객체 배열** `new Human[5]` 후 원소마다 `new` |
| p214 | `static double getSum(double[])` — 배열을 매개변수로 받는 메소드 |

## 실습문제 (practice)

| 파일 | 클래스 | 배운 것 |
|------|--------|---------|
| P1 | `TV` | `private` 필드 + `public` 메소드, **`this()` 생성자 체이닝** (`this("LG",32,100)`) |
| P2 | `Cube` | `getVolume()`, `increase()`, `isZero()` — 상태를 바꾸는 메소드와 조회 메소드 |
| P3 | `Grade` | 생성자로 초기화 + getter(`getName`, `getAverage`) |
| p4 | `Average` | 내부 배열 + `index`로 저장 위치 관리, `put`/`showAll`/`getAvg` |
| p5 | `Song` | 4개 필드, 기본 생성자가 `this("","",0,"")` 호출 |
| p6 | `Rectangle` | **객체를 매개변수로 받는 메소드** `contains(Rectangle r)` — 다른 객체의 `private` 필드에 접근 가능 |
| p7 | `Memo` | `isSameName(Memo m)`, `length()` |
| p8 | `Account` | **메소드 오버로딩** — `deposit(int)` / `deposit(int[])`, 잔액 초과 시 부분 인출 |
| p11 | `ArrayUtil` | 전부 `static` — 객체 없이 `ArrayUtil.concat(a,b)`로 호출 |
| p12 | `Dictionary` | `static` 배열 + `static` 메소드로 한→영 사전 |

## 핵심 정리

- **캡슐화**: 필드는 `private`, 접근은 메소드로. p6·p7처럼 **같은 클래스의 다른 객체**는
  `r.x`처럼 `private` 필드에 직접 접근할 수 있다 (접근 제어는 클래스 단위).
- **`this`의 두 용법**
  - `this.name = name` — 매개변수와 필드 이름이 겹칠 때 필드를 가리킴.
  - `this(...)` — **다른 생성자 호출**. 반드시 생성자 첫 줄이어야 한다.
- **오버로딩**은 매개변수의 타입·개수로 구분된다. 리턴 타입만 다른 건 오버로딩이 아니다.
- **`static`** 멤버는 객체가 아니라 클래스에 속한다 — `ArrayUtil.print(a)`, `Dictionary.kor2Eng(w)`.
- **객체 배열**은 두 단계: `Human[] h = new Human[5]`는 참조 5칸만 만들고 전부 `null`.
  각 칸에 `h[i] = new Human()`을 해야 실제 객체가 생긴다.

⚠️ p7의 `isSameName`이 `name == m.name`으로 문자열을 비교한다 — 리터럴이라 우연히 동작할 뿐
`.equals()`가 맞다. [06주차 EX1](../6week)에서 `equals` 오버라이딩으로 다시 다룬다.
