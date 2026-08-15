package original;

// 그래픽 관련 import
import java.awt.Color;      // 점수 패널 내부 게이지 및 텍스트 색상 설정
import java.awt.Font;       // 점수, 플레이어 이름, 아이콘 설명 텍스트의 폰트 설정
import java.awt.Graphics;   // 패널 배경 및 렌더링에 사용

// 컴포넌트
import javax.swing.ImageIcon;   // 에스더 스킬 및 암흑 게이지 이미지 로딩
import javax.swing.JLabel;      // 점수를 표현하기 위해 사용
import javax.swing.JPanel;      // 최상위 컨테이너

//- 점수, 플레이어 이름, 에스더 스킬(미구현), 암흑 게이지를 관리하는 패널
public class ScorePanel extends JPanel {

	// 점수를 표시하는 필드
	private int score = 0;
	private JLabel scoreString = new JLabel("score : ");
	private JLabel scoreLabel = new JLabel(Integer.toString(score));    // 실제 점수 값 표시 라벨

	// 사용자 이름을 표시하는 필드
	private JLabel userString = new JLabel("player : ");
	private JLabel userName = new JLabel();

	// 에스더 스킬 (미구현 입니다. 미처 빼지는 못해서 넣어놓았습니다.)
	// 니나브는 직선상의 단어들을 삭제하고 삭제한 단어만큼 보스에게 추가 피해를 줍니다.
	private ImageIcon ninevehIcon = new ImageIcon("images/nineveh.jpg");         // 이미지 로딩 및 저장
	private ImageIcon ninevehFull = new ImageIcon("images/nineveh_Full.jpg");    // 게이지가 가득 찼을 때 아이콘
	private JLabel nineveh = new JLabel(ninevehIcon);                            // 출력 라벨

	// 웨이는 사용자가 입력하는 단어의 데미지를 두배로 증가시킵니다.
	private ImageIcon weiIcon = new ImageIcon("images/wei.jpg");         // 이미지 로딩 및 저장
	private ImageIcon weiFull = new ImageIcon("images/wei_Full.jpg");    // 게이지가 가득 찼을 때 아이콘
	private JLabel wei = new JLabel(weiIcon);                            // 출력 라벨

	// 이난나는 보스의 특수 패턴의 게이지를 감소시킵니다. (암흑) 가능하면? 데미지도 일정 시간 안들어오게 가능
	private ImageIcon inannaIcon = new ImageIcon("images/inanna.jpg");           // 이미지 로딩 및 저장
	private ImageIcon inannaFull = new ImageIcon("images/inanna_Full.jpg");      // 게이지가 가득 찼을 때 아이콘
	private JLabel inanna = new JLabel(inannaIcon);                              // 출력 라벨

	// 에스더 게이지 - 꽉차면 에스더스킬 3종중 1종을 쓸수있다.
	private MyLabel estherGage = new MyLabel(100);
	private fillThread th;      // 에스더 게이지를 자동으로 채우는 스레드

	// 정산 게이지 - 꽉차면 암흑 상태에 걸린다.
	private DarkGage darkGageComponent = new DarkGage();    // 진짜 암흑 게이지를 채우는 사용자 정의 컴포넌트
	private Thread darkThread;                              // 암흑 게이지를 자동으로 채우는 스레드 생성

