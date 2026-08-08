import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class P2 extends JFrame{
    MyPanel panel = new MyPanel();

    public P2(){
        super("이미지 위에 원드래깅 연습 2번");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(250,300);
        setVisible(true);
    }

    class MyPanel extends JPanel{
        ImageIcon icon = new ImageIcon("sul3.jpg");
        Image img = icon.getImage();
        
        private Point currentPoint = new Point(20,20);

        public MyPanel(){
            addMouseMotionListener(new MouseMotionAdapter(){
                @Override
                public void mouseDragged(MouseEvent e){
                    
                    currentPoint = e.getPoint();
                    repaint();
                }
            });
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(img,0,0,this);
            
            g.setColor(Color.GREEN);
            g.fillOval(currentPoint.x, currentPoint.y, 20,20);
            
        }
    }

    public static void main(String[] args) {
        new P2();
    }
}
