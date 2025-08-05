import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class P2 extends JFrame{
    public P2(){
        super("드래깅동안 노래지는 swing 프로그램");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        //1. 컨텐트팬의 배경을 초록으로 하고
        c.setBackground(Color.green);
        //2. 마우스를 드래깅하는 동안만 노란색으로 변하도록 하라
        
        c.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                //Component c = (componenet)e.getSource();
                c.setBackground(Color.yellow);
            }
        });
        c.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e){
                c.setBackground(Color.green);
            }
        });
        setSize(300,150);
        setVisible(true);
    }
    public static void main(String[] args) {
        new P2();
    }
}
