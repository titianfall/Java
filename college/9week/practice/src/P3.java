import java.awt.*;
import javax.swing.*;
public class P3 extends JFrame{
    public P3(){
        setTitle("GridLayout으로 10개의 버튼을 배치한 프레임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();

        c.setLayout(new GridLayout(1,10));
        //c.add(new Button("0"));
        for(int i=0;i<10;i++){
            c.add(new Button(Integer.toString(i)));
        }  
        setSize(300,200);
        setVisible(true);
    }
    public static void main(String[] args) {
        new P3();
    }
}
