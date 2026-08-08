import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//독립된 클래스로 이벤트 리스너 작성 (크기가 커서)
class MyActionListener implements ActionListener{
    //추상클래스를 구체화 해주어야함
    public void actionPerformed(ActionEvent e){
        JButton b = (JButton)e.getSource();
        if(b.getText().equals("Action")){
            b.setText("액션");
        }
        else{
            b.setText("Action");
        }
    }
}
public class E1 extends JFrame{
    public E1(){
        setTitle("Action 이벤트 리스너 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        JButton btn = new JButton("Action");
        btn.addActionListener(new MyActionListener());
        c.add(btn);

        setSize(350,150);
        setVisible(true);
    }
    public static void main(String[] args){
        new E1();//IndepClssListener();
    }
}