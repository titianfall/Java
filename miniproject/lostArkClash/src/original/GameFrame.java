package original;

// 이벤트 처리관련 import
import java.awt.BorderLayout;             // 상 하 배치를 위해 사용하는 레이아웃 매니저
import java.awt.CardLayout;               // 화면 전환을 위한 레이아웃 매니저
import java.awt.event.ActionEvent;        // 클릭이나 엔터를 통한 이벤트를 수행하기 위한 이벤트 객체
import java.awt.event.ActionListener;     // 이벤트 리스터 인터페이스

// 파일 및 오디오 처리 관련 import
import java.io.File;                      // 오디오 파일 객체
import java.io.IOException;               // 입출력 예외 처리

// 사운드 재생 관련 import
import javax.sound.sampled.AudioInputStream;  // 오디오 입력 스트림
import javax.sound.sampled.AudioSystem;       // 오디오 시스템 접근
import javax.sound.sampled.Clip;              // 사운드 클립 객체
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// J컴포넌트 import
import javax.swing.JButton;               // 버튼
import javax.swing.JFrame;                // 최상위 윈도우 프레임
import javax.swing.JMenu;                 // 메뉴
import javax.swing.JMenuBar;              // 메뉴바
import javax.swing.JMenuItem;             // 메뉴 아이템
import javax.swing.JPanel;                // 패널
import javax.swing.JToolBar;              // 툴바

// 게임 전체를 감싸는 메임 프레임 클래스
public class GameFrame extends JFrame {

	// 화면 전환을 위한 cardLayout 활용 하나의 컨테이너에 여러 화면을 올려두고 필요할때 전환합니다.
	private CardLayout cardLayout = new CardLayout();          // 카드레이아웃 객체 생성
	private JPanel container = new JPanel(cardLayout);         // 해당하는 화면을 띄울 객체 생성

	// 카드로 관리되는 주요 화면 패널
	private StartPanel startPanel;              // 게임 시작 화면
	private EditPanel editPanel;                // 단어 추가 화면
	private LeaderboardPanel leaderboardPanel;  // 랭킹 화면
	private ClashPanel clashPanel;              // 격돌 화면
	private GamePanel gamePanel;                // 실제 게임 진행 화면

	private GameContainerPanel gameContainerPanel; // gamePanel, scorePanel을 모두가진 컨테이너
	private BattleItemPanel battleItemPanel;       // 사용자 hp 및 사용 가능한 아이템을 가지는 패널

	private ScorePanel scorePanel = new ScorePanel(); // 점수를 가르키는 패널 객체
	private TextStore tStore = new TextStore();       // 텍스트 저장소 객체 생성

	// 메뉴 및 툴바 버튼 - 지역변수로 만들어놓으면 접근이 좀 까다롭다.
	private JMenuItem startItem = new JMenuItem("Start");   // 메뉴바에 붙이는 시작 버튼
	private JButton startBtn = new JButton("Start");        // 툴바에 붙이는 시작 버튼

	private JMenuItem stopItem = new JMenuItem("stop");     // 메뉴바에 붙이는 일시 정지 버튼
	private JButton stopBtn = new JButton("stop");          // 툴바에 붙이는 일시 정지 버튼

	private JMenuItem exitItem = new JMenuItem("exit");     // 메뉴바에 붙이는 게임 종료 버튼
	private JButton exitBtn = new JButton("exit");          // 툴바에 붙이는 게임 종료 버튼

	private JToolBar tBar;                  // 버튼을 붙일 툴바

	private boolean clashActive = false;    // 격돌 중복 호출 방지
	private int shrinkSpeed = 1;            // 격돌 난이도(원이 줄어드는 속도), 기본설정 1 (노말)

	// 음악 제어 메뉴
	private JMenuItem play = new JMenuItem("play");                 // 음악 재생
	private JMenuItem musicPause = new JMenuItem("music pause");    // 음악 일시정지
	private JMenuItem playAgain = new JMenuItem("play again");      // 음악 처음부터 재생

	// 배경 음악 클립
	private Clip clip;

