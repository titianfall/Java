import java.awt.*;
import javax.swing.*;
public class P2 extends JFrame{
    public P2(){
        setTitle("BorderLayout 배치 관리자 연습");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        
        c.setBackground(Color.yellow);
        c.setLayout(new BorderLayout(5,7));
        c.add(new JButton("North"),BorderLayout.NORTH);
        c.add(new JButton("west"),BorderLayout.WEST);
        c.add(new JButton("east"),BorderLayout.EAST);
        c.add(new JButton("south"),BorderLayout.SOUTH);
        c.add(new JButton("center"),BorderLayout.CENTER);

        setSize(300,200);
        setVisible(true);
    }
    public static void main(String[] args) {
        new P2();
    }
}
