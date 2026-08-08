import java.awt.*;
import javax.swing.*;
public class E4 extends JFrame{
    private MyPanel panel = new MyPanel();

    public E4(){
        setTitle("drawLine 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(200,170);
        setVisible(true);
    }

    class MyPanel extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.drawLine(20,20,100,100);
            //g.drawOval(20,20,80,80); //원
            //g.drawRect(20,20,80,80); //사각형
            //g.drawRoundRect(20,20,120,80,40,60); //둥근모서리다각형
        }
        
    }
    public static void main(String[] args) {
        new E4();
    }
}
