package original;

// 파일 입출력 관련 import
import java.io.BufferedReader;         // 텍스트 파일을 한 줄씩 효율적으로 일기 위한 스트림
import java.io.File;                   // 실제 파일 객체를 생성하기 위한 클래스
import java.io.FileNotFoundException;  // 파일이 존재하지 않을 경우 발생하는 예외
import java.io.FileReader;             // 텍스트 파일을 문자 단위로 일기 위한 리더
import java.io.FileWriter;             // 텍스트 파일에 문자 단위로 쓰기 위한 Writer
import java.io.IOException;            // 입출력 과정에서 발생하는 예외 처리
import java.util.Vector;               // 단어를 저장하는 자료구조 Vector
import java.io.BufferedWriter;         // 파일 쓰기 성능 향상을 위한 버퍼 스트림

// 게임에서 사용되는 모든 단어를 관리하는 데이터 저장 클래스
public class TextStore {

	// 멀티스레딩이 실행되면 벡터에 접근이 안되기때문에 벡터를 먼저 선언한다.
	private Vector<String> v = new Vector<String>();

	// 생성자 - 프로그램 시작시 word.txt를 읽어 모든 단어를 메모리에 적재
	public TextStore() {
		try {
			// 단어 파일 객체 생성
			File f = new File("data/word.txt");     // 상대경로 기준으로 파일을 읽는다.

			// 파일 입력 스트림 생성
			BufferedReader br = new BufferedReader(new FileReader(f));
			System.out.println(f.getPath() + "를 벡터에 저장합니다.");    // 로딩 상태 확인용 로그

			String line;
			// 파일의 끝까지 한 줄씩 읽어 Vector에 저장
			while ((line = br.readLine()) != null) {
				v.add(line);
			}

			// 스트림 자원 해제
			br.close();

		} catch (FileNotFoundException e) {     // 파일이 없을경우 발생 시 처리
			e.printStackTrace();
		} catch (IOException e) {               // 파일 입출력중 오류 발생 시 처리
			e.printStackTrace();
		}
	}

	// EditPanel에서 단어를 입력하고 이벤트를 받을경우 단어를 추가해주는 메소드 addText
	public void addText(String text) {
		// 이미 단어가 벡터에 존재한다면 추가하지 않고 바로 돌아갑니다.
		if (v.contains(text)) {
			return;
		}

		// word.txt에 단어를 추가합니다.
		try {
			// 기존 파일 뒤에 이어서 기록하는 BufferWriter 객체 생성
			BufferedWriter bw = new BufferedWriter(new FileWriter("data/word.txt", true));

			bw.write(text);     // 새로운 단어 추가
			bw.newLine();       // 줄바꿈 추가
			bw.close();         // 파일 쓰기 종료 및 자원 해제
		} catch (IOException e) {   // 파일 쓰기 중 오류 발생 시 처리
			System.out.println("입출력 오류");
		}

		v.add(text);    // 벡터에 해당 단어 추가
	}

	// 랜덤으로 출력할 단어를 반환하는 메소드 get
	public String get() {
		int index = (int) (Math.random() * v.size());   // 랜덤한 벡터 크기 내 인덱스 설정

		return v.get(index);    // 해당 인덱스 단어 반환
	}

	// Vector 객체를 가르키는 레퍼런스 반환 - 조회 목적
	public Vector<String> getVector() {
		return v;
	}
}
