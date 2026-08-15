package original;

// 그래픽 관련
import java.awt.Color;  // HP 바 및 아이템 개수 텍스트 색상 설정에 사용
import java.awt.Font;   // 아이템 개수 표시 라벨의 폰트 설정

// 아이템 이미지를 클릭하여 사용하는 이벤트 처리를 위해 사용
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;   // ImageIcon에서 실제 이미지를 로딩시 사용
import javax.swing.JPanel;      // BattelItemPanel의 최상위 컨테이너
import javax.swing.JLabel;      // 아이템 이미지 및 개수 표시를 위한 컴포넌트

// 전투중 플레이어 HP, 배틀 아이템, 상태 아이콘 관리
public class BattleItemPanel extends JPanel {

	// 아이템 사용효과(데미지 증가, 단어 삭제)를 반영하기 위해
	private GamePanel gamePanel;

	private HealthPoint userHp;     // 플레이어 HP를 표시하고 조작하기 위한 객체

	// 상태 아이콘
	private ImageIcon mokokoBasic = new ImageIcon("images/mokoko.jpg");          // 기본 상태 이미지(드가자)
	private ImageIcon decreaseMokoko = new ImageIcon("images/decrease.jpg");     // 실패 상태(체력 감소) 이미지
	private ImageIcon powerMokoko = new ImageIcon("images/power.jpg");           // 공격력 증가 상태 이미지
	private ImageIcon usePotionMokoko = new ImageIcon("images/usePotion.jpg");   // 회복 아이템 사용시 이미지
	private JLabel mokoko = new JLabel(mokokoBasic);                             // 현재 상태를 표시하는 라벨(기본상태)

	// 배틀 아이템이 0개일 경우 사용하는 이미지
	private ImageIcon emptyIcon = new ImageIcon("images/empty.jpg");

	// 회복물약 - HP20% 회복
	private ImageIcon healingPotionIcon = new ImageIcon("images/healingPotion.jpg");  // 물약이 1개라도 있을경우 사용하는 이미지
	private JLabel healingPotion;   // 포션 이미지를 출력할 라벨
	private int potionAmount;       // 물약 수량

	// 아드로핀 - HP를 25% 감소키지만 적에게 주는 데미지를 증가시킵니다.
	private ImageIcon atropineIcon = new ImageIcon("images/atropine.jpg");   // 아드로핀이 1개라도 있을경우
	private JLabel atropine;        // 아드로핀 이미지를 출력할 라벨
	private int atropineAmount;     // 아드로핀 개수

	// 파괴폭탄 - 무작위 단어 최대 5개 삭제
	private ImageIcon destructionBombIcon = new ImageIcon("images/destructionBomb.jpg");  // 파괴폭탄이 하나라도 있을경우
	private JLabel destructionBomb;     // 파괴폭탄 이미지를 출력할 라벨
	private int destructionBombAmount;  // 파괴폭탄 개수

	// 개수를 보여주는 라벨
	private JLabel potionCount;             // x3
	private JLabel atropineCount;           // x3
	private JLabel destructionBombCount;    // x3