	// 생성자
	public ScorePanel() {

		setLayout(null);    // 임의 배치를 위해 배치 관리자를 제거합니다.

		// score : 라벨 부착
		scoreString.setSize(150, 50);
		scoreString.setLocation(10, 0);
		scoreString.setFont(new Font("GOTHIC", Font.BOLD, 30));
		add(scoreString);

		// 점수 라벨 부착
		scoreLabel.setSize(100, 40);
		scoreLabel.setLocation(120, 7);
		scoreLabel.setFont(new Font("GOTHIC", Font.BOLD, 30));
		add(scoreLabel);

		// player : 라벨 부착
		userString.setSize(200, 50);
		userString.setLocation(10, 60);
		userString.setFont(new Font("GOTHIC", Font.BOLD, 30));
		add(userString);

		// 현재 플레이중인 유저 이름 패널에 추가
		userName.setSize(100, 50);
		userName.setLocation(140, 60);
		userName.setFont(new Font("GOTHIC", Font.BOLD, 30));
		add(userName);

		// 에스더 니나브 패널에 추가
		nineveh.setToolTipText("일직선상의 단어들을 없애버리고 없앤 단어의 개수만큼 보스에게 추가피해를 입힙니다.");
		nineveh.setSize(150, 150);
		nineveh.setLocation(20, 100);
		add(nineveh);

		// 에스더 웨이 패널에 추가
		wei.setToolTipText("사용자가 보스에게 입히는 피해량을 두배만큼 증가시킵니다.");
		wei.setSize(150, 150);
		wei.setLocation(106, 100);
		add(wei);

		// 에스더 이난나 패널에 추가
		inanna.setToolTipText("보스의 특수 패턴 게이지를 대폭 감소시킵니다.");
		inanna.setSize(150, 150);
		inanna.setLocation(186, 100);
		add(inanna);

		// 에스더 게이지를 패널에추가합니다.
		estherGage.setBackground(Color.BLACK);
		estherGage.setOpaque(true);     // JLabel 타입이기 때문에 투명화 설정 작업이 필요하다.
		estherGage.setLocation(50, 216);
		estherGage.setSize(255, 25);
		add(estherGage);

		// 암흑 게이지 패널에 추가
		darkGageComponent.setToolTipText("특수패턴 게이지가 가득차면 유저가 암흑 상태에 걸립니다.");
		darkGageComponent.setLocation(50, 300);
		darkGageComponent.setSize(180, 180);
		add(darkGageComponent);
	}

	// 배경을 그리는 메소드
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	// 유저 이름을 설정하는 메소드
	public void setUserName(String name) {
		this.userName.setText(name);
	}

	// 랭킹저장을 위한 게터 생성
	public String getName() {
		return userName.getText();
	}

	// 랭킹 저장을 위해 점수를 반환하는 getter
	public int getScore() {
		return score;
	}

	// exit > start 시 호출되는 reset처리 메소드
	public void resetScore() {

		score = 0;
		scoreLabel.setText(Integer.toString(score));
	}

	// 점수를 증가시키는 메소드
	public void increase(int amount) {
		score += amount;

		if (score < 0) {
			score = 0;      // 점수 하한 설정
		}

		scoreLabel.setText(Integer.toString(score));    // 점수 갱신
	}

	// 외부에서 점수를 증가시키기 위해 호출하는 메소드
	public void increase() {
		increase(50);
	}

	// 단어를 맞출경우 에스더 게이지를 추가로 증가시키기 위한 메소드 fill
	public void fill(int amount) {
		estherGage.fill(amount);
	}

	// 에스더 게이지가 가득찰 경우 아이콘을 바꿉니다.
	public void changeIcon(boolean full) {
		if (full) {
			nineveh.setIcon(ninevehFull);
			wei.setIcon(weiFull);
			inanna.setIcon(inannaFull);
		} else {
			nineveh.setIcon(ninevehIcon);
			wei.setIcon(weiIcon);
			inanna.setIcon(inannaIcon);
		}
	}

	// GamePanel > ScorePanel > DarkGage 로 접근하여 암흑상태를 알아오기 위한 ScorePanel 게터이다.
	public boolean getDarkGageState() {
		return darkGageComponent.getDark();
	}

	// 암흑 상태를 해제하는 메소드이다.
	public void clearDark() {
		darkGageComponent.setDark(false);   // 암흑상태를 해제합니다. 이미지 복귀, 채우기 재개
	}

