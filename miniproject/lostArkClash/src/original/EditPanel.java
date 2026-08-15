package original;

// 그래픽 및 이벤트 처리 관련 import
import java.awt.Color;      // 안내 문구 및 텍스트 색상 지정에 사용
import java.awt.Font;       // 라벨 및 텍스트 영역의 폰트 스타일 지정
import java.awt.Graphics;   // 배경 이미지르 직접 그리기 위해 사용
import java.awt.Image;      // ImageIcon에서 실제 이미지 객체를 얻기 위해 사용
import java.awt.Toolkit;    // [해상도 호환] 화면 폭 기준으로 컴포넌트를 배치하기 위해 사용

// 버튼 클릭 이벤트 처리를 위한 인터페이스
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 단어를 저장하는 자료구조 Vector
import java.util.Vector;

// 컴포넌트
import javax.swing.ImageIcon;   // 배경 이미지 로딩을 위한 아이콘 클래스
import javax.swing.JButton;     // 저장 및 돌아가기 버튼
import javax.swing.JLabel;      // 안내 문구 출력 라벨
import javax.swing.JPanel;      // 최상위 컨테이너
import javax.swing.JScrollPane; // 스크롤 기능을 제공하기 위해 사용
import javax.swing.JTextArea;   // 저장된 단어 목록을 출력하는 텍스트 영역
import javax.swing.JTextField;  // 추가하고자 하는 단어 입력 필드

// 게임에서 사용할 단어를 추가/관리하는 화면
public class EditPanel extends JPanel {

	// 안내 및 입력
	private JLabel la = new JLabel("단어를 입력해주세요!");     // 안내 라벨
	private JTextField inputField = new JTextField(80);      // 저장할 단어를 입력할 필드 최대 10글자
	private JButton saveBtn = new JButton("Save");           // 단어를 저장하기 위한 버튼

	private JButton returnBtn = new JButton("돌아가기");       // 시작 화면으로 돌아가는 버튼

	private JTextArea ta = new JTextArea(10, 20);            // 입력된 단어들을 출력하는 텍스트 영역 컴포넌트
	private JScrollPane sc = new JScrollPane(ta);            // 여러개를 입력할 경우 스크롤바를 통해 내려볼수 있는 기능 제공

	// 생성자
	public EditPanel(TextStore textStore, GameFrame gameFrame) {
		setLayout(null);    // 위치를 임의로 설정합니다.

		// [해상도 호환] 원본 좌표(inputField x=1000, saveBtn x=1900, sc 1400x900 @700,100)는
		// 2560 폭 모니터 기준이라 FHD에서는 화면 밖으로 나간다. 화면 중앙 기준 상대 배치로 바꿨다.
		int W = Toolkit.getDefaultToolkit().getScreenSize().width;

		// 안내 문구 라벨 설정
		la.setSize(300, 50);                                // 안냄 문구 사이즈 설정
		la.setLocation(W / 2 - 620, 20);                    // 안내 문구 위치 설정
		la.setOpaque(false);                                // 라벨 글자색 지정을 위해 투명설정 해제
		la.setForeground(Color.WHITE);                      // 글자색 흰색으로 지정
		la.setFont(new Font("GOTHIC", Font.ITALIC, 24));    // 폰트 지정
		add(la);                                            // 안내 문구를 컨테이너에 부착합니다.

		inputField.setSize(700, 50);                        // 단어 필드 사이즈 설정
		inputField.setLocation(W / 2 - 300, 20);            // 단어 필드 위치 설정
		add(inputField);                                    // 입력 필드를 컨테이너에 추가합니다.

		saveBtn.setSize(100, 50);                           // 저장 버튼 사이즈 설정
		saveBtn.setLocation(W / 2 + 420, 20);               // 저장 버튼 위치 설정
		add(saveBtn);                                       // 저장 버튼을 컨테이너에 추가합니다.

		// 버튼에 익명 리스너 설정 - EditPanel의 Vector 에 저장합니다.
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = inputField.getText().trim();  // 공백계열 문자는 앞뒤로 제거합니다. 단어를 맞출때 오작동 방지
				if (text == null || text.trim().isEmpty()) {  // 공백계열 문자만 있거나 아무것도 입력하지 않았을 경우는 저장 방지
					return;
				} else {
					textStore.addText(text);        // 단어를 저장합니다.
					ta.append(text + "\n");         // 추가된 단어를 ta에 추가합니다.
				}
				inputField.setText("");             // 다음 입력을 위해 입력필드 초기화
			}
		});

		returnBtn.setSize(100, 50);         // 되돌아가는 버튼 사이즈 설정
		returnBtn.setLocation(0, 0);        // 되돌아가는 버튼 위치 설정
		add(returnBtn);                     // 되돌아가는 버튼을 컨테이너에 추가합니다.
		// 버튼에 익명 리스너 설정 - 초기 화면을 보이게합니다.
		returnBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showStart();
			}
		});

		Vector<String> v = textStore.getVector();           // 단어를 저장한 벡터의 레퍼런스를 받아옵니다.
		ta.setFont(new Font("Arial", Font.ITALIC, 24));     // 폰트 설정

		// 화면이 생성될때 ta에 메모장의 모든 단어를 채웁니다.
		for (int i = 0; i < v.size(); i++) {
			ta.append(v.get(i) + "\n");
		}

		// 스크롤바를 붙여 출력하도록 한다.
		sc.setSize(1200, 800);                  // ta의 크기 설정
		sc.setLocation(W / 2 - 600, 100);       // ta의 위치 설정
		add(sc);                                // ta컴포넌트를 부착합니다.

		setVisible(true);   // 패널의 컴포넌트들을 보이도록 설정합니다.
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 랭킹 배경 삽입 및 그리기
		ImageIcon Icon = new ImageIcon("images/leaderboard.png");
		Image img = Icon.getImage();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
}
