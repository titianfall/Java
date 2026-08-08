import java.awt.*;
import javax.swing.*;
public class E4 extends JFrame{
    public E4(){
        super("체크박스 만들기 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c =getContentPane();
        c.setLayout(new FlowLayout());

        ImageIcon sul1 = new ImageIcon("sul3.jpg");
        ImageIcon sul2 = new ImageIcon("sul2.jpg");

        JCheckBox apple = new JCheckBox("사과");
        JCheckBox pear = new JCheckBox("배",true);
        JCheckBox pajame = new JCheckBox("설윤",sul2);

        pajame.setBorderPainted(true);//체크박스 외곽선이 보이도록
        pajame.setSelectedIcon(sul1);

        c.add(apple);
        c.add(pear);
        c.add(pajame);

        setSize(300,300);
        setVisible(true);
    }
    public static void main(String[] args) {
        new E4();
    }
}
