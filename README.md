# Java

자바 실습 코드와 챕터별 학습 정리를 모아둔 저장소.

- **기본편**: 김영한 — 자바 기본편 🟡 실습 코드 완료 / 정리 진행 예정
- **학교 수업**: 자바 프로그래밍 (1~12주차) 🟡 실습 코드 완료 / 정리 진행 예정

## 디렉토리 구조

```
.
├── basic/                     # 김영한 자바 기본편
│   ├── answer/src/            # 강의 따라 친 정답지 (패키지 = 챕터)
│   ├── java-basic/src/        # 백지에서 다시 작성하는 연습장
│   ├── docs/javaBasic/        # 학습 정리 md
│   └── 강의자료/               # 강의 PDF (git 무시)
└── college/                   # 학교 자바 프로그래밍 수업
    ├── README.md              # 주차 인덱스
    └── 1week ... 12week/      # 주차별 실습 코드 + README(요약 정리)
```

## 학습 정리 진행 상황

### 기본편 — 김영한 자바 기본편

정답지: [`basic/answer/src`](basic/answer/src) — 강의를 따라 친 코드 · 정리 md: `basic/docs/javaBasic/`
연습장: [`basic/java-basic/src`](basic/java-basic/src) — 정답지를 보지 않고 직접 다시 작성해 보는 별도 프로젝트.

<details>
<summary><b>챕터별 정리</b> (1/13)</summary>

| # | 챕터 | 실습 코드 패키지 | 정리 |
|---|------|-----------------|------|
| 01 | 클래스와 데이터 | `class1` | ⬜ |
| 02 | 기본형과 참조형 | `ref` | ⬜ |
| 03 | 객체 지향 프로그래밍 | `oop1` | ⬜ |
| 04 | 생성자 | `construct` | ⬜ |
| 05 | 패키지 | `pack`, `com/helloshop` | ⬜ |
| 06 | 접근 제어자 | `access` | ⬜ |
| 07 | 자바 메모리 구조와 static | `memory`, `static1`, `static2` | [✅](basic/docs/javaBasic/07.%20자바%20메모리%20구조와%20static.md) |
| 08 | final | `final1` | ⬜ |
| 09 | 상속 | `extends1` | ⬜ |
| 10 | 다형성1 | `poly` | ⬜ |
| 11 | 다형성2 | `poly` | ⬜ |
| 12 | 다형성과 설계 | `poly/ex` | ⬜ |
| 13 | 다음으로 | — | ⬜ |

</details>

**부록** (챕터 순번과 무관한 배경 지식 정리)

| 문서 | 내용 |
|------|------|
| [부록A. `new` 인스턴스 생성 과정 — JVM에서 CPU까지](basic/docs/javaBasic/부록A.%20new%20인스턴스%20생성%20과정%20—%20JVM에서%20CPU까지.md) | 바이트코드 → 클래스 로딩 → 힙 할당 → JIT → OS → CPU 전 과정 다이어그램 |

### 학교 수업 — 자바 프로그래밍

전체 인덱스: **[college/README.md](college/README.md)** · 주차별 요약은 각 주차 README에 있다.

<details>
<summary><b>주차별 정리</b> (12/12)</summary>

| 주차 | 주제 | 요약 정리 |
|------|------|-----------|
| 01 | 자바 시작하기 | [1week/README.md](college/1week/README.md) |
| 02 | 자바 기본 프로그래밍 | [2week/README.md](college/2week/README.md) |
| 03 | 반복문·배열과 예외 처리 | [3week/README.md](college/3week/README.md) |
| 04 | 클래스와 객체 | [4week/README.md](college/4week/README.md) |
| 05 | 상속 | [5week/README.md](college/5week/README.md) |
| 06 | 자바 기본 API와 `Object` 메소드 | [6week/README.md](college/6week/README.md) |
| 07 | 제네릭과 컬렉션 | [7week/README.md](college/7week/README.md) |
| 08 | 입출력 스트림과 파일 입출력 | [8week/README.md](college/8week/README.md) |
| 09 | 자바 GUI — 스윙 기초 | [9week/README.md](college/9week/README.md) |
| 10 | 자바 이벤트 처리 | [10week/README.md](college/10week/README.md) |
| 11 | 스윙 컴포넌트 활용 | [11week/README.md](college/11week/README.md) |
| 12 | 그래픽 | [12week/README.md](college/12week/README.md) |

</details>

> 주차별 주제는 각 주차 실습 코드를 실제로 읽고 정리한 것이다. 강의 목차와 표현이 다르면 수정한다.

## 개발 환경

| 항목 | 기본편 | 학교 수업 |
|------|--------|-----------|
| IDE | IntelliJ IDEA | VS Code (Java Extension Pack) |
| 빌드 | 없음 (`src/` 단일 소스 루트) | 없음 (`src/` → `bin/` 컴파일) |
| 주요 주제 | OOP, 상속, 다형성, 설계 | 문법 기초 → 컬렉션 → I/O → Swing GUI |

## 참고

- JPA 학습 정리: [JPA-study](https://github.com/titianfall/JPA-study)
- 정리 md 작성 규칙은 [CLAUDE.md](CLAUDE.md) 참고.
