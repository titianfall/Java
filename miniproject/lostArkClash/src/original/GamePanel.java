package original;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// 게임의 핵심 로직을 담당하는 메인 패널
public class GamePanel extends JPanel {

	// 동시에 화면에 존재할 수 있는 최대 단어 개수
	private final int MAX_SIZE = 20;

	// 단어와 관련된 라벨과 스레드
	private JLabel[] fallingLabel = new JLabel[MAX_SIZE];               // 떨어지는 레이블개수만큼 할당
	private FallingThread[] fThread = new FallingThread[MAX_SIZE];      // 스레드의 개수만큼 할당

	// 떨어지는 단어를 주기마다 만들어내는 스레드
	private MakeWordThread mwThread;

	// 커스텀 JLabel HealthPoint 객체
	private HealthPoint userHp;     // 사용자 hp 데미지를 입거나 회복합니다.
	private HealthPoint bossHp;     // 보스 hp 데미지를 입거나 회복합니다.

	// 사용자hp 및 보스hp 스레드
	private HealthThread userHpThread;
	private HealthThread bossHpThread;

	// 암흑 상태 관리 스레드
	private DarkModeController darkModeController;

	// 패널들
	private GroundPanel groundPanel;                // 단어가 떨어지는 패널이다.
	private ScorePanel scorePanel = null;           // 점수와 정산게이지를 관리하기 위한 패널
	private InputPanel inputPanel;                  // 단어의 입력을 받는 패널
	private BattleItemPanel battleItemPanel;        // 배틀아이템, 상태아이콘 및 HP를 관리하는 패널
	private GameFrame gameFrame;
	private LeaderboardPanel leaderboardPanel;      // 랭킹 저장을 위해 저장하는 객체

	// 단어를 랜덤으로 가져오기 위한 저장소
	private TextStore tStore = null;

	// 단어가 일정한 주기로 생성되기 위해 필요한 필드들이다.
	private final long DELAY = 2000;    // 단어가 생성 주기를 설정하는 필드
	private long lastSpawnTime = 0;     // 마지막으로 생성된 단어의 시간을 저장하는 필드
	private long timePaused = 0;        // 정지 시점의 시간을 기록하는 필드

	// 그래픽으로 삽입할 배경화면 노말/하드
	private ImageIcon normalIcon = new ImageIcon("images/normalBackground.jpg");  // 노말모드 배경을 로딩하고 저장한다.
	private ImageIcon hardIcon = new ImageIcon("images/hardBackground.jpg");      // 하드모드 배경을 로딩하고 저장한다.

	// 난이도 파라미터들
	private int wordFallSpeed;      // 단어 낙하 속도
	private int bossMaxHp;          // 보스 최대 HP 설정
	private int missDamage;         // 단어를 못치거나 잘못쳤을때의 데미지

	private int clashShrinkSpeed;   // 격돌시 원이 줄어드는 속도
	private int darkGaugeSpeed;     // 암흑 게이지 증가 속도

	// 기본 공격력
	private int attack = 20;

	public int basicScore = 5;              // 기본 점수
	public boolean atropineFlag = false;    // 아드로핀 사용 여부 데미지 2배

	// 격돌 제어
	private boolean clash66Triggered = false;   // 중복 격돌 제어를 위한 플래그 - 66퍼
	private boolean clash33Triggered = false;   // 중복 격돌 제어를 위한 플래그 - 33퍼
	private boolean inClash = false;            // 격돌 진행중임을 알리는 플래그

	private int difficulty = 1;     // 난이도

	private String bg = "music/backgroundMusic.wav";    // 게임내 배경음악

