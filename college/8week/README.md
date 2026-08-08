# 08주차 — 입출력 스트림과 파일 입출력

바이트 스트림 vs 문자 스트림, 버퍼 스트림, `File` 클래스.

실습 코드: [`example/src`](example/src) (E1~E11) · [`practice/src`](practice/src) (P1~P10)

## 강의 예제 (example)

| 파일 | 주제 |
|------|------|
| E1 | **`FileReader`** 로 텍스트 읽기, `read()`가 `-1`이면 끝. `FileNotFoundException`을 `IOException`보다 **먼저** catch |
| E2 / E3 | **`InputStreamReader`로 인코딩 지정** — `UTF-8`은 한글 정상, `US-ASCII`는 깨짐. `getEncoding()` |
| E4 | **`FileWriter`** 로 콘솔 입력을 파일에 저장, 빈 줄 입력 시 종료 |
| E5 / E6 | **`FileOutputStream`/`FileInputStream`** — 바이너리(`byte[]`) 읽고 쓰기 |
| E7 | **`BufferedOutputStream`** — 버퍼가 차야 출력되므로 `flush()`로 강제 출력 |
| E8 | **`File` 클래스** — `getPath()`/`getParent()`/`getName()`, `isFile()`/`isDirectory()`, `exists()`, `mkdir()`, `renameTo()`, `listFiles()`, `lastModified()` |
| E9 | 텍스트 파일 복사 (`FileReader` → `FileWriter`) |
| E10 / E11 | **바이너리 파일 복사 성능 비교** — 1바이트씩 vs 10KB 버퍼. `System.currentTimeMillis()`로 시간 측정 |

## 실습문제 (practice)

| 파일 | 문제 |
|------|------|
| P1 | 이름·전화번호를 `phone.txt`에 저장 |
| P2 | `phone.txt`를 읽어 출력 |
| P3 / P3_2 | 파일을 대문자로 변환 출력 — **문자 단위(`Character.toUpperCase`)** vs **줄 단위(`Scanner` + `toUpperCase`)** |
| P4 | 파일을 줄 번호 붙여 출력 |
| P5 | **두 파일 바이트 단위 비교** — 길이·내용이 모두 같은지 |
| P6 | 두 파일을 이어붙여 `append.txt`로 저장 |
| P7 | `BufferedInputStream`/`BufferedOutputStream`으로 복사 + **10%마다 `*` 진행률 출력** |
| P8 | `listFiles()`로 가장 큰 파일 찾기 |
| P9 | 디렉터리의 `.txt` 파일 모두 삭제 (`lastIndexOf` + `delete()`) |
| P10 | `BufferedReader.readLine()` + `StringTokenizer`로 `phone.txt`를 `HashMap`에 적재 후 검색 |

## 핵심 정리

- **바이트 스트림 vs 문자 스트림**

  | | 바이트 | 문자 |
  |---|---|---|
  | 입력 | `FileInputStream` | `FileReader` |
  | 출력 | `FileOutputStream` | `FileWriter` |
  | 용도 | 이미지·실행 파일 등 모든 파일 | 텍스트 전용 (인코딩 변환 수행) |

  이미지를 `FileReader`로 복사하면 **깨진다** — 문자 변환이 끼어들기 때문.
- **`read()`의 리턴은 `int`** — `-1`이 EOF다. `byte`나 `char`로 받으면 EOF 판별이 안 된다.
- **버퍼가 성능을 좌우한다**: E10(1바이트씩) vs E11(10KB 버퍼)의 실행 시간 차이가 그대로 답.
  출력 버퍼는 `close()` 또는 `flush()`를 해야 실제로 파일에 써진다 (E7).
- **`File`은 경로 정보 객체**일 뿐 읽고 쓰지 않는다 — P4 주석대로 `f.close()`는 존재하지 않는다.
- **`FileNotFoundException`은 `IOException`의 자식** — catch 순서가 반대면 컴파일 오류.

⚠️ 예제 대부분이 `c:\Temp\...`, `c:\windows\system.ini` 같은 **하드코딩 경로**를 쓴다.
다른 환경에서 실행하려면 경로를 고치거나 `c:\Temp` 디렉터리를 먼저 만들어야 한다.
E10/E11은 `c:\Temp\sul.jpg`가 필요하다 (저장소에는 각 주차 폴더에 `sul*.jpg`가 들어 있음).

⚠️ P6은 `read()`가 `-1`을 리턴할 때 `write(buf, 0, -1)`을 호출해 예외가 날 수 있다.
P5처럼 `if(n == -1) break;`를 먼저 두는 편이 안전하다.
