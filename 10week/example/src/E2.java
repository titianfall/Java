import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class E2 extends JFrame{
    public E2(){
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

    private class MyActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton b = (JButton)e.getSource();
            if(b.getText().equals("Action")){
                b.setText("그만");
            }
            else{
                b.setText("Action");
            }
            //E2의 멤버나 JFrame 의 멤버를 호출할 수 있음
            E2.this.setTitle(b.getText());
            //버튼과 제목이 같이 바뀌도록 설정
        }
    }

    public static void main(String[] args){
        new E2();//IndepClssListener();
    }
}

