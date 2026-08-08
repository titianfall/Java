import java.awt.*;
import javax.swing.*;
public class E2 extends JFrame{
    public E2(){
        setTitle("ContentPane 과 JFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setBackground(Color.yellow);
        contentPane.setLayout(new FlowLayout());

        contentPane.add(new JButton("OK"));
        contentPane.add(new JButton("Cancel"));
        contentPane.add(new JButton("Ignore"));
        
        setSize(300,300);
        setVisible(true);
    }
    public static void main(String[] args) {
        new E2();//contentpane
    }
}
