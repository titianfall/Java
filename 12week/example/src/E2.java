import java.awt.*;
import javax.swing.*;

public class E2 extends JFrame{
    private MyPanel panel = new MyPanel();

    public E2(){
        setTitle("drawString 사용 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(250,220);
        setVisible(true);
    }

    class MyPanel extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawString("예제가 안끝나요",30,30);
            g.drawString("무한으로 즐겨요..",60,60);
        }
    }
    public static void main(String[] args) {
        new E2();
    }
}
