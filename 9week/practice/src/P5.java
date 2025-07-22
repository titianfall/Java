import java.awt.*;
import javax.swing.*;
public class P5 extends JFrame{
    public P5(){
        setTitle("4x4 Color 프레임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Container c = getContentPane();
        c.setLayout(new GridLayout(4,4));
        Color[] color = {Color.RED, Color.ORANGE, Color.YELLOW, 
            Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA,
            Color.GRAY, Color.PINK, Color.LIGHT_GRAY, 
            Color.WHITE, Color.BLACK, Color.RED, Color.ORANGE, 
            Color.YELLOW, Color.GREEN, Color.CYAN};
        
        for(int i=0;i<16;i++){
            JButton btn = new JButton(Integer.toString(i));
            btn.setBackground(color[i]);
            c.add(btn);
        }

        setSize(600,300);
        setVisible(true);
    }
    public static void main(String[] args) {
        new P5();
    }
}
