import java.awt.*;
import javax.swing.*;

public class E1 extends JFrame{
    private MyPanel panel = new MyPanel();

    public E1(){
        setTitle("JPaenl 의 paintComponent() 예제입니다.");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(250,220);
        setVisible(true);
    }
    class MyPanel extends JPanel{
        @Override
        public void paintComponent(Graphics g){//java.awt.Graphics
            super.paintComponent(g);
            g.setColor(Color.BLUE);
            g.drawRect(10,10,50,50);
            g.drawRect(50,50,50,50);
            g.setColor(Color.MAGENTA);
            g.drawRect(90,90,50,50);
        }
    }
    public static void main(String[] args) {
        new E1();
    }
}
