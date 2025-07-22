import java.awt.*;
import javax.swing.*;
public class P4 extends JFrame{
    public P4(){
        setTitle("GridLayout으로 10개의 버튼을 배치한 프레임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();

        c.setLayout(new GridLayout(1,10));
        
        Color [] color = {Color.RED, Color.ORANGE, Color.YELLOW,
             Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA,
              Color.GRAY, Color.PINK, Color.LIGHT_GRAY};
        for(int i=0;i<10;i++){
            JButton btn = new JButton(Integer.toString(i));
            btn.setBackground(color[i]);
            c.add(btn);
        }  
        setSize(600,200);//사이즈가 너무 작으면 ... 으로 표기됨
        setVisible(true);
    }
    public static void main(String[] args) {
        new P4();
    }
}
