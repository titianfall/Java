package original;

// 그래픽 및 이벤트 처리 관련 import
import java.awt.Color;          // 랭킹 텍스트 및 강조 색상 설정에 사용
import java.awt.Font;           // 랭킹 텍스트, 타이틀 폰트 설정에 사용

import java.awt.Graphics;
import java.awt.Graphics2D;     // Graphics2D를 사용하여 폰트, 색상 등 고급 렌더링 처리
import java.awt.Image;          // 배경 이미지 출력을 위한 Image 객체

// 버튼 클릭 이벤트 처리
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 파일 입출력 관련 import
import java.io.BufferedReader;  // 랭킹 파일을 한 줄씩 읽기 위한 스트림
import java.io.BufferedWriter;  // 랭킹 파일에 쓰기 위한 스트림
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;     // 입출력간 예외 처리

import java.util.Vector;        // 랭킹 목록을 저장하기 위한 컬렉션

// 컴포넌트
import javax.swing.ImageIcon;   // 배경 이미지 로딩
import javax.swing.JButton;     // 시작 화면으로 돌아가기 버튼
import javax.swing.JPanel;

// Rangking 데이터 클래스 - 이름, 점수 표현
class Rangking {

	public String name;     // 이름
	public int score;       // 점수

	// 생성자
	public Rangking(String name, int score) {
		this.name = name;       // 이름 초기화
		this.score = score;     // 점수 초기화
	}
}

// Top 10 랭킹을 출력하는 화면
public class LeaderboardPanel extends JPanel {

	// 배경 이미지
	private ImageIcon rangkingIcon = new ImageIcon("images/leaderboard.png");
	private Image rangkingImage = rangkingIcon.getImage();

	// 랭킹 데이터
	private Vector<Rangking> list = new Vector<>();
	private int startY = 220;   // 첫 줄 y
	private int gapY = 55;      // 줄 간격

	private int leftX = 140;                // 왼쪽 랭킹 x
	private int rightX = getWidth() - 520;  // 오른쪽 랭킹 x

	private JButton backBtn = new JButton("돌아가기");    // 시작 화면으로 돌아가는 역할을 하는 버튼

	// 생성자
	public LeaderboardPanel(GameFrame gameFrame) {

		loadRanking();      // 랭킹 ranking.txt 파일 로드
		setLayout(null);    // 배치관리자 제거

		backBtn.setFont(new Font("GOTHIC", Font.BOLD, 24));     // 돌아가기 버튼 폰트 지정
		backBtn.setSize(150, 150);                              // 돌아가기 버튼 사이즈 설정
		backBtn.setLocation(20, 20);                            // 돌아가기 버튼 위치 설정

		// 익명 리스너 설정 - 시작 화면이 보이도록 호출
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showStart();
			}
		});
		add(backBtn);   // 돌아가기 버튼 부착
	}

	// 랭킹 파일 로드 파일을 읽어 list에 저장합니다.
	public void loadRanking() {
		File f = new File("data/ranking.txt");  // 상대경로로 파일 객체 생성

		try {
			FileReader fin = new FileReader(f);  //
			BufferedReader br = new BufferedReader(fin);
			String line;

			while ((line = br.readLine()) != null) {
				String[] tok = line.split(",");
				list.add(new Rangking(tok[0], Integer.parseInt(tok[1])));
			}

			fin.close();    // 자원 해제
			sort();         // 로딩 후 정렬한다.
		} catch (IOException e) {   // 파일이 없을 경우, 읽기 등 오류 예외 처리
			e.printStackTrace();
		}
	}

	// 정렬 - Top 10
	public void sort() {
		list.sort((a, b) -> b.score - a.score);     // 내림차순
	}

	// 랭킹 저장 - 현재 list를 rangking.txt에 덮어쓴다.
	public void save() {

		File f = new File("data/ranking.txt");  // 랭킹 파일 객체 생성
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(f));

			// list의 모든 항목을 한줄씩 저장한다.
			for (Rangking e : list) {

				bw.write(e.name + "," + e.score);   // 이름, 점수 추가
				bw.newLine();                       // 줄바꿈 추가
			}
		} catch (IOException e) {   // 파일 쓰기 예외 처리
			e.printStackTrace();
		}
	}

	// 랭킹 추가
	public void add(String name, int score) {

		list.add(new Rangking(name, score));    // 새로운 랭킹 항목 추가

		sort();     // 재정렬 및 순위 최신화

		// 랭킹은 최대 10명까지 저장해야한다.
		if (list.size() > 10) {
			list.setSize(10);   // 10개 초과분 제거
		}

		save();     // 갱신된 랭킹을 파일에 저장합니다.
	}

	// 화면 렌더링 - 배경이미치 출력, 랭킹 텍스트를 그린다.
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 배경 이미지 출력
		g.drawImage(rangkingImage, 0, 0, getWidth(), getHeight(), this);

		// 캐스팅을 통해 폰트/색상을 제어
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(new Font("GOTHIC", Font.BOLD, 40));

		// 랭킹 출력 레이아웃 설정
		int startY = 300;   // 첫줄 y
		int gapY = 120;     // y줄 간격

		int leftX = 600;                    // 왼쪽 x 좌표
		int rightX = getWidth() - 800;      // 오른쪽 x좌표

		// 부족하지 않을경우에는 이름과 점수 꼴로 출력한다.
		for (int i = 0; i < 10; i++) {

			int rank = i + 1;

			int x, y;
			// 5위까지는 왼쪽 6등부터는 오른쪽에 배치한다.
			if (i < 5) {
				x = leftX;
				y = startY + (i * gapY);
			} else {
				x = rightX;
				y = startY + ((i - 5) * gapY);
			}

			// 데이터가 없을경우
			String name = "---";
			String score = "---";

			// 실제 데이터가 있는 경우 list에서 값을 가져옴
			if (i < list.size()) {
				name = list.get(i).name;
				score = String.valueOf(list.get(i).score);
			}

			// 1등 강조 - 노란색
			if (rank == 1 && i < list.size()) {
				g2.setColor(Color.YELLOW);
				g2.setFont(new Font("GOTHIC", Font.BOLD, 36));
			} else {
				g2.setColor(Color.WHITE);
				g2.setFont(new Font("GOTHIC", Font.BOLD, 30));
			}

			// 순위 이름 점수 한줄로 출력
			g2.drawString(rank + ". " + name + " " + score, x, y);
		}

		g2.setFont(new Font("GOTHIC", Font.BOLD, 60));  // 폰트 설정
		g2.setColor(new Color(180, 200, 255));          // 색상 설정

		String title = "TOP 10";                    // 제목 설정
		int titleX = (getWidth()) / 2 - 100;        // 중앙 정렬

		// 상단에 타이틀 출력
		g2.drawString(title, titleX, 120);
	}
}
