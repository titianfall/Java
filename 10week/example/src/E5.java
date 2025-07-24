import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class E5 extends JFrame{
    private JLabel la = new JLabel("Hello");

    public E5(){
        setTitle("마우스로 문자열 이동시키기 - 마우스 이벤트 연습");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.addMouseListener(new MyMouseAdapter());

        c.setLayout(null);
        la.setSize(50,20);
        la.setLocation(30,20);
        c.add(la);

        setSize(250,250);
        setVisible(true);
    }
    class MyMouseAdapter extends MouseAdapter{ //확장을 extends 로 해야함
        public void mousePressed(MouseEvent e){
            int x = e.getX();
            int y = e.getY();
            la.setLocation(x, y);
        }
        // 작성하지 않아도됨!
        // public void mouseReleased(MouseEvent e){}
        // public void mouseClicked(MouseEvent e){}
        // public void mouseEntered(MouseEvent e){}
        // public void mouseExited(MouseEvent e){}
    }
    public static void main(String[] args) {
        new E5();
    }
}
