import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
public class E10 extends JFrame{
    private MyPanel panel = new MyPanel();
    public E10(){
        setTitle("drawing Line by Mouse 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);

        setSize(300,300);
        setVisible(true);
    }
    class MyPanel extends JPanel{
        private Vector<Point> vStart = new Vector<Point>();
        private Vector<Point> vEnd = new Vector<Point>();

        public MyPanel(){
            addMouseListener(new MouseAdapter(){
                public void mousePressed(MouseEvent e){
                    Point StartP = e.getPoint();
                    vStart.add(StartP);
                }
                public void mouseReleased(MouseEvent e){
                    Point EndP = e.getPoint();
                    vEnd.add(EndP);

                    repaint();
                }
            }); 
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.BLUE);

            for(int i=0;i<vStart.size();i++){
                Point s = vStart.elementAt(i);
                Point e = vEnd.elementAt(i);

                g.drawLine((int)s.getX(),(int)s.getY(), (int)e.getX(),(int)e.getY());
            }
        }
    }
    public static void main(String[] args) {
        new E10();
    }
}
