import java.awt.*; //JFrame
import javax.swing.*;//Color Container
public class P1 extends JFrame{
    public P1(){
        setTitle("Make a Window using Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setBackground(Color.YELLOW);

        setSize(400,150);
        setVisible(true);
    }
    public static void main(String[] args) {
        new P1();
    }
}
