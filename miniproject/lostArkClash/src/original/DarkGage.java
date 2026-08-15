package original;

// 그래픽 관련
import java.awt.BasicStroke;   // 원호 두께를 설정하기 위해 사용
import java.awt.Color;         // 원호 및 배경 색상 설정에 사용

// Graphics2D를 사용하여 setStroke(), drawArc() 두께, 원호 그리기를 사용
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;   // 암흑 게이지 배경 이미지 로딩
import java.awt.Image;          // ImageIcon에서 실제 이미지를 추출하기 위해 사용

import javax.swing.JComponent;  // 커스텀 드로잉을 위한 컴포넌트 사용

// 나만의 커스텀 드로잉 컴포넌트 만들기 - 원호 모양
class DarkGage extends JComponent implements Runnable {

	private int percent;        // 원호의 게이지 정도를 나타내는 필드입니다.
	private int arcAngle;       // 원호의 전체 각도 (음수를 사용하여 시계 방향으로 증가하도록 설정)
	private int filledAngle;    // 현재 채워진 원호 각도

	private ImageIcon darkNormal = new ImageIcon("images/darkGage.jpg");    // 게이지가 채워지지 않은 기본 상태 이미지
	private ImageIcon darkFull = new ImageIcon("images/darkFull.jpg");      // 게이지가 가득 찼을 때 (암흑 상태) 이미지
	private Image darkImg = darkNormal.getImage();                          // 현재 출력중인 배경 이미지

	// 스레드 상태 제어 플래그
	private boolean pause = false;  // 일시 정지상태 도달
	private boolean dark = false;   // 암흑상태임을 알리는 불린 필드

	// 생성자
	public DarkGage() {
		this.percent = 0;           // 게임 시작시 게이지 필드를 1로 초기화합니다.
		this.filledAngle = 0;       // 채워지는 필드는 1로 초기화
		this.arcAngle = -240;       // 원호 각도이자 시계방향으로 그립니다.
	}

	// 암흑 게이지만을 위한 스레드 생성
	@Override
	public void run() {
		// 게임이 종료하기 전까지 무한루프
		while (true) {
			synchronized (this) {
				// 일시 정지 상태라면 재개될 때까지 대기
				while (pause) {
					try {
						wait();
					} catch (InterruptedException e) {
						// 인터럽트 발생 시 스레드 종료
						return;
					}
				}
			}

			try {
				Thread.sleep(100);

				fill(1);    // 게이지 1% 증가
			} catch (InterruptedException e) {
				// 인터럽트 발생 시 스레드 종료
				return;
			}
		}
	}

	// 원호 게이지를 생성합니다.
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);    // 기본 컴포넌트 페인팅 처리

		Graphics2D g2 = (Graphics2D) g;     // 선 두께조절을 사용하기 위해 업캐스팅

		// 원호 좌표, 크기 및 각도 설정
		int x = 22;                     // x 좌표
		int y = 13;                     // y 좌표
		int w = getWidth() - x * 2;     // 가로축 크기
		int h = getHeight() - y * 2;    // 세로축 크기
		int startAngle = 210;           // 원호의 시작 각도

		int size = 8;                   // 원호의 선 두께

		g.drawImage(darkImg, 0, 0, getWidth(), getHeight(), this);   // 배경

		// 게이지의 배경이 되는 원호 - 비어있는 이미지
		g2.setColor(Color.BLACK);                       // 그려지는 색을 검정색으로 설정합니다.
		g2.setStroke(new BasicStroke(size));            // 선의 두께를 조절합니다.
		g2.drawArc(x, y, w, h, startAngle, arcAngle);   // startAngle 부터 arcAngle까지 원호를 그립니다.(시계방향)

		// 채워지는 원호 게이지 - 파란계열 색상
		g2.setColor(new Color(0, 120, 255));                // 채워지는 원호의 색은 파란계열의 색입니다.
		g2.setStroke(new BasicStroke(size));                // 선의 두께를 10으로 조절합니다.
		g2.drawArc(x, y, w, h, startAngle, filledAngle);    // 초기 게이지는
	}

	// 게이지 증가 처리
	public void fill(int percent) {
		this.percent += percent;                                    // 퍼센트 증가
		filledAngle = (int) (arcAngle * (this.percent / 100.0));    // 현재 퍼센트 비율에 맞는 각도 계산

		// 게이지가 가득 찼을 경우
		if (this.percent >= 100) {
			this.percent = 100;         // 퍼센트 최대값 고정
			filledAngle = arcAngle;

			dark = true;        // 암흑 상태 활성화
			setDark(true);      // 암흑 상태로 전환

			//repaint();        // 갱신

			pauseThread();      // 정지 상태로 만듭니다.
		}
		repaint();  // 게이지 변경 사항 화면 반영
	}

	// 암흑 상태 설정
	synchronized public void setDark(boolean isfull) {
		// 게이지가 가득 찼을 경우
		if (isfull) {
			darkImg = darkFull.getImage();  // 암흑 상태용 이미지 설정
			pauseThread();                  // 게이지 스레드 정지
		} else {
			setNormal();    // 노말 상태로 복귀
			notify();       // 대기중인 스레드 깨우기
		}
		repaint();  // 갱신
	}

	// 게임 패널에서 현재 정산 게이지가 다 찼는지 알기위한 게터
	public boolean getDark() {
		return dark;    // dark 가 true이면 암흑 상태이다.
	}

	// 스레드 일시 정지
	synchronized public void pauseThread() {
		pause = true;
	}

	// 스레드 재개
	synchronized public void resumeThread() {
		pause = false;
		notify();
	}

	// 게이지 초기화
	public void init() {
		setNormal();        // 노말 상태로 복귀
		repaint();          // 갱신
		resumeThread();     // 게이지 스레드 재개
	}

	// 노말 상태로 복귀, 게이지 삭제, 암흑상태 및 정지상태 플래그 초기화
	public void setNormal() {
		darkImg = darkNormal.getImage();

		percent = 0;
		filledAngle = 0;

		dark = false;
		pause = false;
	}
}
