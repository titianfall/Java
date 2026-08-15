package original;

import java.awt.BorderLayout;    // 게임화면 구성을 위한 레이아웃 매니저 import
import java.awt.Toolkit;        // [해상도 호환] 화면 해상도에 맞춰 분할 위치를 잡기 위해 사용

import javax.swing.JPanel;      // 최상위 컨테이너 역할을 하는 JPanel import
import javax.swing.JSplitPane;  // 게임화면을 고정 비율로 분할하기 위해 사용한다.

// 게임 화면 전체 레이아웃을 담당하는 컨테이너 패널 이자 GamePanel, ScorePanel, BattleItemPanel을 배치한다.
public class GameContainerPanel extends JPanel {

	// 생성자
	public GameContainerPanel(ScorePanel scorePanel, GamePanel gamePanel, BattleItemPanel battleItemPanel) {
		setLayout(new BorderLayout());  // 배치관리자 설정 중앙 영역에 hPane을 부착하기 위해 사용합니다.

		// [해상도 호환] 원본은 setDividerLocation(2300)으로 2560 폭 모니터를 가정했다.
		// FHD(1920) 환경에서는 우측 패널이 화면 밖으로 밀려나므로 화면 폭 기준으로 계산하도록 바꿨다.
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int rightAreaWidth = 560;                                   // 점수 + 아이템 패널이 필요로 하는 폭
		int dividerX = Math.max(600, screenWidth - rightAreaWidth);  // 좌측 게임 화면 폭

		JSplitPane hPane = new JSplitPane();                        // 좌우 분할용 객체 생성
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);          // 좌우로 분할 설정
		hPane.setDividerLocation(dividerX);                         // 좌측 게임화면 영역 지정
		hPane.setEnabled(false);                                    // 사용자가 Divider를 움직이지 못하도록 설정합니다.
		add(hPane, BorderLayout.CENTER);                            // CENTER 부분에 좌우분할 패널을 배치합니다.

		JSplitPane vPane = new JSplitPane();                        // 상하 분할용 객체 생성
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);            // 상하로 분할 설정
		vPane.setDividerLocation(500);                              // 점수 패널 높이 설정
		vPane.setEnabled(false);                                    // 사용자가 핸들링하지 못하도록 설정
		vPane.setTopComponent(scorePanel);                          // 점수 패널을 상단에 배치
		vPane.setBottomComponent(battleItemPanel);                  // 사용자 hp 및 사용가능한 아이템 목록을 하단에 배치

		hPane.setRightComponent(vPane);                             // 오른쪽에 vPane을
		hPane.setLeftComponent(gamePanel);                          // 왼쪽에 게임 패널을 붙입니다.
	}
}