	// 생성자로서 점수패널, 단어 저장, 게임 프레임의 레퍼런스를 저장한다.
	public GamePanel(ScorePanel scorePanel, TextStore tStore, GameFrame gameFrame, LeaderboardPanel leaderboardPanel) {
		this.scorePanel = scorePanel;
		this.gameFrame = gameFrame;
		this.tStore = tStore;
		this.leaderboardPanel = leaderboardPanel;

		// HP 객체 생성
		this.userHp = new HealthPoint(200, false, this);
		this.bossHp = new HealthPoint(bossMaxHp, true, this);

		setLayout(new BorderLayout());  // 전체 레이아웃은 상/중/하 구조

		this.groundPanel = new GroundPanel();   // 실제로 단어가 떨어지는 패널이다.
		this.inputPanel = new InputPanel();     // 단어를 입력하는 패널

		add(inputPanel, BorderLayout.SOUTH);    // 단어를 입력하는 패널을 아래로 배치한다.
		add(groundPanel, BorderLayout.CENTER);  // 단어가 떨어지는 패널은 그이외의 화면이다.

	}

	// 보스 HP 감소 시에만 호출됨
	public synchronized void onBossDamaged(int currentHp, int maxHp) {
		if (inClash) return;

		double ratio = (double) currentHp / maxHp;

		// 66% 구간 격돌
		if (ratio <= 0.66 && !clash66Triggered) {
			System.out.println("66퍼 격돌");
			clash66Triggered = true;
			startClash();
		}

		// 33% 구간 격돌
		if (ratio <= 0.33 && !clash33Triggered) {
			System.out.println("33퍼 격돌");
			clash33Triggered = true;
			startClash();
		}
	}

	// 격돌 시작
	public void startClash() {
		if (inClash) return;

		inClash = true;

		// 일반 게임 스레드 정지
		stop();     // mwThread fThead 전부 pause 상태로 만든다.

		// EDT에서 화면 전환 요청
		SwingUtilities.invokeLater(() -> {
			gameFrame.startClash();
		});
	}

	// 격돌 종료
	public void endClash() {

		inClash = false;

		start();    // 일반 게임 스레드 생성
		SwingUtilities.invokeLater(() -> {

			gameFrame.endClash();
			requestInputFocus();
		});
	}

	// 입력 포커스 요청
	public void resumeAfterClash() {

		mwThread.resumeThread();

		for (FallingThread ft : fThread) {
			if (ft != null) ft.resumeThread();
		}

		userHpThread.resumeThread();
		bossHpThread.resumeThread();
	}

	// 격돌 진행중임을 알리는 메소드
	public boolean isInClash() {

		return inClash;
	}

	// 암흑 패턴 컨트롤러 생성
	public void setBattleItemPanel(BattleItemPanel battleItemPanel) {
		this.battleItemPanel = battleItemPanel;

		this.darkModeController = new DarkModeController(this, groundPanel, scorePanel, battleItemPanel, tStore);
	}

	// 입력 포커스 요청
	public void requestInputFocus() {
		SwingUtilities.invokeLater(() -> {
			inputPanel.focusInput();
		});
	}

	// 게임 프레임 객체를 전달하는 getter
	public GameFrame getGameFrame() {
		return gameFrame;
	}

	// 설정된 난이도 초기화
	public void init(int difficulty) {

		this.difficulty = difficulty;
		applyDifficulty(difficulty);

		bossHp.setMaxHp(bossMaxHp);
		bossHp.reset();

		resetClashFlags();
		groundPanel.setMode();
	}

	// 난이도 적용
	private void applyDifficulty(int difficulty) {
		// HARD
		if (difficulty == 2) {
			wordFallSpeed = 16;
			bossMaxHp = 600;
			missDamage = 10;
			clashShrinkSpeed = 2;
			darkGaugeSpeed = 2;

		}
		// NORMAL
		else {
			wordFallSpeed = 10;
			bossMaxHp = 400;
			missDamage = 5;
			clashShrinkSpeed = 1;
			darkGaugeSpeed = 1;
		}

		gameFrame.configureClashSpeed(clashShrinkSpeed);
	}

	// Frame에서 생성자 호출을 위한 레퍼런스를 돌려주는 중개 메소드
	public HealthPoint getUserHp() {
		return userHp;
	}

	public HealthPoint getBossHp() {
		return bossHp;
	}

