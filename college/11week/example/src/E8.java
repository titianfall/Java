import java.awt.*;
import javax.swing.*;
public class E8 extends JFrame {
    public E8(){
        super("텍스트필드 만들기 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        c.add(new JLabel("이름 "));
        c.add(new JTextField(20));
        c.add(new JLabel("학과 "));
        c.add(new JTextField(20));
        c.add(new JLabel("주소 "));
        c.add(new JTextField(20));

        JTextField tf = new JTextField("안녕",20);
        tf.setEditable(false); //입력 수정 불가
        c.add(tf);
        JTextField tf2 =new JTextField();
        tf2.setText("Hello");
        tf2.setFont(new Font("고딕체",Font.ITALIC,20));
        c.add(tf2);
        setSize(250, 200);
        setVisible(true);
    }
    public static void main(String[] args) {
        new E8();
    }
}
