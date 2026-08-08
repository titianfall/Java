import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class P3 extends JFrame {
    public P3(){
        super("왼쪽 방향키로 한문자씩 회전함");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        JLabel la = new JLabel("Love Java");
        c.add(la);
        
        la.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_LEFT){
                    JLabel label = (JLabel)e.getSource();
                    String text = la.getText();
                    String first = text.substring(0,1);
                    String last = text.substring(1);
                    la.setText(last.concat(first));
                }
            }
        });

        setSize(250,100);
        setVisible(true);

        la.setFocusable(true);
        la.requestFocus();
    }
    public static void main(String[] args) {
        new P3();
    }
}
