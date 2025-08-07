import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class P1 extends JFrame{
    MyPanel panel = new MyPanel();

    public P1(){
        setTitle("문제 1번 2회차풀이");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);

        setSize(250,300);
        setVisible(true);
    }

    class MyPanel extends JPanel{
        private ImageIcon icon = new ImageIcon("sul3.jpg");
        private Image img = icon.getImage();
        int imgw = img.getWidth(this);
        int imgh = img.getHeight(this);
        private JButton btn;
        //private int cnt =0;
        private boolean showflag = true;
        public MyPanel(){
            setLayout(new FlowLayout());
            btn = new JButton("Hide/Show");

            btn.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e){
                    // cnt+=1;
                    // showflag = (cnt % 2 == 0);
                    
                    showflag = !showflag;
                    repaint();
                } 
            });
            add(btn);
        }
    
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            if(showflag){
                g.drawImage(img,0,0,imgw,imgh,this);
            }        
        }
    }

    public static void main(String[] args) {
        new P1();
    }
}
