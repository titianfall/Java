import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class E6 extends JFrame{
    private JLabel[] keyMessage;

    public E6(){
        setTitle("KeyListener 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.addKeyListener(new MyKeyAdapter());

        keyMessage = new JLabel[3];
        keyMessage[0] = new JLabel(" getKeyCode()  ");
        keyMessage[1] = new JLabel(" getKeyChar()  ");
        keyMessage[2] = new JLabel(" getKeyText()  ");

        for(int i=0;i<3;i++){
            c.add(keyMessage[i]);
            keyMessage[i].setOpaque(true);//배경색이 보이도록 불투명 속성 설정
            keyMessage[i].setBackground(Color.YELLOW);
        }
        setSize(300,150);
        setVisible(true);

        //컨텐츠팬이 키 입력을 받을 수 있도록 포커스 강제 지정
        c.setFocusable(true);
        c.requestFocus();

    }

    class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            int keyCode = e.getKeyCode();
            char keyChar = e.getKeyChar();
            keyMessage[0].setText(Integer.toString(keyCode));
            keyMessage[1].setText(Character.toString(keyChar));
            keyMessage[2].setText(KeyEvent.getKeyText(keyCode));

            System.out.println("KeyPressed");
        }
        public void keyReleased(KeyEvent e){
            System.out.println("KeyReleased" );
        }    
        public void keyTyped(KeyEvent e){
            System.out.println("KeyTyped");
        }
    }
    
    public static void main(String[] args) {
        new E6();        
    }
}
