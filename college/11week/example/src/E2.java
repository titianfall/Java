import java.awt.*;
import javax.swing.*;
public class E2 extends JFrame{
    public E2(){
        super("레이블 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c =getContentPane();
        c.setLayout(new FlowLayout());

        //문자열 레이블 생성
        JLabel textLabel = new JLabel("사랑합니다.");

        //이미지 레이블 생성
        ImageIcon sulY = new ImageIcon("sul2.jpg");
        JLabel sul = new JLabel(sulY);

        //문자열 이미지를 모두 가진 레이블 생성
        ImageIcon sulY2 = new ImageIcon("sul3.jpg");
        JLabel sul2 = new JLabel("단국대 축제", sulY2, SwingConstants.CENTER);
        //맨뒤의 정렬 선택도 해주어야함

        c.add(textLabel);
        c.add(sul);
        c.add(sul2);

        setSize(400,600);
        setVisible(true);

    }
    public static void main(String[] args) {
        new E2();
    }
}
