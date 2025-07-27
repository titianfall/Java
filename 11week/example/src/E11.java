import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
public class E11 extends JFrame{
    private JTextField tf = new JTextField(10);
    private Vector<String> v = new Vector<String>();
    //java util Vector
    private JList<String> nameList = new JList<String>();

    public E11(){
        super("리스트 변경 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        c.add(new JLabel("이름 입력 후 <Enter>키"));
        c.add(tf);

        v.add("황기태");
        v.add("이재문");

        nameList.setVisibleRowCount(5);
        nameList.setFixedCellWidth(100);
        c.add(new JScrollPane(nameList));

        setSize(300,300);
        setVisible(true);

        tf.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JTextField t = (JTextField)e.getSource();
                v.add(t.getText());
                t.setText("");
                nameList.setListData(v);
            }
        });
    }
    public static void main(String[] args) {
        new E11();
    }
}
