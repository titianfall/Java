# 03주차 — 반복문·배열과 예외 처리

`for`/`while`/`do-while`, 1차원·2차원 배열, `Math.random()`, 그리고 `try-catch` 예외 처리.

실습 코드: [`example/src`](example/src) (ex1~ex18) · [`practice/src`](practice/src) (p1~p17)

## 강의 예제 (example)

| 파일 | 주제 |
|------|------|
| ex1 | `for`로 1~10 누적합, `1+2+...+10=55` 형태 출력 |
| ex2 | `while` — 센티널(-1) 입력까지 평균 계산 |
| ex3 | `do-while` — `'a'`~`'z'` 출력 (`char` 산술) |
| ex4 | 중첩 `for` — 구구단 표 |
| ex5 / ex6 | `continue` / `break` |
| ex7 / ex8 | 1차원 배열 — 최댓값, 평균 |
| ex9 | **향상된 for(`for-each`)** — 배열, `String[]`, `enum Week` 순회 |
| ex10 | 2차원 배열 리터럴, `arr.length` / `arr[i].length` |
| ex11 | **비정방형(ragged) 배열** — `new int[4][]` 후 행마다 다른 길이 할당 |
| ex12 | 메소드가 배열을 리턴 (`static int[] makeArray()`) |
| ex13 | `main(String[] args)`의 명령행 인자를 `Double.parseDouble`로 합산 |
| ex14 / ex15 | 0으로 나누기 → `ArithmeticException`, `try-catch`로 재입력 루프 |
| ex16 | `ArrayIndexOutOfBoundsException` |
| ex17 | `InputMismatchException` — 잘못된 입력 후 `scanner.nextLine()`으로 **버퍼 비우기** |
| ex18 | `NumberFormatException` — `"3.141592"`를 `Integer.parseInt` 시도 |

## 실습문제 (practice)

| 파일 | 문제 |
|------|------|
| p1 | 0~99 짝수 합 (`while`) |
| p2 / p4 | 비정방형 2차원 배열 출력 |
| p3 | 역삼각형 `*` 출력 (중첩 `for`) |
| p5 / p6 | 정수 10개 중 3의 배수 / 자릿수 합이 9인 수 찾기 |
| p7 / p8 | `Math.random()`으로 배열 채우고 평균 |
| p9 / p10 | 4x4 랜덤 배열, **임계값 기준 이진화**(255/0) |
| p11 | 구구단 퀴즈 — 3번 틀리면 종료 |
| p12 | 작명 프로그램 — 성별에 따라 이름 배열에서 랜덤 조합 |
| p13 | 과목명 → 학점 검색 (두 배열을 인덱스로 짝지음) |
| p14 | 갬블링 게임 — 랜덤 3개가 모두 같으면 성공 |
| p15 | `InputMismatchException` 처리 후 재입력 |
| p16 | `Integer.parseInt` + `NumberFormatException`으로 잘못된 입력 걸러 평균 |
| p17 | 커피 주문 — 메뉴 검색 + 잔 수 입력 예외 처리 |

## 핵심 정리

- **`Math.random()`** 은 `[0.0, 1.0)` — `(int)(Math.random()*n)+start` 관용구로 범위 지정.
- **배열 길이**는 `arr.length` (괄호 없음). `String`은 `.length()` (괄호 있음).
- **비정방형 배열**: `new int[4][]`처럼 행 개수만 정하고 행마다 길이를 따로 줄 수 있다.
- **예외 처리 3형태**: 잡고 재시도(ex15), 잡고 건너뛰기(p16), 잡고 종료(ex18).
- ⚠️ **`InputMismatchException` 후 버퍼 비우기**: `nextInt()`가 실패하면 잘못된 토큰이 버퍼에 그대로 남아
  무한루프가 된다. `catch` 안에서 반드시 `scanner.nextLine()`으로 비운다 (ex17, p15, p17).
- `try` 밖에서 선언한 변수만 `catch`에서 쓸 수 있다 — ex18의 인덱스 `i`가 밖에 선언된 이유.
