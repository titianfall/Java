import java.awt.*;
import javax.swing.*;
public class P6 extends JFrame{
    public P6(){
        setTitle("배치관리자 없는 컨테이너 작성");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);
        Color [] color = {Color.RED, Color.ORANGE, Color.YELLOW,
                    Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA,
                    Color.GRAY, Color.PINK, Color.LIGHT_GRAY};
        for(int i=0;i<20;i++){
            int x = (int)(Math.random()*241)+10; //0~240 
            int y = (int)(Math.random()*241)+10;
            int z = (int)(Math.random()*10);
            JLabel lbl = new JLabel();
            lbl.setBackground(color[z]);
            lbl.setLocation(x,y);
            lbl.setSize(10,10);
            lbl.setOpaque(true);
            c.add(lbl);
        }
        setSize(300,300);
        setVisible(true);
    }
    public static void main(String[] args) {
        new P6();
    }
}
