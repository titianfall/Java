import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class E7 extends JFrame{
    private JLabel la = new JLabel();

    public E7(){
        setTitle("keyCode 예제 f1 초록 / %키 노랑");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.addKeyListener(new MykeyListener());

        c.add(la);
        setSize(300,150);
        setVisible(true);

        c.setFocusable(true);
        c.requestFocus();
    }

    class MykeyListener extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            Container contentPane = (Container)e.getSource();
            la.setText(KeyEvent.getKeyText(e.getKeyCode())+"키가 입력되었음");

            if(e.getKeyChar() == '%'){
                contentPane.setBackground(Color.YELLOW);
            }
            else if(e.getKeyCode() == KeyEvent.VK_F1){
                contentPane.setBackground(Color.GREEN);
            }
        }
    }
    public static void main(String[] args) {
        new E7();
    }
}