	// 게임 시작 시 호출
	synchronized public void startGame() {

		// 에스더 게이지 스레드를 생성 및 start()
		if (th == null) {
			th = new fillThread(estherGage, 1, this);
			th.startThread();
		} else {
			th.resumeThread();  // 재시작시 stop 상태를 풀고 notify()
		}

		// 암흑 게이지 스레드를 생성 및 start()
		if (darkThread == null) {
			darkThread = new Thread(darkGageComponent);
			darkThread.start();
		} else {
			darkGageComponent.resumeThread();   // 재시작시 stop 상태를 풀고 notify()
		}
	}

	// 게임 중지시 게이지들이 차지 못하도록 제어하는 메소드 pause
	public void pause() {
		th.pauseThread();
		darkGageComponent.pauseThread();
	}

	// 게임 종료시 게이지를 관리하는 스레드를 종료시키고 다시 시작할때 새로운 레퍼런스를 얻도록 해당 레퍼런스를 삭제합니다.
	synchronized public void exit() {
		if (th != null) {
			th.interrupt();
			th = null;
			estherGage.init();
		}

		if (darkThread != null) {
			darkThread.interrupt();
			darkThread = null;
			darkGageComponent.init();
		}
	}
}

//에스더 게이지를 사용할 경우 기본 아이콘으로 변경합니다.
class fillThread extends Thread {

	private MyLabel bar;            // 채워질 게이지 객체
	private int chargeSpeed;        // 충전 속도
	private ScorePanel sPanel;      // 아이콘 변경을 위한 ScorePanel 참조

	// 상태 관리 플래그
	private boolean pause = false;
	private boolean running = false;

	// 생성자
	public fillThread(MyLabel bar, int chargeSpeed, ScorePanel sPanel) {
		this.bar = bar;                     // 객체 저장
		this.chargeSpeed = chargeSpeed;     // 채워지는 속도 조절
		this.sPanel = sPanel;               // 점수 저장을 위한 객체 저장
	}

	@Override
	public void run() {
		while (running) {
			synchronized (this) {
				while (pause) {
					try {
						wait();
					} catch (InterruptedException e) {
						return;
					}
				}
			}

			// 정지 상태가 아닐경우 계속해서 채웁니다.
			try {
				sleep(2000);

				boolean full = bar.fill(chargeSpeed);
				if (full) {
					pause = true;
					sPanel.changeIcon(full);
				}
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	synchronized public void startThread() {
		running = true;     // 스스로 정지합니다.
		start();
	}

	// 일시정지시 호출 or 에스더 게이지가 가득찰 경우 호출
	synchronized public void pauseThread() {
		pause = true;       // 스스로 정지합니다.
	}

	// 재시작시 호출
	synchronized public void resumeThread() {
		pause = false;      // 정지를 풀고
		notify();           // 다시 게이지를 채우기를 시작합니다.
	}

	// exit 버튼 클릭시 호출
	synchronized public void stopThread() {
		running = false;
		interrupt();
	}
}

class MyLabel extends JLabel {
	private int barSize = 0;    // 현재 그려져야할 바의 크기
	private int maxBarsize;     // 바의 최대 크기

	public MyLabel(int maxBarsize) {
		this.maxBarsize = maxBarsize;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.YELLOW);   // 노란색 생각보다 별로임 다른 적절한 색 찾아보기

		int width = (int) (((double) (this.getWidth())) / maxBarsize * barSize);  // 현재 HP 비율을 그립니다.
		if (width == 0) return;     // 크기가 0이기 때문에 바를 그릴 필요 없음
		g.fillRect(0, 0, width, this.getHeight());   // 갱신된 체력을 채운다.
	}

	// 게이지 증가
	public boolean fill(int amount) {
		barSize += amount;
		if (barSize > maxBarsize) {
			barSize = maxBarsize;
			repaint();      // 완료후 멈추기전에 다시 화면에 그린다.
			return true;
		}
		repaint();          // 갱신
		return false;
	}

	// 게이지 초기화
	public void init() {
		barSize = 0;

		repaint();
	}
}
