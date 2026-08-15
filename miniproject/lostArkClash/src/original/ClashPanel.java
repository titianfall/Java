package original;

// 그래픽 관련 import
import java.awt.BasicStroke;      // 원의 선 두께를 조절하기 위해 사용
import java.awt.Color;            // 판정에 따른 색상 표현
import java.awt.Font;             // 문자 및 판정 텍스트 폰트
import java.awt.Graphics;
import java.awt.Graphics2D;       // 두께가 있는 원을 그리기 위해 업캐스팅해서 사용
import java.awt.Image;            // 배경 이미지
import java.awt.RenderingHints;   // 원의 계단현상을 줄이기 위한 안티앨리어싱

// 키 입력 처리
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;     // 배경 이미지 로딩
import javax.swing.JPanel;        // 최상위 컨테이너
import javax.swing.Timer;         // 원을 줄이고 판정 결과를 잠시 보여주기 위한 Swing Timer

/*
 * [복원 노트]
 * 보고서(객체지향언어2 미니프로젝트 보고서)의 "4. 프로그램 소스 코드" 항목에는
 * 12번 ClashPanel.java 의 제목만 있고 본문 코드가 누락되어 있었다.
 * 그래서 이 클래스만은 원본 코드가 아니라, 보고서 1장(작품 개요)과 3장(실행 과정)에 적힌
 * 격돌 미니게임 사양을 근거로 다시 작성한 것이다.
 *
 *  - 지정된 단어(문자) 8개 중 하나가 무작위로 설정되어 화면에 출력된다.
 *  - 문자 주변에는 시간이 지날수록 줄어드는 원이 있고,
 *    같은 문자를 입력한 시점의 원 크기로 perfect / good / bad 를 판정한다.
 *  - 격돌은 3세트이며 세트당 1~3회 랜덤하게 진행한다(최소 3회, 최대 9회).
 *  - bad 판정이면 원이 빨간색으로 표시되고 사용자는 전체 HP의 30% 피해를 입는다.
 *  - bad 가 2번 이상이면 격돌을 즉시 종료하고 다시 게임 화면으로 돌아간다.
 *
 * GameFrame 이 호출하는 규격(생성자, startClashSequence(), setShrinkSpeed())은 원본 그대로 맞췄다.
 */
public class ClashPanel extends JPanel {

	// 격돌에 사용되는 지정 문자 8개
	private static final char[] CLASH_KEYS = { 'Q', 'W', 'E', 'R', 'A', 'S', 'D', 'F' };

	// 원 크기 관련 상수
	private static final int START_RADIUS = 260;    // 격돌 시작시 원의 반지름
	private static final int TARGET_RADIUS = 70;    // 판정 기준이 되는 고정 원의 반지름
	private static final int PERFECT_RANGE = 14;    // perfect 판정 허용 오차
	private static final int GOOD_RANGE = 42;       // good 판정 허용 오차
	private static final int MISS_RADIUS = 20;      // 이 크기보다 작아지면 입력을 놓친 것으로 본다.

	private static final int TOTAL_SET = 3;         // 격돌 세트 수
	private static final int MAX_BAD = 2;           // 격돌이 중단되는 bad 횟수
	private static final int USER_CLASH_DAMAGE = 60;    // bad 판정시 피해량 (유저 전체 HP 200의 30%)

	// 협력 객체
	private ScorePanel scorePanel;      // 판정 성공시 점수를 올리기 위한 참조
	private GamePanel gamePanel;        // 격돌 종료 및 사용자 피해 처리를 위한 참조

	// 배경 이미지
	private ImageIcon clashIcon = new ImageIcon("images/clashBackground.jpg");
	private Image clashImg = clashIcon.getImage();

	// 격돌 진행 상태
	private int shrinkSpeed;            // 원이 줄어드는 속도 (난이도)
	private int totalCount;             // 이번 격돌에서 진행할 총 입력 횟수
	private int currentCount;           // 현재 몇 번째 입력인지
	private int badCount;               // bad 판정 횟수

