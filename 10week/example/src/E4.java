import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class E4 extends JFrame{
    private JLabel la = new JLabel("Hello");

    public E4(){
        setTitle("마우스로 문자열 이동시키기 - 마우스 이벤트 연습");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.addMouseListener(new MyMouseListener());

        c.setLayout(null);
        la.setSize(50,20);
        la.setLocation(30,20);
        c.add(la);

        setSize(250,250);
        setVisible(true);
    }
    class MyMouseListener implements MouseListener{
        public void mousePressed(MouseEvent e){
            int x = e.getX();
            int y = e.getY();
            la.setLocation(x, y);
        }
        //추상클래스이므로 사용하지는 않아도 모두 구현은 해주어야 하는 불편함이 존해한다.
        public void mouseReleased(MouseEvent e){}
        public void mouseClicked(MouseEvent e){}
        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
    }
    public static void main(String[] args) {
        new E4();
    }
}
