# 학교 수업 — 자바 프로그래밍

1~12주차 강의 예제(`example`)와 실습문제(`practice`)를 주차별로 모아둔 폴더.
각 주차 README에 그 주 코드의 요약 정리가 들어 있다.

## 주차별 정리

| 주차 | 주제 | 핵심 키워드 |
|------|------|-------------|
| [01](1week) | 자바 시작하기 | `main`, `println`, 변수·상수, javadoc |
| [02](2week) | 자바 기본 프로그래밍 | 타입·형 변환, 연산자, `if`/`switch`, `Scanner` |
| [03](3week) | 반복문·배열과 예외 처리 | `for`/`while`, 배열·2차원 배열, `try-catch` |
| [04](4week) | 클래스와 객체 | 생성자, `this`, 캡슐화, 오버로딩, `static` |
| [05](5week) | 상속 | `extends`, `super`, 오버라이딩, 추상 클래스, 인터페이스 |
| [06](6week) | 자바 기본 API와 `Object` 메소드 | `String`, `StringTokenizer`, `Calendar`, `toString`/`equals` |
| [07](7week) | 제네릭과 컬렉션 | `Vector`, `ArrayList`, `HashMap`, `Iterator`, `class G<T>` |
| [08](8week) | 입출력 스트림과 파일 입출력 | 바이트/문자 스트림, 버퍼, `File` |
| [09](9week) | 자바 GUI — 스윙 기초 | `JFrame`, 컨텐트팬, 배치관리자, `JPanel` |
| [10](10week) | 자바 이벤트 처리 | 리스너 4방식, 어댑터, 마우스·키 이벤트 |
| [11](11week) | 스윙 컴포넌트 활용 | 컴포넌트 전반, `ItemEvent`, `ChangeEvent` |
| [12](12week) | 그래픽 | `paintComponent`, `Graphics`, `repaint()` |

## 폴더 구조

```
<N>week/
├── README.md      # 그 주차 요약 정리
├── example/src/   # 강의 예제 (ex1.java, E1.java ...)
└── practice/src/  # 실습문제 (p1.java, P1.java ...)
```

- 1주차는 `example`/`practice` 구분 없이 `src/` 하나.
- 4주차의 예제 폴더는 `example4/`.
- 11주차의 실습문제는 `example/practice/`로 한 단계 더 들어가 있다.
- 6주차는 예제 1개(`example/src/Example.java`)만 있고 나머지는 `practice/`에 있다.
- 컴파일 산출물 `bin/`과 `.vscode/`는 `.gitignore` 대상이라 커밋되지 않는다.

## 실행

VS Code Java Extension Pack 기준. 각 `src/` 폴더를 워크스페이스로 열고 실행할 클래스의
`main`에서 Run.

일부 예제는 추가 조건이 필요하다.

| 주차 | 필요 조건 |
|------|-----------|
| 08 | `c:\Temp` 디렉터리, `c:\Temp\sul.jpg`, `c:\windows\system.ini` 등 하드코딩 경로 |
| 11, 12 | 실행 디렉터리에 `sul.jpg`~`sul4.jpg` (일부 예제는 없는 경로를 참조) |

## 컴파일되지 않는 파일

| 파일 | 문제 |
|------|------|
| [6week Ex12.java](6week/practice/src/Ex12.java) | 작성 중 중단 — 미선언 변수, 오타, `return` 누락 |
| [10week P4.java](10week/practice/src/P4.java) | `c.setLayout(new );` 문법 오류 |
| 2week p11, 7week E9·E11 | 내용 없는 빈 클래스 |