	private char targetKey;             // 현재 맞춰야 하는 문자
	private int radius;                 // 현재 줄어드는 원의 반지름

	private boolean running = false;    // 격돌이 진행중인지
	private boolean judged = false;     // 이번 입력의 판정이 끝났는지
	private String judgeText = "";      // 화면에 보여줄 판정 결과
	private Color circleColor = new Color(0, 160, 255);  // 줄어드는 원의 색 (bad면 빨간색)

	// 타이머
	private Timer shrinkTimer;      // 원을 줄이는 타이머
	private Timer nextTimer;        // 판정 결과를 잠시 보여준 뒤 다음 입력으로 넘어가는 타이머

	// 생성자 - GameFrame 이 넘겨주는 참조와 난이도를 저장한다.
	public ClashPanel(ScorePanel scorePanel, GamePanel gamePanel, int shrinkSpeed) {
		this.scorePanel = scorePanel;
		this.gamePanel = gamePanel;
		this.shrinkSpeed = shrinkSpeed;

		setLayout(null);            // 직접 그리는 화면이라 배치관리자는 사용하지 않는다.
		setBackground(Color.BLACK);
		setFocusable(true);         // 키 입력을 받기 위해 포커스를 받을 수 있어야 한다.

		// 격돌 전용 키 리스너 등록
		addKeyListener(new ClashKeyAdapter());

		// 16ms 마다 원의 반지름을 줄인다. (EDT 에서 동작하므로 화면 갱신이 안전하다)
		shrinkTimer = new Timer(16, e -> shrink());

		// 판정 결과를 0.7초 보여준 뒤 다음 입력을 준비한다.
		nextTimer = new Timer(700, e -> {
			nextTimer.stop();
			nextRound();
		});
	}

	// 격돌 난이도(원이 줄어드는 속도)를 외부에서 설정하기 위한 메소드
	public void setShrinkSpeed(int shrinkSpeed) {
		this.shrinkSpeed = shrinkSpeed;
	}

	// GameFrame.startClash() 에서 호출한다. 격돌 한 판을 시작한다.
	public void startClashSequence() {
		// 3세트 각각 1~3회 → 최소 3회 최대 9회
		totalCount = 0;
		for (int i = 0; i < TOTAL_SET; i++) {
			totalCount += (int) (Math.random() * 3) + 1;
		}

		currentCount = 0;
		badCount = 0;
		running = true;

		// 카드 전환 직후라 바로 포커스를 요청하면 실패할 수 있어 EDT 큐 뒤로 미룬다.
		javax.swing.SwingUtilities.invokeLater(() -> requestFocusInWindow());

		startRound();
	}

	// 입력 한 번을 시작한다.
	private void startRound() {
		currentCount++;

		targetKey = CLASH_KEYS[(int) (Math.random() * CLASH_KEYS.length)];   // 지정 문자 8개 중 랜덤
		radius = START_RADIUS;
		judged = false;
		judgeText = "";
		circleColor = new Color(0, 160, 255);

		repaint();
		shrinkTimer.start();
	}

	// 타이머가 호출한다. 원을 줄이고 다 줄어들면 실패로 처리한다.
	private void shrink() {
		if (!running || judged) return;

		radius -= shrinkSpeed;

		// 원이 판정 범위를 지나쳐 사라지면 입력을 놓친 것이다.
		if (radius <= MISS_RADIUS) {
			judge(false, "BAD");
			return;
		}

		repaint();
	}

