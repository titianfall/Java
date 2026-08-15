package original;

import java.awt.Color;              // 암흑 패턴 단어 입력 성공/진행 상태를 색상으로 표현하기 위해 사용

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;     //암흑 상태에서 키 입력을 받기 위한 키 이벤트 처리

import javax.swing.JLabel;          // 암흑 패턴에서 단어를 구성하는 개별 문자 라벨
import javax.swing.Timer;           // 제한 시간 관리를 위한 Swing Timer
import javax.swing.SwingUtilities;  // 암흑 패턴 종료 후 게임 재개를 EDT에서 안전하게 호출하기 위해 사용

// 보스의 특수 패턴인 암흑 상태를 관리하는 클래스
public class DarkModeController {

	// 저장할 객체 목록
	private final GamePanel gamePanel;                  // 메인 게임 패널
	private final GamePanel.GroundPanel groundPanel;    // 실제 단어가 그려지는 하위 패널
	private final ScorePanel scorePanel;                // 점수 및 게이지 관리 패널
	private final BattleItemPanel battleItemPanel;      // 아이템 및 모코코 상태 표시 패널
	private final TextStore tStore;                     // 단어 저장소 (랜덤 단어 제공)

	// 암흑 패턴 상태 플래그
	private boolean active = false;     // 암흑 모드가 현재 실행 중인지 여부
	private boolean resolved = false;   // 성공/실패가 이미 확정되었는지 여부
	private int failCount = 0;          // 단어 입력 실패 횟수

	private static final int MAX_FAIL = 3;          // 허용되는 최대 실패 횟수
	private static final int LIMIT_TIME = 10000;    // 암흑 패턴 제한 시간 (ms)

	// 암흑 관련 객체
	private DarkWord darkWord;          // 현재 암흑 상태에서 사용 중인 단어 객체
	private DarkTimeBar darkTimeBar;    // 제한 시간을 시각적으로 보여주는 타임 바
	private Timer darkTimer;            // 제한 시간 감소를 담당하는 Swing Timer

	// 키 입력
	private DarkKeyAdapter keyAdapter;      // 암흑 패턴 전용 키 어댑터
	private boolean keyLocked = false;      // 키 연타로 인한 중복 입력 방지 플래그

	// 생성자
	public DarkModeController(GamePanel gamePanel, GamePanel.GroundPanel groundPanel, ScorePanel scorePanel,
			BattleItemPanel battleItemPanel, TextStore tStore) {
		// 객체 저장
		this.gamePanel = gamePanel;
		this.groundPanel = groundPanel;
		this.scorePanel = scorePanel;
		this.battleItemPanel = battleItemPanel;
		this.tStore = tStore;

		// 키 어댑터 객체 생성
		this.keyAdapter = new DarkKeyAdapter();
	}

	// 암흑 모드 활성 여부 반환
	public boolean isActive() {
		return active;
	}

	synchronized public void start() {
		groundPanel.removeKeyListener(keyAdapter);  // 중복 KeyListener 등록 방지

		if (active) return;

		// 상태 초기화
		active = true;
		resolved = false;
		failCount = 0;

		// 게임 정지
		gamePanel.stop();

		// 랜덤 단어 생성
		String text = tStore.get();
		darkWord = new DarkWord(text);

		// 단어를 GroundPanel에 추가
		groundPanel.addDarkWordLabels(darkWord.getTextLabels());

		// 키 어댑터에 라벨 연결
		keyAdapter.setTextLabels(darkWord.getTextLabels());

		// 제한시간 바 생성 및 배치
		darkTimeBar = new DarkTimeBar(LIMIT_TIME);
		darkTimeBar.setSize(300, 20);
		darkTimeBar.setLocation(groundPanel.getWidth() / 2 - 150, groundPanel.getHeight() / 2 + 50);
		groundPanel.add(darkTimeBar);

		// 제한 시간 감소용 타이머 (100ms 단위)
		darkTimer = new Timer(100, e -> tick());
		darkTimer.start();

		// 키 입력 포커스 설정
		this.groundPanel.addKeyListener(keyAdapter);

		groundPanel.setFocusable(true);
		groundPanel.requestFocus();
	}

