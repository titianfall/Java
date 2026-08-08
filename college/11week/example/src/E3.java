import java.awt.*;
import javax.swing.*;
public class E3 extends JFrame{
    public E3(){
        super("이미지 버튼 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c =getContentPane();
        c.setLayout(new FlowLayout());

        ImageIcon normal = new ImageIcon("sul2.jpg");
        ImageIcon rollover = new ImageIcon("sul3.jpg");
        ImageIcon pressIcon = new ImageIcon("sul4.jpg");
        
        JButton b = new JButton("설윤입니다",normal);
        b.setPressedIcon(pressIcon);
        b.setRolloverIcon(rollover);

        c.add(b);

        setSize(300,400);
        setVisible(true);
        
    }
    public static void main(String[] args) {
        new E3();
    }
}
