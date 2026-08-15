package original;

// 그래픽 관련
import java.awt.Color;      // HP 바의 배경 및 생명력 색상 설정에 사용
import java.awt.Graphics;   // 생명력 바를 직접 그리기 위한 그래픽 객체

// 컴포넌트
import javax.swing.JLabel;  // HP 바를 표현하는 커스텀 컴포넌트

// 플레이어나 보스의 HP를 표현하는 클래스
public class HealthPoint extends JLabel {

	private int hp;                 // 현재 체력
	private int maxHp;              // 최대 체력
	private boolean isBoss;         // 보스 여부
	private GamePanel gamePanel;    // 참조 - 사망처리, 보스 피해처리를 담당합니다.

	// 생성자

	public HealthPoint(int maxHp, boolean isBoss, GamePanel gamePanel) {

		this.isBoss = isBoss;           // 최대 체력 설정
		this.maxHp = maxHp;             // 최대 HP 설정
		this.hp = maxHp;                // 처음 HP는 최대로 설정한다.
		this.gamePanel = gamePanel;     // 참조를 저장한다.

		setOpaque(true);    // 배경색을 직접 그리기 위해 Opaque 설정
	}

	// HP 바
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);        // JLabel 기본 배경 처리
		setBackground(Color.BLACK);     // HP 바 배경은 검정색
		g.setColor(Color.RED);          // 생명력은 빨간색으로 표시합니다.

		int width = (int) (((double) (this.getWidth())) / maxHp * hp);   // 현재 HP 비율에 따른 바의 너비 계산
		if (width <= 0) return;     // 크기가 0이기 때문에 바를 그릴 필요 없음
		g.fillRect(0, 0, width, this.getHeight());   // 계산된 너비만큼 사각형을 채워 HP 바를 그림
	}

	// 회복 물약, 아이템을 먹었을때 그리고 암흑 패턴 파훼 실패시 hp 상태를 변경하기 위해호출합니다.
	synchronized public void heal(int amount) {
		hp += amount;                   // 체력 증가
		if (hp > maxHp) hp = maxHp;     // 최대 체력을 초과하지 않도록 제한

		repaint();      // 갱신
	}

	// 체력 초기화 - exit > start 시 호출
	synchronized public void reset() {

		hp = maxHp;     // 현재HP를 최대치로 복구

		repaint();      // 갱신
	}

	// 피해 처리 (보스에게 사용하는 메소드)
	synchronized public void takeDamage(int damage) {
		hp -= damage;               // 체력 감소
		if (hp < 0) hp = 0;         // 최소 체력 이하로 내려가지 안도록 제한
		repaint();                  // 갱신

		// 보스가 데미지를 입었을때 격돌을 실행할지 말지 gamePanel에게 전달
		if (isBoss) {
			gamePanel.onBossDamaged(hp, maxHp);     // HP변경 사항을 전달

			// HP가 0이면 보스 사망 처리
			if (hp == 0) {
				gamePanel.onDeath(false);   // 보스 사망 전달 > Dialog를 띄우기 위함
			}
		}
	}

	// 사용자의 HP가 감소할때 실행하는 메소드
	synchronized public void decrease(int damage) {
		hp -= damage;           // 체력 감소
		if (hp < 0) hp = 0;     // 최소 hp 제한

		repaint();              // 갱신

		// 플레이어 사망 처리
		if (!isBoss && hp == 0) {
			gamePanel.onDeath(true);    // 유저 사망
		}
	}

	// HP 최대치인지 확인
	synchronized public boolean isFull() {

		return hp == maxHp;     // 해당 여부 반환
	}

	// 최대 HP 변경 - 모드에 따른 보스HP를 설정합니다.
	public void setMaxHp(int bossMaxHp) {
		this.maxHp = bossMaxHp;
		this.hp = maxHp;

		repaint();      // 갱신
	}
}

// 자동 회복 또는 주기적 처리용 스레드
class HealthThread extends Thread {

	// 상태 설정용 플래그
	private boolean running = false;
	private boolean pause = false;

	private HealthPoint healthPoint;    // 스레드가 기억하는 메소드 지정 bossHp 나 userHp

	// 생성자
	public HealthThread(HealthPoint healthPoint) {
		this.healthPoint = healthPoint;     // hp 객체 저장
	}

	@Override
	public void run() {
		// 실행 중일때만 루프
		while (running) {
			synchronized (this) {
				// 정지 상태이거나 HP가 가득차있을 경우 대기합니다.
				while (pause || healthPoint.isFull()) {
					try {
						wait();
					} catch (InterruptedException e) {
						// 인터럽스 발생시 스레드 종료
						return;

					}
				}
			}
			try {
				sleep(1000);    // 1초 주기로 동작합니다.
			} catch (InterruptedException e) {
				// 인터럽스 발생시 스레드 종료
				return;
			}
		}
	}

	// 스레드 일시 정지
	synchronized public void pauseThread() {
		pause = true;
	}

	// 스레드 재개
	synchronized public void resumeThread() {
		pause = false;
		notifyAll();
	}

	// 스레드 완전 종료
	public synchronized void stopThread() {
		running = false;
		pause = false;
		interrupt();
	}

	// 스레드 시작
	synchronized public void startThread() {
		System.out.println("스레드 시작");
		running = true;
		start();
	}
}
