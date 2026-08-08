import java.awt.*;
import javax.swing.*;
public class E4 extends JFrame{
    public E4(){
        setTitle("BorderLayout 배치관리자");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();

        c.setLayout(new BorderLayout());
        c.add(new JButton("Calculate"),BorderLayout.CENTER);
        c.add(new JButton("add"),BorderLayout.NORTH);
        c.add(new JButton("sub"),BorderLayout.SOUTH);
        c.add(new JButton("div"),BorderLayout.WEST);
        c.add(new JButton("mul"),BorderLayout.EAST);

        setSize(300,200);
        setVisible(true);
    }
    public static void main(String[] args) {
        new E4();//BorderLayout
    }
}
