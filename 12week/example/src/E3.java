import java.awt.*;
import javax.swing.*;

public class E3 extends JFrame{
    private MyPanel panel = new MyPanel();
    public E3(){
        setTitle("Color, Font 사용 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(350, 470);
        setVisible(true);
    }
    class MyPanel extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.BLUE);
            g.drawString("아 놀고싶다",30,30);
            //Font f = new Font("Arial",Font.ITALIC,30);
            //g.setFont(f);
            g.setColor(new Color(255,0,0));
            g.setFont(new Font("Arial",Font.ITALIC,30));
            g.drawString("How much?",30,60);
            g.setColor(new Color(0x0000ff00));
            for(int i=1;i<=5;i++){
                g.setFont(new Font("Jokerman",Font.ITALIC,i*10));
                g.drawString("This much!!",30,60+i*60);
            }
            
        }
    }
    public static void main(String[] args) {
        new E3();
    }
}
