import java.awt.*;
import javax.swing.*;

public class E14 extends JFrame {
	public E14() {
		setTitle("슬라이더 만들기 예제");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
		slider.setPaintLabels(true);//label표시
		slider.setPaintTicks(true);//눈금
		slider.setPaintTrack(true);//트랙
		slider.setMajorTickSpacing(50);//작은 눈금감격 조절
		slider.setMinorTickSpacing(10);//큰눈금 감격
		c.add(slider);
		
		setSize(300,100);
		setVisible(true);
	}

	public static void main(String [] args) {
		new E14();
	}
}