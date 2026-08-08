import java.awt.*;
import javax.swing.*;
public class E9 extends JFrame{
    private MyPanel panel = new MyPanel();

    public E9(){
        setTitle("클리핑 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(300,400);
        setVisible(true);    
    }

    class MyPanel extends JPanel{
        ImageIcon icon = new ImageIcon("sul3.jpg");
        Image img = icon.getImage();
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setClip(100,20,150,150);

            g.drawImage(img, 0, 0, getWidth(), getHeight(),this);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("고딕체",Font.ITALIC,40));
            g.drawString("         이쁘다!!!",10,150);

        }
    }
    public static void main(String[] args) {
        new E9();
    }
}
