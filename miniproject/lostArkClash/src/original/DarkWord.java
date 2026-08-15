package original;

// 그래픽 관련 import
import java.awt.Color;   // 각 글자 라벨의 배경색 및 글자색을 설정하기 위해 사용
import java.awt.Font;    // 암흑 패턴에서 출력되는 글자의 폰트 스타일을 지정하기 위해 사용

import javax.swing.JLabel;   // 단어의 각 글자를 화면에 출력하기 위한 컴포넌ㅌ,

// 암흑 모드에 단어를 띄우기 위해 하나의 단어를 여러개의 라벨로 나누는 작업을 하는 클래스
class DarkWord {

	// 단어
	private String text;            // 원본 단어
	private JLabel[] textLabels;    // 단어를 거꾸로 저장하고 레퍼런스를 반환할 JLabel 배열

	// 생성자
	public DarkWord(String text) {
		this.text = text;                           // 단어 초기화
		textLabels = new JLabel[text.length()];     // 배열을 단어의 개수만큼 생성한다.

		createTextLabels();     // 단어를 레이블 배열로 생성한다.
	}

	// 단어 하나당 하나의 레이블을 가지도록 레이블 생성
	public void createTextLabels() {
		// 단어 알파벳 개수만큼 반복
		for (int i = 0; i < text.length(); i++) {

			textLabels[i] = new JLabel(text.substring(i, i + 1));            // 문자열의 i번째 문자만 잘라 JLabel로 생성
			textLabels[i].setFont(new Font("Arial", Font.ITALIC, 50));      // 폰트 설정
			textLabels[i].setOpaque(true);                                  // 배경을 그리기 위해 opaque 설정
			textLabels[i].setBackground(Color.WHITE);                       // 배경 설정
			textLabels[i].setForeground(Color.BLACK);                       // 글자 설정
			textLabels[i].setSize(50, 50);                                  // 사이즈 설정
		}
	}

	// 라벨 리스트를 가르키는 레퍼런스 반환
	public JLabel[] getTextLabels() {
		return textLabels;
	}
}
