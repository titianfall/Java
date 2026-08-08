# 07주차 — 제네릭과 컬렉션

`Vector`, `ArrayList`, `HashMap`, `Iterator`, 그리고 제네릭 클래스 직접 만들기.

실습 코드: [`example/src`](example/src) (E1~E11) · [`practice/src`](practice/src) (P1~P7)

## 강의 예제 (example)

| 파일 | 주제 |
|------|------|
| E1 | **`Vector<Integer>`** — `add()`, `add(index, val)`, `get()`/`elementAt()`, `size()` vs `capacity()`, **자동 박싱** |
| E2 | `Vector<Point>` — 객체 저장, `remove(1)`, `var` 타입 추론 |
| E3 | `ArrayList<String>` — 가장 긴 이름 찾기 |
| E4 | **`Iterator`** — `v.iterator()`, `hasNext()`/`next()`. 한 번 끝까지 돌면 재사용 불가라 **다시 얻어야 한다** |
| E5 | `HashMap<String,String>` 영한 사전 — `put()`, `get()`, 없으면 `null` |
| E6 | `HashMap<String,Integer>` + **`keySet()`으로 전체 순회**, `null` 체크 |
| E7 | `HashMap<String,Student>` — **값으로 객체 저장** |
| E8 | `HashMap<String,Vector<Integer>>` — **중첩 제네릭**, 학생별 점수 목록 |
| E9 / E11 | 빈 파일 |
| E10 | **제네릭 클래스 직접 작성** — `class GStack<T>`, `push(T)`, `T pop()`. `String`/`Integer` 스택으로 재사용 |

## 실습문제 (practice)

| 파일 | 문제 |
|------|------|
| P1 | `Vector<Integer>`에 -1까지 입력받아 최솟값 찾기 |
| P2 | 음수를 0으로 바꾸고(`set()`) 합산 — `read`/`changeToZero`/`showAll`/`add`로 메소드 분리 |
| P3 | `HashMap` 주식 종목·주가 저장 후 검색 |
| P4 | `HashMap` 상품 가격표 + `StringTokenizer`로 "물건 개수" 파싱해 장바구니 합계 |
| P7 | `HashMap<String,Location>` — 도시별 경도/위도, `read`/`printAll`/`processQuery` 구조 |

## 핵심 정리

- **제네릭은 컴파일 타임 타입 안전성** — `Vector<Integer>`에 `String`을 넣으면 컴파일 오류.
  꺼낼 때 캐스팅이 필요 없다.
- **자동 박싱/언박싱**: `v.add(5)`는 `v.add(Integer.valueOf(5))`. `int n = v.get(i)`는 자동 언박싱.
- **`size()` vs `capacity()`**: `size()`는 실제 원소 수, `capacity()`는 확보된 배열 크기 (E1).
- **`HashMap.get()`은 없으면 `null`을 리턴** — 곧바로 `int`에 대입하면 `NullPointerException`.
  E6이 `Integer s`로 먼저 받아 `null` 체크하는 이유다. P3은 이 체크가 빠져 있다.
- **`keySet()` + `Iterator`** 가 맵 전체 순회의 기본형. 순서는 보장되지 않는다.
- **제네릭 타입 매개변수 `T`** 로 자료구조를 한 번만 만들고 여러 타입에 재사용 (E10).
  단, `new T[]`는 불가능해서 내부는 `Object[]`로 두고 꺼낼 때 `(T)` 캐스팅한다.
- `var`(Java 10+)는 **지역 변수**에만 쓸 수 있다. 필드나 매개변수엔 불가.

💡 P2·P4·P7이 `main`에 다 넣지 않고 인스턴스 메소드로 나눈 구조 — [09주차 이후 GUI](../9week)에서
프레임 클래스를 짜는 방식과 같다.
