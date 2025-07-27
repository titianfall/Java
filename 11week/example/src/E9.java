import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class E9 extends JFrame{
    private JTextField tf = new JTextField(20);
    private JTextArea ta = new JTextArea(7,20);

    public E9(){
        super("텍스트 영역 만들기 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        c.add(new JLabel("입력 후 <ENTER>키를 입력해주세요"));
        c.add(tf);
        c.add(new JScrollPane(ta));

        tf.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JTextField t = (JTextField)e.getSource();
                ta.append(t.getText() +"\n");

                t.setText("");
            }
        });

        setSize(300,300);
        setVisible(true);
    }
    public static void main(String[] args) {
        new E9();
    }
}