	// 전체 UI 및 게임 구조 초기화
	public GameFrame() {

		super("카멘");                                          // 제목 설정
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // 창 닫을시 프로그램 종료

		// 전체화면 모드로 실행
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		makeMenu();     // 메뉴바 생성
		makeToolBar();  // 툴바 생성

		// 각 패널 객체 생성
		startPanel = new StartPanel(tStore, this, scorePanel);
		editPanel = new EditPanel(tStore, this);
		leaderboardPanel = new LeaderboardPanel(this);

		// 게임 패널 생성(점수, 단어 저장, 격돌, 음악 관리를 위한 참조 전달)
		gamePanel = new GamePanel(scorePanel, tStore, this, leaderboardPanel);

		// 배틀 아이템 패널 생성(사용자HP, 배틀아이템 사용을 위해 참조 전달)
		battleItemPanel = new BattleItemPanel(gamePanel.getUserHp(), gamePanel);

		// battleItemPanel을 생성하기 전에 battleItemPanel 레퍼런스가 필요해서
		gamePanel.setBattleItemPanel(battleItemPanel);

		// 게임 화면을 구성하는 컨테이너 생성
		gameContainerPanel = new GameContainerPanel(scorePanel, gamePanel, battleItemPanel);

		// 격돌 패널 생성
		clashPanel = new ClashPanel(scorePanel, gamePanel, shrinkSpeed);

		// 카드레이아웃에 패널을 등록합니다.
		container.add(startPanel, "START");
		container.add(editPanel, "EDIT");
		container.add(gameContainerPanel, "GAME");
		container.add(clashPanel, "CLASH");
		container.add(leaderboardPanel, "LEADERBOARD");

		// 최상위 레이아웃 구성
		JPanel root = new JPanel(new BorderLayout());
		root.add(tBar, BorderLayout.NORTH);         // 상단 툴바
		root.add(container, BorderLayout.CENTER);   // 중앙 화면
		setContentPane(root);

		showStart();    // 시작 화면 표시

		loadAudio("music/lobby.wav");   // 기본 배경음악 로드
		clip.start();                   // 기본 배경음악 재생

		setVisible(true);   // 모든 초기화 완료 후 화면 표시
	}

	// 카드 레이아웃 화면 전환 메소드
	public void showLeaderboard() {
		cardLayout.show(container, "LEADERBOARD");
	}

	public void showStart() {
		cardLayout.show(container, "START");
	}

	public void showEdit() {
		cardLayout.show(container, "EDIT");
	}

	public void showGame() {
		cardLayout.show(container, "GAME");
	}

	public void showClash() {
		System.out.println("격돌 실행!");
		cardLayout.show(container, "CLASH");

		revalidate();
		repaint();

	}

	// 격돌 제어를 위한 메소드
	public boolean isClashActive() {
		return clashActive;
	}

	// 격돌 시작시 실행되는 메소드
	public void startClash() {
		if (clashActive) return;    // 중복 실행 방지
		clashActive = true;         // 격돌 진행중임을 알리는 플래그

		showClash();                        // 격돌 화면으로 전환합니다.
		clashPanel.startClashSequence();    // 포커스 및 격돌 스레드 시작
	}

	// 격돌 성공 실패와 관계없이 격돌 종료시 실행되는 메소드
	public void endClash() {
		clashActive = false;    // 플래그를 초기화하여 다음 격돌을 실행할수 있도록 초기화
		showGame();             // 게임 화면으로 전환합니다.
	}

	// 게임 시작
	public void startGame(int difficulty) {
		gamePanel.init(difficulty);     // 난이도 초기화
		showGame();                     // 화면을 게임 화면으로 바꾼다.
		start();                        // 게임 시작
	}

	// 격돌 난이도(속도)를 외부에서 설정하기 위한 메소드
	public void configureClashSpeed(int shrinkSpeed) {
		// 격돌 패널 내부에서 사용하는 값을 외부에서 조절합니다.
		clashPanel.setShrinkSpeed(shrinkSpeed);
	}

	// 게임 시작 기능을 가진 메소드를 호출한다.
	private void start() {
		gamePanel.start();
	}

	// 게임 일시 중지 기능을 가진 메소드를 호출한다.
	private void stop() {
		gamePanel.stop();
	}

	// 게임 강제 종료 기능을 가진 메소드를 호출한다.
	private void exit() {
		gamePanel.exit();
	}