	// 사망 처리
	public void onDeath(boolean userDead) {

		stop();     // 모든 게임 관련 스레드 정지

		String title;
		String message;

		// 사망 주체에 따라 메시지 분기
		if (userDead) {
			title = "Game Over";
			message = "플레이어가 사망했습니다.";
		} else {
			title = "Victory";
			message = "보스를 처치했습니다!";
		}

		// 결과 다이얼로그 출력
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);

		// 게임 상태 정리 및 랭킹 저장
		exit();

		// 시작 화면으로 복귀
		gameFrame.showStart();
	}

	// 아드로핀 사용 시 10초 동안 데미지 및 점수 2배
	public void resetClashFlags() {

		clash66Triggered = false;
		clash33Triggered = false;
	}

	public void damageBoost() {
		SwingUtilities.invokeLater(() -> {

			// 이미 부스트 상태라면 중복 사용 방지
			if (atropineFlag) return;

			atropineFlag = true;

			int originalAttack = attack;

			// 공격력 2배
			attack = attack * 2;

			// 10초 후 원래 공격력으로 복구
			new Thread(() -> {

				try {
					Thread.sleep(10000);    // 10초 유지

				} catch (InterruptedException e) {
					return;

				}

				SwingUtilities.invokeLater(() -> {
					attack = originalAttack;
					atropineFlag = false;

					// [PDF 조판] 원본 보고서에서는 이 줄이 damageBoost() 밖(클래스 본문)에 떠 있어 그대로 두면 컴파일되지 않는다.
					// 문맥상 아드로핀 효과가 끝난 뒤 상태 아이콘을 되돌리는 자리로 판단해 이 위치에 두었다.
					battleItemPanel.changeImageToNormal();

				});
			}).start();
		});
	}

	// 파괴 폭탄 - 화면에 존재하는 단어를 최대 5개까지 랜덤하게 지워줍니다.
	public void destructionBomb() {
		SwingUtilities.invokeLater(() -> {
			int destroyCount = 0;

			for (int i = 0; i < fThread.length && destroyCount < 5; i++) {
				int randIndex = (int) (Math.random() * 20);     // 0 ~ 19까지 인덱스 중 랜덤 인덱스 설정
				if (fThread[randIndex] != null && fallingLabel[randIndex].isVisible()) {
					fThread[randIndex].interrupt();
					fThread[randIndex] = null;

					fallingLabel[randIndex].setText("");
					fallingLabel[randIndex].setVisible(false);

					bossHp.takeDamage(attack);
					scorePanel.increase(basicScore);

					destroyCount++;
					if (destroyCount == 5) break;   // 5개의 파괴를 완료했다면 더이상 진행하지 않고 돌아온다.
				}
			}
		});
	}

	// 게임시작시 플래그를 초기화하고 단어를 만드는 스레드를 호출하는 메소드 start

	public void start() {
		if (mwThread == null) {
			gameFrame.playBgm(bg, true);

			mwThread = new MakeWordThread();    // 2초마다 단어를 만드는 스레드를 생성합니다.
			mwThread.startThread();             // 단어를 만드는 스레드를 스케줄링 가능하도록 상태를 변경합니다.

			inputPanel.changeStateInput(true);  // 입력 제한 상태를 풉니다.
			requestInputFocus();                // 키보드 입력을 요청합니다.

			scorePanel.startGame();             // 점수 및 게이지 스레드를 시작합니다.

			userHpThread = new HealthThread(userHp);    // 유저 hp를 관리하는 스레드를 생성합니다.
			bossHpThread = new HealthThread(bossHp);    // 보스 hp를 관리하는 스레드를 생성합니다.

			userHpThread.startThread();     // 사용자 스레드 내부의 플래그를 바꾸고 스케줄링이 가능하도록 상태를 변경합니다.
			bossHpThread.startThread();     // 보스 스레드 내부의 플래그를 바꾸고 스케줄링이 가능하도록 상태를 변경합니다.

			bossHp.setSize(groundPanel.getWidth(), 40);
			bossHp.setLocation(0, 0);

		} else {
			// 진행중인 상태에서 일시 정지가 풀리는 start 버튼 클릭 시
			long elapsedTime = System.currentTimeMillis() - timePaused;  // 정지된 시간을 계산합니다.

			lastSpawnTime += elapsedTime;   // 정지된 시간만큼 마지막으로 생성된 단어의 시간을 추가합니다 .
			mwThread.resumeThread();

			inputPanel.changeStateInput(true);  // 입력 제한 상태를 풉니다.
			requestInputFocus();                // 키보드 입력을 요청합니다.

			scorePanel.startGame();             // 점수 및 게이지 스레드를 시작합니다.

			// fThread의 모든 WAIT 상태의 스레드를 깨웁니다.
			for (int i = 0; i < fThread.length; i++) {
				if (fThread[i] != null) {
					synchronized (fThread[i]) {
						fThread[i].resumeThread();
						//fThread[i].notifyAll();   // 떨어지는 단어를 움직이도록 만든다.
					}
				}
			}

			userHpThread.resumeThread();
			bossHpThread.resumeThread();
		}
	}

	// 게임을 일시 중지 시키는 메소드 stop()
	public void stop() {
		//gameFrame.stopBgm();
		if (mwThread != null) {
			// 플래그 설정을 통해 스레드를 wait()상태로 만든다.
			mwThread.pauseThread();

			// 플래그 설정을 통해 화면상의 스레드를 멈추도록 만든다.
			for (int i = 0; i < fThread.length; i++) {
				if (fThread[i] != null) {
					fThread[i].pauseThread();
				}
			}

			// 일정 주기로 단어 생성을 유지하기 위해 정지 시점의 시간을 기록합니다.

			timePaused = System.currentTimeMillis();

			inputPanel.changeStateInput(false);     // 정지 상태에서는 단어 입력이 제한됩니다.

			scorePanel.pause();     // 점수패널의 스레드들을 멈추도록 만듭니다.

			userHpThread.pauseThread();     // 유저 hp를 관리하는 스레드를 멈춥니다.
			bossHpThread.pauseThread();     // 보스 hp를 관리하는 스레드를 멈춥니다.
		}
	}

	// 게임 종료 버튼 exit()
	public void exit() {
		gameFrame.stopBgm();
		if (mwThread != null) {
			mwThread.stopThread();  // 플래그를 바꾸면서 스레드를 interrput로 TERMINATED 상태로 만든다.
			mwThread = null;
		}

		// 화면상에 존재하는 단어들을 모두 제거한다
		for (int i = 0; i < fThread.length; i++) {
			if (fThread[i] != null) {           // 종료되지 않은 스레드에 대하여
				fThread[i].interrupt();         // 스레드를 TERMINATED 상태로 만든다.
				fThread[i] = null;              // 더이상 생명력을 가지지 못하는 스레드의 참조를 제거한다

				fallingLabel[i].setVisible(false);  // 화면상의 단어를 보이지 않게 처리한다.
				fallingLabel[i].setText("");        // 채워진 단어를 비운다.
			}
		}

		lastSpawnTime = 0;

		timePaused = 0;

		if (darkModeController != null) {
			darkModeController.exit();
		}

		leaderboardPanel.add(scorePanel.getName(), scorePanel.getScore());

		// 게이지를 관리하는 스레드를 종료하고 종료상태가 된 참조를 제거한다.
		scorePanel.exit();

		// hp를 관리하는 스레드를 종료한다.
		userHpThread.stopThread();
		userHpThread = null;

		bossHpThread.stopThread();
		bossHpThread = null;

		userHp.reset();
		bossHp.reset();

		battleItemPanel.reset();
	}

	// 단어를 만드는 스레드 클래스 MakeWordThread
	class MakeWordThread extends Thread {

		// 단어를 만드는 스레드를 제어하는 필드
		private boolean running = false;    // 게임이 시작중인지
		private boolean pause = false;      // 게임이 일시정지 상태인지
		private int index;                  //

		@Override
		public void run() {

			while (running) {
				// 일시 정지 처리 스레드를 wait()상태로 만들고 사용자가 start

				// 버튼을 누르면 다시 작동하도록 만듭니다.
				synchronized (this) {
					if (pause) {
						try {
							wait();     // 정지 시에 영구히 대기한다.
						} catch (InterruptedException e) {
							return;
						}
					}
				}

				// 암흑상태가 진입시 바로 단어 생성을 금지시킵니다. 독단적인 행동을 금지시킨다.
				if (darkModeController != null && darkModeController.isActive()) {
					pauseThread();
					continue;
				}

				//spawnWord();
				long now = System.currentTimeMillis();  // 단어 생성 시작시간

				// Gamepanel > ScorePanel > DarkGage 로부터 얻어온 암흑 상태인지 확인하는 게터이다.
				if (scorePanel.getDarkGageState() && darkModeController != null && !darkModeController.isActive()) {
					// 암흑 상태가 되면

					// EDT에 다크모드를 실행을 요청한다. 절대 mwThread가 실행하면 안된다.
					javax.swing.SwingUtilities.invokeLater(() -> {
						darkModeController.start();
					});

					groundPanel.repaint();
				}

				// 현재 시간과 마지막으로 단어가 생성된 시간을 DEALY 시간과 비교합니다.

				// 이를 통해 유저가 stop 버튼을 누르고 start 버튼을 눌렀을때 자연스러운 단어 생성을 돕습니다.
				if (now - lastSpawnTime < DELAY) {
					long remainTime = DELAY - (now - lastSpawnTime);     // 단어 생성까지 남은 시간
					try {
						sleep(remainTime);      // 단어 생성까지 남은시간을 대기하고 출력합니다.
					} catch (InterruptedException e) {
						return;
					}
					continue;
				}

				// 단어 하나 생성
				for (int i = 0; i < fallingLabel.length; i++) {
					// 레이블 배열을 순환하며 인덱스를 계산합니다.
					index = (index + 1) % fallingLabel.length;

					// 투명한 상태가 아닌 인덱스를 탐색합니다. 이때 하나도 없다면 단어가 더이상 만들어지지 않습니다.
					if (!fallingLabel[index].isVisible()) {
						setFlabel(fallingLabel[index]);     // 레이블 설정(크기, 단어)를 담당합니다.
						fThread[index] = new FallingThread(fallingLabel[index], index);  // 새로운 스레드를 생성합니다. 이를 통해 생성 종료가 가능해집니다.
						fThread[index].startThread();       // JVM이 스케줄링 할수 있는 상태로 만듭니다.

						lastSpawnTime = System.currentTimeMillis();     // 마지막으로 단어가 생성된 시간을 갱신합니다.
						break;      // 단어를 생성했다면 단어의 생성 정지합니다.
					}
				}
			}
		} // end run()

		synchronized public void startThread() {

			running = true;
			start();
		}

		synchronized public void pauseThread() {
			pause = true;
		}

		synchronized public void resumeThread() {
			pause = false;
			notify();
		}

		synchronized public void stopThread() {
			running = false;
			pause = false;
			interrupt();
		}
	}

	// 라벨이 떨어지는 동작
	public boolean move(JLabel la, int speed) {
		int x = la.getX();
		int y = la.getY();

		// 단어가 떨어질때 가장 위로 돌아가는 임계를 저장한다.
		int fallingLimitY = groundPanel.getHeight() - fallingLabel[0].getHeight();

		// 맞추기 못하고 단어가 떨어진 경우
		// 스레드를 삭제하는 대신 점수를 떨어트리고 단어를 새로 가져온 뒤 다시 떨어트린다.
		if (y + la.getHeight() >= fallingLimitY) {
			scorePanel.increase(-10);       // 점수를 감점시키고
			userHp.decrease(missDamage);    // 체력의 5퍼센트만큼의 피해를 입습니다.
			System.out.println("데미지를 입었습니다.");
			battleItemPanel.changeImageToFail();    // 실패한 경우 아이콘으로 바꿉니다.
			return true;
		}

		else {
			y += speed;     // y를 기존에 떨어트리던 수치만큼 떨어트립니다.
			la.setLocation(x, y);

			return false;
		}
	}

	public void setFlabel(JLabel fLabel) {
		fLabel.setVisible(true);

		String text = tStore.get();     // 랜덤한 새로운 단어를 가져옵니다.
		fLabel.setText(text);           // 단어를 채웁니다.

		fLabel.setFont(new Font("GOTHIC", Font.BOLD, 18));
		fLabel.setSize(fLabel.getPreferredSize());
		int limitX = groundPanel.getWidth() - fLabel.getWidth();     // 패널내의 무작위 x 좌표
		int randX = (int) (Math.random() * limitX);                  // 랜덤한 x 좌표
		fLabel.setLocation(randX, bossHp.getHeight());               // 보스 피통 아래에서 만들어져야함
		fLabel.setForeground(Color.WHITE);                           // 단어의 색을 흰색으로 변경합니다.

		groundPanel.revalidate();
		groundPanel.repaint();
	}

	//private
	class FallingThread extends Thread {    //스레드로서의 기능으로 작용하는 클래스
		private JLabel la = null;   // 현재 스레드의 라벨을 기억합니다.
		private int speed;          // 스레드를 생성할때 생성자에 주어서 난이도 조절을 용이하게 하겠습니다.
		private int index;

		private boolean running = false;

		private boolean pause = false;

		public FallingThread(JLabel la, int index) {
			this.la = la;       // 자기 자신을 기억해야하므로 la를 move에 넘겨 사용
			this.index = index;
			this.speed = wordFallSpeed;
		}

		@Override
		public void run() {     //JVM 이불러서 public
			// 암흑 상태에서 스레드가 생성되었을 경우 정지상태로 만든다.
			if (darkModeController != null && darkModeController.isActive()) {
				pause = true;
			}

			while (running) {   // exit이 눌리게 되면 자동으로 루프를 빠져나가 스레드가 죽도록 하는 구조이다.
				synchronized (this) {
					if (pause) {
						try {
							wait();     // 정지 상태일경우 스레드를 WAIT 상태로 만듭닏
						} catch (InterruptedException e) {
							return;
						}
					}
				}

				boolean limitReached = move(la, speed);     // 해당 패널의 y축의 끝에 닿았는지 결과

				// y축의 끝에 닿았을경우
				if (limitReached) {
					la.setVisible(false);       // 단어를 안보이게하고
					la.setText("");             // 단어의 문장을 비워 오작동을 방지하고
					fThread[index].interrupt(); // 단어를 떨어트리는 스레드를 종료시키고

					fThread[index] = null;      // TERMINATED 상태가 된 참조를 제거합니다.
					break;      // 루프를 멈춰 스레드를 종료하게끔 합니다. 근데 이거 필요한가? interrupt는 왜쓰는데?
				}

				try {
					sleep(100);     // 0.5초간 대기한다. TIMED_WAITING(500);
				} catch (InterruptedException e) {
					return;
				}
			}
		} // end run()

		synchronized public void startThread() {
			running = true;
			start();
		}

		synchronized public void pauseThread() {
			pause = true;
		}

		synchronized public void resumeThread() {
			pause = false;
			notify();
		}

		synchronized public void stopThread() {
			running = false;
			pause = false;
			interrupt();
		}
	}

	class GroundPanel extends JPanel {
		private Image backgroundImage;
		private DarkWord darkWord;

		public GroundPanel() {
			this.setBackground(Color.WHITE);
			this.setLayout(null);

			setOpaque(true);

			for (int i = 0; i < fallingLabel.length; i++) {
				fallingLabel[i] = new JLabel("");
				fallingLabel[i].setSize(100, 20);
				fallingLabel[i].setVisible(false);

				add(fallingLabel[i]);
			}

			add(bossHp);

			setFocusable(true);
		}

		// 난이도에 따른 배경을 추가합니다.
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (backgroundImage != null) {
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);   // 화면 전체를 채웁니다.
			}
		}

		// 난이도를 설정하는 메소드
		public void setMode() {
			if (difficulty == 2) {
				backgroundImage = hardIcon.getImage();
			} else {
				backgroundImage = normalIcon.getImage();
			}
			repaint();
		}

		// 단어 하나당 하나의 라벨로 쪼갠 라벨들을 화면에 붙이고 새로고침합니다.

		public void addDarkWordLabels(JLabel[] labels) {
			for (int i = 0; i < labels.length; i++) {
				labels[i].setVisible(true);
				add(labels[i]);     // 라벨을 컴포넌트에 부착합니다.
				int x = getWidth() / 2;
				int y = getHeight() / 2;
				labels[i].setLocation(x + (60 * i), y);
			}
			repaint();  // 화면을 새로고침합니다.
		}

		// 단어를 다 맞추었거나 제한시간내 맞추지 못한경우 삭제합니다.
		public void removeDarkWordLabels(JLabel[] labels) {
			for (int i = 0; i < labels.length; i++) {
				labels[i].setVisible(false);
				remove(labels[i]);      // 라벨을 컴포넌트에서 제거합니다.
			}
			repaint();  // 화면을 새로고침합니다.
		}
	}

	class InputPanel extends JPanel {
		private JTextField inputField = new JTextField(10);

		public InputPanel() {
			this.setBackground(Color.GRAY);
			add(inputField);
			inputField.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// 왜 수동태로 쓰는가? call 되는 거니까 라는 생각의 필요
					// Event Dispatch Thread 에서 호출하기 때문에
					// 전혀 상관없는 클래스에서 접근하기 때문에 public으로 선언해야한다.
					// Event Dispatch Thread 에게 아무것도 줄 필요가 없다. void
					JTextField tf = (JTextField) e.getSource();  // 여러개 있을수 있다는 가정을 까먹으면 안됨
					String userText = tf.getText().trim();       // 띄어쓰기로 인한 오작동 방지

					if (userText.isEmpty()) return;     // 빈배열 입력시 리턴

					int maxY = -1;          // 가장 큰 y값 필드
					int targetIndex = -1;   // 삭제할 인덱스 필드

					for (int i = 0; i < fallingLabel.length; i++) {

						if (userText.equals(fallingLabel[i].getText())) {
							int y = fallingLabel[i].getY();     // 입력한 단와와 동일한 라벨의 y값
							if (y > maxY) {
								maxY = y;           // 입력창과 가장 가까운 y값 저장
								targetIndex = i;
							}
						}
					}

					// 삭제할 단어가 화면에 존재할 경우
					if (targetIndex != -1) {
						scorePanel.increase();          // 점수를 증가시키고
						scorePanel.fill(basicScore);    // 점수를 증가시키고
						bossHp.takeDamage(attack);      // 보스에게 데미지를 입히고

						battleItemPanel.changeImageToSuccess();     // 성공한 경우의 설정된 이미지로 바꿉니다.
						fThread[targetIndex].interrupt();           // 떨어지는 스레드를 종료시키고

						// 오작동을 방지하기 위한 초기화
						fallingLabel[targetIndex].setVisible(false);    // 떨어지는 컴포넌트의 글자를 보이지 않게 만들고
						fallingLabel[targetIndex].setLocation(0, 0);    // 컴포넌트의 위치를 초기화합니다.
						fallingLabel[targetIndex].setText("");          // 컴포넌트의 내부 문자열을 초기화합니다.

						fThread[targetIndex] = null;    // Terminated 상태가 된 기존의 스레드 참조를 제거합니다.
						// 기존의 참조가 제거된 객체는 가비지가 되고 가비지 컬렉션을 촉진시킨다.
					} else {
						scorePanel.increase(-10);
						userHp.decrease(missDamage);    // 단어를 잘못입력했을경우
						battleItemPanel.changeImageToFail();
					}
					tf.setText("");     // 다음 단어를 입력받을 준비를 합니다.
				}

			});
		}

		// stop 이나 exit 상태일때 입력이 불가능하도록 막는다.
		public void changeStateInput(boolean state) {
			if (!state) {                       // stop 상태일 경우
				inputField.setEnabled(false);   // 텍스트필드 입력 제한
			} else {                            // start 상태일 경우
				inputField.setEnabled(true);    // 텍스트필드 입력 가능
			}
		}

		public void focusInput() {
			inputField.requestFocusInWindow();
		}
	}
}
