import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class P1 extends JFrame{
    public P1(){
        super("MouseListener 작성");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        JLabel la = new JLabel("Love Java");
        c.add(la);

        la.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                JLabel la1 = (JLabel)e.getSource();
                la1.setText("Love Java");
            }
            public void mouseExited(MouseEvent e){
                JLabel la2 = (JLabel)e.getSource();
                la2.setText("사랑해 자바");
            }
        });

        setSize(250,150);
        setVisible(true);
    }
    public static void main(String[] args) {
        new P1();
    }
}
