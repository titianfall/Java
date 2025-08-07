import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class P3 extends JFrame{
    MyLabel label = new MyLabel();

    public P3(){
        super("이미지 레이블 드래깅 연습");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(label);
        setSize(300,300);
        setVisible(true);
    }

    class MyLabel extends JLabel{
        ImageIcon icon = new ImageIcon("sul3.jpg");
        Image img = icon.getImage();
        private Point currentImage = new Point(50,50);
        public MyLabel(){
            addMouseMotionListener(new MouseMotionAdapter(){
                @Override
                public void mouseDragged(MouseEvent e){
                    currentImage = e.getPoint();
                    repaint();
                }
            });
        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(img, currentImage.x, currentImage.y, this);
        } 
    }
    public static void main(String[] args) {
        new P3();
    }
}