	// 메뉴바를 만드는 메소드 makeMenu()
	private void makeMenu() {
		JMenuBar mBar = new JMenuBar();  // 메뉴바 객체 생성

		this.setJMenuBar(mBar);          // 프레임에 메뉴바 장착

		JMenu fileMenu = new JMenu("file");  // file이라는 메뉴 생성
		mBar.add(fileMenu);                  // 메뉴바에 추가

		fileMenu.addSeparator();     // 구분선을 그려준다.
		fileMenu.add(startItem);     // start 버튼을 통해 게임을 시작하고
		fileMenu.add(stopItem);      // stop 버튼을 통해 게임을 일시중단하거나
		fileMenu.add(exitItem);      // 게임을 완전히 종료할수 있다.

		// Start라는 메뉴를 클릭할 경우 게임 시작 메소드를 호출한다.
		startItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				start();    // 코드를 중복하지 않고 이곳으로 가도록 해야한다.
			}
		});

		// 일시정지 메뉴를 클릭하는 경우이다.
		stopItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();     // 코드를 중복 작성하지 않도록 한다.
			}
		});

		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();     // 코드를 중복 작성하지 않도록 한다.
			}

		});

		JMenu controlMusic = new JMenu("music");  // music 메뉴 생성
		mBar.add(controlMusic);                   // 메뉴바에 추가

		MyActionListener al = new MyActionListener();   // 커스텀액션리스너 객체 생성

		// 음악을 관리하는 버튼을 메뉴에 장착하고 리스너를 추가합니다.
		controlMusic.add(play);
		play.addActionListener(al);
		controlMusic.add(musicPause);
		musicPause.addActionListener(al);
		controlMusic.add(playAgain);
		playAgain.addActionListener(al);
	}

	// 툴바 생성
	private void makeToolBar() {
		tBar = new JToolBar();   // 툴바 객체 생성 컴포넌트를 붙일수 있다.

		// 시작버튼을 툴바에 붙이고 리스너를 만든다.
		tBar.add(startBtn);
		startBtn.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {
				start();    // 게임을 시작합니다.
			}
		});

		// 정지버튼을 툴바에 붙이고 리스너를 만든다.
		tBar.add(stopBtn);
		stopBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();     // 게임을 멈춥니다.
			}
		});

		// 종료버튼을 툴바에 붙이고 리스너를 만든다.
		tBar.add(exitBtn);
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();     // 게임을 종료합니다.
			}
		});

		// 마우스로 잡고 움직이는 것을 핸들이라고 한다.
		tBar.setFloatable(false);   // 툴바가 마우스로 끌려서 이동하는 기능(핸들)을 비활성화합니다.
	}

	// 배경음악 제어 시작 정지

	// 배경음악 재생 제어 메소드
	public void playBgm(String path, boolean loop) {

		// 기존 음악이 재생 중이면 정지 및 리소스 해제
		if (clip != null) {
			clip.stop();
			clip.close();
		}

		// 새로운 음악 파일 로드
		loadAudio(path);

		// 반복 재생 여부 판단
		if (loop) {

			// 무한 반복 재생
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else {

			// 단발
			clip.start();
		}
	}

	// 배경 음악 정지 메소드
	public void stopBgm() {
		// 음악이 재생 중일 때만 정지

		if (clip != null && clip.isRunning()) {
			clip.stop();
		}
	}

	// 오디오 파일 로드 메소드
	private void loadAudio(String pathName) {
		try {
			// 비어있는 오디오 클립 생성
			clip = AudioSystem.getClip();

			// 파일 경로명으로부터 오디오 파일 객체 생성
			File audioFile = new File(pathName);

			// 오디오 스트림 생성
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

			// 클립에 오디오 데이터 로드
			clip.open(audioStream);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 음악 메뉴 전용 ActionListener 인터페이스
	class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 메뉴 이름에 따라 동작
			switch (e.getActionCommand()) {
			case "play":            // 음악 재생
				clip.start();
				break;
			case "music pause":     // 음악 일시 정지
				clip.stop();
				break;
			case "play again":      // 음악 재생 위치를 처음으로 이동
				clip.setFramePosition(0);
				clip.start();
				break;
			}
		}
	}
}
