
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class P4 extends JFrame{
	private MyPanel panel = new MyPanel(); 

	public P4() {
		setTitle("이미지 레이블 드래깅 연습");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);		// panel을 CONTENTPANE으로 설정
		
        setVisible(true);
		setSize(500, 500);
	}
	
	class MyPanel extends JPanel{
		private ImageIcon image = new ImageIcon("sul3.jpg");
		private Image img = image.getImage();
		private Point currentPoint = new Point(20,20);
		@Override
        public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, currentPoint.x, currentPoint.y , this);	// 이미지의 크기에 따라서 가변적으로 설정
			addMouseMotionListener(new MouseMotionAdapter() {	// 마우스 어댑터를 익명 클래스를 활용하여 구현
				public void mouseDragged(MouseEvent e) {
					currentPoint = e.getPoint();
					repaint(); // 현재 마우스의 좌표를 받아온 후 repaint()
				}
			});
		}
	}
	public static void main(String[] args) {
		new P4();
	}
}