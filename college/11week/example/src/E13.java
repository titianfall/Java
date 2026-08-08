import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class E13 extends JFrame{
    private String[] sulY = {"pajama", "danguk","cat"};
    private ImageIcon[] image = {new ImageIcon("sul2.jpg"), 
                                new ImageIcon("sul3.jpg"),
                                new ImageIcon("sul4.jpg")};
    private JLabel imgLabel = new JLabel(image[0]);
    private JComboBox<String> strcombo = new JComboBox<String>(sulY);
    public E13(){
        super("콤보박스 활용 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        c.add(strcombo);
        c.add(imgLabel);

        strcombo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JComboBox<String> ch = (JComboBox<String>)e.getSource();
                int index = ch.getSelectedIndex();
                imgLabel.setIcon(image[index]);
            }
        });

        setSize(300,250);
        setVisible(true);
    }
    public static void main(String[] args) {
        new E13();
    }
}