	// 생성자
	public BattleItemPanel(HealthPoint userHp, GamePanel gamePanel) {

		// 객체 레퍼런스 저장
		this.gamePanel = gamePanel;
		this.userHp = userHp;

		// 레이아웃 설정
		setLayout(null);

		// HP 바 - 검은 배경, 현재 HP는 빨간색
		userHp.setSize(500, 60);
		userHp.setLocation(0, 0);
		userHp.setBackground(Color.RED);
		add(userHp);

		// 상태 이미지(모코코)
		mokoko.setSize(200, 200);
		mokoko.setLocation(150, 80);
		add(mokoko);

		// 라벨 생성(포션, 아드로핀, 파괴폭탄)
		healingPotion = new JLabel();
		atropine = new JLabel();
		destructionBomb = new JLabel();

		// 배틀 아이템 개수 라벨 생성
		potionCount = createCountLabel(0);
		atropineCount = createCountLabel(0);
		destructionBombCount = createCountLabel(0);

		// 배치
		healingPotion.setBounds(100, 300, 100, 100);
		potionCount.setBounds(145, 280, 40, 30);

		atropine.setBounds(200, 300, 100, 100);
		atropineCount.setBounds(245, 280, 40, 30);

		destructionBomb.setBounds(300, 300, 100, 100);
		destructionBombCount.setBounds(340, 280, 40, 30);

		healingPotion.setToolTipText("사용시 체력을 20퍼센트만큼 회복합니다.");   // 툴팁 설명

		// 익명 리스너 등록 - HP20% 회복, 상태 아이콘 변경, 아이템 개수 감소 및 개수에 따른 아이콘 설정
		healingPotion.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (potionAmount <= 0) return;

				userHp.heal(20);
				mokoko.setIcon(usePotionMokoko);

				potionAmount--;
				potionCount.setText(Integer.toString(potionAmount));

				if (potionAmount == 0) {
					healingPotion.setIcon(emptyIcon);
					healingPotion.setEnabled(false);
				}
			}
		});

		atropine.setToolTipText("사용시 체력이 25%감소하지만 데미지가 2배로 증가합니다");   // 툴팁 설명

		// 익명 리스너 등록 - HP25% 감소, 상태 아이콘 변경, 아이템 개수 감소 및 개수에 따른 아이콘 설정
		atropine.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (atropineAmount <= 0) return;

				userHp.takeDamage(25);
				gamePanel.damageBoost();
				mokoko.setIcon(powerMokoko);

				atropineAmount--;
				atropineCount.setText(Integer.toString(atropineAmount));

				if (atropineAmount == 0) {
					atropine.setIcon(emptyIcon);
					atropine.setEnabled(false);
				}
			}
		});

		destructionBomb.setToolTipText("화면상의 단어를 최대 5개 무작위로 지워줍니다!");    // 툴팁 설명

		// 익명 리스너 등록 - 아이템 개수 감소 및 개수에 따른 아이콘 설정
		destructionBomb.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (destructionBombAmount <= 0) return;

				gamePanel.destructionBomb();

				destructionBombAmount--;
				destructionBombCount.setText(Integer.toString(destructionBombAmount));

				if (destructionBombAmount == 0) {
					destructionBomb.setIcon(emptyIcon);
					destructionBomb.setEnabled(false);
				}
			}
		});

		// 패널에 컴포넌트들을 추가합니다.
		add(healingPotion);
		add(potionCount);
		add(atropine);
		add(atropineCount);
		add(destructionBomb);
		add(destructionBombCount);

		// 아이템 개수 및 아이콘을 설정하고 터치 가능여부와 사이즈 변경에 따른 재배치와 다시 그릴것을 요청합니다.
		init();
	}

	// 아이템 개수 라벨 생성 메소드
	private JLabel createCountLabel(int amount) {

		JLabel label = new JLabel(Integer.toString(amount));
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Arial", Font.BOLD, 18));
		label.setOpaque(false);
		label.setSize(40, 30);
		return label;
	}

	// 초기화 게임시작 및 게임 도중 exit시 호출됩니다.
	public void init() {

		// 배틀 아이템 개수
		potionAmount = 3;
		atropineAmount = 3;
		destructionBombAmount = 3;

		// 배틀 아이템 아이콘 설정
		healingPotion.setIcon(healingPotionIcon);
		atropine.setIcon(atropineIcon);
		destructionBomb.setIcon(destructionBombIcon);

		// 활성화
		healingPotion.setEnabled(true);
		atropine.setEnabled(true);
		destructionBomb.setEnabled(true);

		// 개수 표시 갱신
		potionCount.setText(Integer.toString(potionAmount));
		atropineCount.setText(Integer.toString(atropineAmount));
		destructionBombCount.setText(Integer.toString(destructionBombAmount));

		// 상태 이미지 초기화
		mokoko.setIcon(mokokoBasic);

		revalidate();   // 재배치
		repaint();      // 그리기
	}

	// 게임 재시작 exit > start 호출시 실행
	public void reset() {
		init();
	}

	// 단어를 맞추면 호출하는 아이콘
	public void changeImageToSuccess() {
		mokoko.setIcon(mokokoBasic);
	}

	// 데미지를 입으면 호출하는 아이콘
	public void changeImageToFail() {
		mokoko.setIcon(decreaseMokoko);
	}

	// 기본상태 아이콘 (복귀를 위한)
	public void changeImageToNormal() {
		mokoko.setIcon(mokokoBasic);
	}
}
