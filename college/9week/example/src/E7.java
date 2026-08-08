import java.awt.*;
import javax.swing.*;
public class E7 extends JFrame{
    public E7(){
        //setTitle("여러 컴포넌트를 가진 GUI 만들기");
        super("여러 컴포넌트를 가진 GUI 만들기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        c.add(new NorthPanel(),BorderLayout.NORTH);
        c.add(new CenterPanel(), BorderLayout.CENTER);

        setSize(300,300);
        setVisible(true);
    }

    class NorthPanel extends JPanel{
        public NorthPanel(){
            setBackground(Color.LIGHT_GRAY);
            setLayout(new FlowLayout());
            add(new JButton("Open"));
            add(new JButton("Read"));
            add(new JButton("Close"));
        }
    }
    class CenterPanel extends JPanel{
        private JLabel[] labels = {new JLabel("Hello"), new JLabel("Java"), new JLabel("Love")};
        public CenterPanel(){
            setLayout(null);
            for(int i=0;i<labels.length;i++){
                int x = (int)(Math.random()*200); // 0~199
                int y = (int)(Math.random()*200); // 0~199
                labels[i].setLocation(x, y);
                labels[i].setSize(100,20);
                add(labels[i]);
            }
        }
    }
    public static void main(String[] args) {
        new E7();
    }
}
