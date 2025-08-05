import java.awt.*;
import javax.swing.*;
public class E8 extends JFrame{
    private MyPanel panel = new MyPanel();

    public E8(){
        setTitle("이미지 일부분을 크기 조절하여 그리기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);

        setSize(300,400);
        setVisible(true);
    }
    class MyPanel extends JPanel{
        private ImageIcon icon = new ImageIcon("sul3.jpg");
        private Image img = icon.getImage();
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(img,20,20,250,100,100,50,200,200,this);
        }
    }
    public static void main(String[] args) {
        new E8();
    }
}
