import java.awt.*;
import javax.swing.*;
public class E10 extends JFrame{
    private String [] fruits= {"apple", "banana", "kiwi", "mango", "pear", 
							"peach", "berry", "strawberry", "blackberry"};
	private ImageIcon [] images = { new ImageIcon("sul1.jpg"),
									new ImageIcon("sul2.jpg"),
									new ImageIcon("sul3.jpg"),
									new ImageIcon("sul4.jpg") };
    public E10(){
        super("리스트 만들기 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        JList<String> strList = new JList<String>(fruits);
        c.add(strList);

        JList<ImageIcon> imgList = new JList<ImageIcon>();
        imgList.setListData(images);
        c.add(imgList);

        JList<String> scrollList = new JList<String>(fruits);
        c.add(new JScrollPane(scrollList));

        setSize(300,300);
        setVisible(true);
    }
    public static void main(String[] args) {
        new E10();
    }
}