	// 판정 처리 - success 여부와 표시할 문구를 받는다.
	private void judge(boolean success, String text) {
		judged = true;
		judgeText = text;
		shrinkTimer.stop();

		if (success) {
			// perfect 는 점수를 더 많이 준다.
			if (text.equals("PERFECT")) {
				scorePanel.increase(300);
			} else {
				scorePanel.increase(150);
			}
			circleColor = new Color(0, 160, 255);
		} else {
			badCount++;
			circleColor = Color.RED;    // bad 판정은 원을 빨간색으로 보여준다.

			// 전체 HP 기준 30%의 피해를 입는다.
			// HealthPoint 를 원본 그대로 두기 위해 게터를 추가하지 않고,
			// GamePanel 이 유저 HP를 200으로 만드는 것에 맞춰 30% = 60 으로 계산했다.
			gamePanel.getUserHp().decrease(USER_CLASH_DAMAGE);
		}

		repaint();

		// bad 가 2번 이상이면 격돌을 즉시 종료한다.
		if (badCount >= MAX_BAD) {
			nextTimer.stop();
			javax.swing.Timer endTimer = new javax.swing.Timer(700, e -> endClash());
			endTimer.setRepeats(false);
			endTimer.start();
			return;
		}

		nextTimer.start();
	}

	// 다음 입력으로 넘어가거나 모두 끝났으면 격돌을 종료한다.
	private void nextRound() {
		if (!running) return;

		if (currentCount >= totalCount) {
			endClash();
		} else {
			startRound();
		}
	}

	// 격돌 종료 - 게임 화면으로 돌아간다.
	private void endClash() {
		if (!running) return;

		running = false;
		shrinkTimer.stop();
		nextTimer.stop();

		gamePanel.endClash();   // GamePanel 이 게임 스레드를 재개하고 GameFrame 이 화면을 되돌린다.
	}

	// 격돌 화면 그리기
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// 배경 이미지 (없으면 검은 배경만 남는다)
		g2.drawImage(clashImg, 0, 0, getWidth(), getHeight(), this);

		int cx = getWidth() / 2;    // 화면 중앙 x
		int cy = getHeight() / 2;   // 화면 중앙 y

		// 판정 기준이 되는 고정 원
		g2.setColor(new Color(255, 255, 255, 160));
		g2.setStroke(new BasicStroke(3));
		g2.drawOval(cx - TARGET_RADIUS, cy - TARGET_RADIUS, TARGET_RADIUS * 2, TARGET_RADIUS * 2);

		// 시간에 따라 줄어드는 원
		if (running && radius > 0) {
			g2.setColor(circleColor);
			g2.setStroke(new BasicStroke(6));
			g2.drawOval(cx - radius, cy - radius, radius * 2, radius * 2);
		}

		// 맞춰야 하는 문자
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 90));
		String key = String.valueOf(targetKey);
		int keyWidth = g2.getFontMetrics().stringWidth(key);
		g2.drawString(key, cx - keyWidth / 2, cy + 32);

		// 진행 상황
		g2.setFont(new Font("GOTHIC", Font.BOLD, 32));
		g2.setColor(new Color(180, 200, 255));
		g2.drawString("격돌  " + currentCount + " / " + totalCount, 60, 80);
		g2.drawString("BAD  " + badCount + " / " + MAX_BAD, 60, 130);

		// 판정 결과 문구
		if (!judgeText.isEmpty()) {
			if (judgeText.equals("PERFECT")) {
				g2.setColor(Color.YELLOW);
			} else if (judgeText.equals("GOOD")) {
				g2.setColor(new Color(120, 255, 120));
			} else {
				g2.setColor(Color.RED);
			}

			g2.setFont(new Font("Arial", Font.BOLD, 60));
			int textWidth = g2.getFontMetrics().stringWidth(judgeText);
			g2.drawString(judgeText, cx - textWidth / 2, cy - START_RADIUS + 20);
		}
	}

	// 격돌 전용 키 어댑터 - 지정된 문자를 입력한 시점의 원 크기로 판정한다.
	private class ClashKeyAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if (!running || judged) return;

			char input = Character.toUpperCase(e.getKeyChar());

			// 다른 키를 눌렀다면 판정하지 않고 무시한다.
			if (input != targetKey) return;

			int diff = Math.abs(radius - TARGET_RADIUS);    // 기준 원과 현재 원의 차이

			if (diff <= PERFECT_RANGE) {
				judge(true, "PERFECT");
			} else if (diff <= GOOD_RANGE) {
				judge(true, "GOOD");
			} else {
				judge(false, "BAD");
			}
		}
	}
}
