import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class P5 extends JFrame {
	public P5() {
		super("클릭 연습 응용프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null); // 배치관리자 제거
		
		JLabel label = new JLabel("C");
		c.add(label);
		
		// 레이블에 절대 위치 지정
		label.setLocation(100,100);
		label.setSize(20, 20);
		
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JLabel la = (JLabel)e.getSource();
				Container c = la.getParent(); // 클릭된 레이블의 컨테이너 알아내기
				
				// 컨테이너 내에서 랜덤한 레이블 위치 설정
				int xBound = c.getWidth() - la.getWidth(); // 레이블의 폭 만큼 감소
				int yBound = c.getHeight() - la.getHeight(); // 레이블의 높이 만큼 감소				
				int x = (int)(Math.random()*xBound);
				int y = (int)(Math.random()*yBound);				
				la.setLocation(x, y); // 레이블의 위치 이동
			}
		});

		setSize(300,200);
		setVisible(true);
	}
	static public void main(String [] args) {
		new P5();
	}
}
