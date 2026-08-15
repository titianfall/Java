package original;

// 그래픽 및 이벤트 처리관련 import
import java.awt.Color;      // 글자 및 색상 지정을 위해 사용합니다.
import java.awt.Font;       // 제목 및 라벨의 폰트스타일을 설정하기 위해 사용
import java.awt.Graphics;   // 배경 이미지를 직접 그리기 위한 Grphics 객체
import java.awt.Image;      // ImageIcon에서 이미지 추출을 위해 사용

// 버튼 클릭 시 발생하는 이벤트 처리용 인터페이스
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// JLabel을 버튼처럼 사용하기 위해 마우스 이벤트를 처리
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Swing 컴포넌트 import
import javax.swing.ImageIcon;   // 난이도 선택 및 배경 이미지를 아이콘 형태로 사용
import javax.swing.JButton;     // 게임 시작, 단어 추가, 랭킹 버튼
import javax.swing.JLabel;      // 텍스트 표시 및 난이도 선택용
import javax.swing.JPanel;      // StartPanel의 최상위 컨테이너
import javax.swing.JTextField;  // 사용자 닉네임 입력 필드

// 시작 화면 - 게임 실행시 가장 먼저 띄워지는 화면이다.
public class StartPanel extends JPanel {

	// 배경 이미지 관련 필드
	private ImageIcon start = new ImageIcon("images/karmen.jpg");    // 이미지를 로딩하고 저장하는곳
	private Image startImg = start.getImage();                       // ImageIcon 에서 객체를 추출하고 PaintComponent에서 직접 사용한다.

	// 게임 제목 - 보스 이름
	private JLabel title = new JLabel("KARMEN");    // 보스의 이름을 나타내는 라벨

	// 설명 및 닉네임 입력창
	private JLabel user = new JLabel("닉네임 : ");        // 빈칸에 닉네임을 입력하라고 설명하는 문자 라벨
	private JTextField userText = new JTextField(10);   // 유저 아이디를 입력받는 칸

	// 난이도 선택

	// 노말 난이도
	private ImageIcon normalIcon = new ImageIcon("images/normal.jpg");           // 노말 난이도 기본 아이콘
	private ImageIcon normalClicked = new ImageIcon("images/normalClicked.jpg"); // 노말 난이도 선택시 아이콘
	private JLabel normal = new JLabel(normalClicked);                           // 난이도를 노말로 선택용 라벨(기본설정)

	// 하드 난이도
	private ImageIcon hardIcon = new ImageIcon("images/hard.jpg");               // 하드 난이도 기본 아이콘
	private ImageIcon hardClicked = new ImageIcon("images/hardClicked.jpg");     // 하드 난이도 선택시 아이콘
	private JLabel hard = new JLabel(hardIcon);                                  // 난이도를 하드 선택용 라벨

	// 버튼 - 시작, 추가, 랭킹
	private JButton startBtn = new JButton("시작");         // 게임 시작 이동 버튼
	private JButton addText = new JButton("추가");          // 단어 추가 화면 이동 버튼
	private JButton showRangking = new JButton("랭킹");     // 랭킹 화면 이동 버튼

	// 난이도 선택
	private int difficulty = 1;     // 1 노말, 2 하드 난이도입니다. 버튼 선택시 변경 및 전달

	// 생성자
	public StartPanel(TextStore tStore, GameFrame gameFrame, ScorePanel scorePanel) {

		setLayout(null);    // 레이아웃 매니저를 설정하지 않았기 때문에 라벨의 크기를 직접 지정하여야한다.

		// 타이틀 설정
		title.setFont(new Font("Arial", Font.BOLD, 100));   // 폰트 설정
		title.setForeground(new Color(0, 120, 255));        // 색 설정
		title.setSize(500, 200);                            // 텍스트 크기 설정
		title.setLocation(250, 80);                         // 타이틀 위치 설정
		this.add(title);                                    // 제목을 알리는 컴포넌트를 부착한다.

		// 랭킹 저장에 필요한 유저 닉네임을 적어야 함을 알리는 컴포넌트
		user.setFont(new Font("GOTHIC", Font.BOLD, 30));
		user.setForeground(Color.WHITE);    // 흰글씨로 설정
		user.setSize(200, 100);             // 설명 사이즈 설정
		user.setLocation(230, 230);         // 설명 위치 설정
		this.add(user);                     // 설명을 알리는 컴포넌트를 부착한다.

		// 사용자의 닉네임을 적는 컴포넌트
		userText.setSize(200, 30);          // 사이즈 설정
		userText.setLocation(400, 265);     // 위치 설정
		this.add(userText);                 // 닉네임을 적는 컴포넌트 부착

		// 노말 난이도 설정 컴포넌트
		normal.setSize(100, 120);           // 사이즈 설정
		normal.setLocation(350, 330);       // 위치 설정
		this.add(normal);                   // 노말 난이도 설정 버튼 부착
		// 버튼에 익명리스너 설정 - 난이도, 클릭시 아이콘 설정
		normal.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				difficulty = 1;
				normal.setIcon(normalClicked);
				hard.setIcon(hardIcon);
			}
		});

		// 하드 난이도 설정 컴포넌트
		hard.setSize(120, 120);             // 사이즈 설정
		hard.setLocation(550, 330);         // 위치 설정
		this.add(hard);                     // 하드 난이도 설정 버튼 부착
		// 버튼에 익명리스너 설정 - 난이도, 클릭시 아이콘 설정
		hard.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				difficulty = 2;
				normal.setIcon(normalIcon);
				hard.setIcon(hardClicked);
			}
		});

		// 추가 버튼 컴포넌트
		addText.setSize(150, 50);           // 사이즈 설정
		addText.setLocation(400, 500);      // 위치 설정
		add(addText);                       // 단어 추가 패널을 보여주는 버튼 부착
		// 버튼에 익명 리스너 설정 - 단어 편집 화면으로 이동
		addText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showEdit();
			}
		});

		// 시작 버튼 컴포넌트
		startBtn.setSize(150, 50);          // 사이즈 설정
		startBtn.setLocation(400, 600);     // 위치 설정
		add(startBtn);                      // 단어 추가 패널을 보여주는 버튼 부착

		// 버튼에 익명 리스너 설정 - 게임 화면으로 이동
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = userText.getText().trim();    // 입력된 닉네임을 가져와 공백 제거
				scorePanel.setUserName(name);               // 점수 패널에 사용자 이름 설정
				gameFrame.startGame(difficulty);            // 선택된 난이도로 게임 시작
			}
		});

		// 랭킹 화면 이동 버튼
		showRangking.setSize(150, 50);          // 사이즈 설정
		showRangking.setLocation(400, 700);     // 위치 설정
		add(showRangking);                      // 랭킹
		// 버튼에 익명 리스너 설정 - 랭킹 화면으로 이동
		showRangking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showLeaderboard();
			}
		});
	}

	// 배경 이미지 그리기
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 시작 화면 배경 이미지를 패널 크기에 맞게 출력합니다.
		g.drawImage(startImg, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
