# 01주차 — 자바 시작하기

첫 자바 프로그램 작성. `main()` 진입점, 콘솔 출력, 변수·상수 선언, 메소드 호출까지.

실습 코드: [`src`](src)

## 파일별 요약

| 파일 | 내용 |
|------|------|
| [Welcome.java](src/Welcome.java) | `System.out.println()`으로 문자열 출력. `main`에 `throws Exception` 붙은 VS Code 기본 템플릿 형태 |
| [Best.java](src/Best.java) | 한글 문자열 두 줄 출력 |
| [HelloDoc.java](src/HelloDoc.java) | javadoc 주석(`/** */`), `static` 메소드 `sum()`, 기본 타입(`int`/`char`/`String`), `final` 상수 |

## 핵심 정리

- **클래스 = 파일**: `public class`의 이름과 `.java` 파일명이 같아야 한다.
- **진입점**: `public static void main(String[] args)` 하나로 프로그램이 시작된다.
- **출력**: `System.out.println()` — 줄바꿈 포함, `print()`는 줄바꿈 없음.
- **상수**: `final int TEN = 10;` — 한 번 초기화 후 변경 불가.
- **완전한 이름(FQN)**: `java.lang.System.out.println()`처럼 패키지 전체 경로로도 호출 가능.
  `java.lang`은 자동 import되므로 평소엔 생략한다.
- **주석 3종**: `//` 한 줄, `/* */` 여러 줄, `/** */` javadoc(문서 생성용).

💡 `HelloDoc.sum()`이 `static`이라 객체 생성 없이 `main`에서 바로 호출된다 —
`static`의 의미는 [07주차](../7week)·기본편 `07. 자바 메모리 구조와 static`에서 다시 다룬다.
