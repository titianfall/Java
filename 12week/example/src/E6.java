import java.awt.*;
import javax.swing.*;
public class E6 extends JFrame{
    private MyPanel panel = new MyPanel();
    public E6(){
        setTitle("원본 크기로 원하는 위치에 이미지 그리기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(250,350);
        setVisible(true);
    }
    class MyPanel extends JPanel{
        private ImageIcon icon = new ImageIcon("sul3.jpg");
        private Image img = icon.getImage(); 
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(img,20,20,this);
        }
    }
    public static void main(String[] args) {
        new E6();
    }
}