	// 암흑 모드 강제 종료
	public void exit() {
		if (!active) return;

		active = false;
		resolved = false;
		if (darkTimer != null) {
			darkTimer.stop();
			darkTimer = null;
		}

		groundPanel.removeKeyListener(keyAdapter);
		System.out.println("[DarkMode] KeyListener removed");
		if (darkWord != null) {
			groundPanel.removeDarkWordLabels(darkWord.getTextLabels());
			darkWord = null;
		}

		if (darkTimeBar != null) {
			groundPanel.remove(darkTimeBar);
			darkTimeBar = null;
		}

		// 암흑 게이지 초기화
		scorePanel.clearDark();
	}

	// 성공 처리
	public void success() {
		finish(true);
	}

	// 실패 처리
	public void fail() {
		finish(false);
	}

	// 제한 시간 감소 처리
	private void tick() {
		if (resolved || !active) return;
		int remain = darkTimeBar.getTime() - 100;
		darkTimeBar.setTime(remain);

		if (remain <= 0) {
			fail();
		}
	}

	// 암흑 패턴 종료 처리
	private void finish(boolean success) {

		System.out.println("[DarkMode] finish() called. success=" + success);
		if (resolved) return;
		resolved = true;
		active = false;

		// 타이머 종료
		if (darkTimer != null) {
			darkTimer.stop();
			darkTimer = null;
		}

		// UI 제거
		if (darkWord != null) {
			groundPanel.removeDarkWordLabels(darkWord.getTextLabels());
			darkWord = null;
		}

		if (darkTimeBar != null) {
			groundPanel.remove(darkTimeBar);
			darkTimeBar = null;
		}

		// 보상 / 페널티
		if (success) {
			scorePanel.increase(300);
			battleItemPanel.changeImageToSuccess();
		} else {
			scorePanel.increase(-100);
			battleItemPanel.changeImageToFail();
		}

		// 암흑 게이지 초기화
		scorePanel.clearDark();
		failCount = 0;
		// 화면 갱신
		groundPanel.repaint();

		// 게임 재개
		SwingUtilities.invokeLater(() -> gamePanel.start());
	}

	// 실패 시 단어 교체
	private void replaceWord() {
		failCount = 0;
		if (darkWord != null) {
			groundPanel.removeDarkWordLabels(darkWord.getTextLabels());
		}

		String text = tStore.get();
		darkWord = new DarkWord(text);

		groundPanel.addDarkWordLabels(darkWord.getTextLabels());
		keyAdapter.setTextLabels(darkWord.getTextLabels());
		groundPanel.revalidate();
		groundPanel.repaint();
	}

	// 키 입력 처리
	private class DarkKeyAdapter extends KeyAdapter {
		private JLabel[] labels;    // 현재 입력 대상 문자 라벨 배열
		private int index;          // 뒤에서부터 입력하기 위한 인덱스

		// 라벨 설정
		public void setTextLabels(JLabel[] labels) {
			this.labels = labels;
			if (labels == null || labels.length == 0) {
				this.index = -1;
				return;
			}
			// 뒤에서부터 인덱스 설정
			this.index = labels.length - 1;
		}

		@Override
		public void keyPressed(KeyEvent e) {

			// 키 연타 방지
			if (keyLocked) return;
			keyLocked = true;

			// 상태 확인
			if (!active || labels == null || resolved) return;
			if (index < 0) return;

			char input = e.getKeyChar();
			char target = labels[index].getText().charAt(0);

			// 올바른 입력
			if (input == target) {
				labels[index].setForeground(Color.GRAY);
				index--;

				if (index < 0) {
					success();
				}
			}
			// 틀린 입력
			else {
				failCount++;

				if (failCount >= MAX_FAIL) {
					fail();
				} else {
					replaceWord();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

			keyLocked = false;
		}
	}
}
