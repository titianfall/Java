package original;

// 그래픽 관련
import java.awt.Color;      // 시간 바의 색상을 지정하기 위해 사용
import java.awt.Graphics;   // 커스텀 바를 직접 그리기 위한 그래픽 객체

import javax.swing.JLabel;  // 시간 바로 커스텀바로 사용합니다.

// 암흑 패턴 제한시간을 나타내는 바
class DarkTimeBar extends JLabel {

	// 시간 상태값
	private int maxTime;    // 시간 최대값
	private int time;       // 남은 시간

	// 생성자
	public DarkTimeBar(int maxTime) {
		// 전체 시간 및 남은시간 초기화
		this.maxTime = maxTime;
		this.time = maxTime;

		setOpaque(true);                // 배경을 직접 그리기 위해 Opaque 설정한다.
		setBackground(Color.BLACK);     // 바의 배경을 검정색으로 설정한다.
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);    // 기본 페인팅 수행 배경색이 이때 그려집니다.

		int width = (int) ((getWidth() * time) / (double) maxTime);  // 현재 시간에 대한 바의 너비 계산

		g.setColor(Color.YELLOW);                       // 시간 바 색상 설정
		g.fillRect(0, 0, width, this.getHeight());      // 계산된 너비만큼 사각형을 채워 시간 바를표현합니다.
	}

	// 시간 값 설정 메소드
	public void setTime(int time) {
		this.time = time;   // 외부에서 전달받은 새로운 시간 값으로 상태를 갱신 - 남은 시간을 갱신하는데 사용

		repaint();          // 다시 그리도록 요청합니다.
	}

	// 현재 시간 값을 반환합니다.
	public int getTime() {
		return time;    // 남은 시간 값을 반환 - 남은 시간을 갱신하는데 사용
	}
}
