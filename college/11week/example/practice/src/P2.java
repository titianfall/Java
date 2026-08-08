import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class P2 extends JFrame{
    //private Vector<String> v = new Vector<String>();
    public P2(){
        super("콤보박스 실습문제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());


        JComboBox<String> strcombo = new JComboBox<String>();
        //빈 콤보박스 생성

        JTextField tf = new JTextField(10);

        c.add(tf);
        c.add(strcombo);

        tf.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JTextField text = (JTextField)e.getSource();
                strcombo.addItem(text.getText());
                text.setText("");
            }
        });
        setSize(300,200);
        setVisible(true);
    }
    public static void main(String [] args){
        new P2();
    }
}
