import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class E5 extends JFrame{
    private String[] names={"사과","배","체리"};
    private JCheckBox[] fruits = new JCheckBox [3];

    private JLabel sumLabel;
    public E5(){
        super("체크박스 ItemEvent 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        c.add(new JLabel("사과 100 배 500 체리 20000"));

        MyItemListener listener = new MyItemListener();
        for(int i=0;i<fruits.length;i++){
            fruits[i] = new JCheckBox(names[i]);
            fruits[i].setBorderPainted(true);
            c.add(fruits[i]);
            fruits[i].addItemListener(listener);
            //void itemStateChanged(ItemEvent e) 한개의 메소드로 구성
        }
        sumLabel = new JLabel("현재 0원 입니다.");
        c.add(sumLabel);

        setSize(200,200);
        setVisible(true);
    }

    class MyItemListener implements ItemListener{
        private int sum=0;

        public void itemStateChanged(ItemEvent e){
            if(e.getStateChange() == ItemEvent.SELECTED){
                if(e.getItem() == fruits[0]){
                    sum +=100;
                }
                else if(e.getItem() == fruits[1]){
                    sum +=500;
                }
                else if(e.getItem() == fruits[2]){
                    sum +=20000;
                }
            }
            else{
                if(e.getItem() == fruits[0]){
                    sum -=100;
                }
                else if(e.getItem() == fruits[1]){
                    sum -=500;
                }
                else if(e.getItem() == fruits[2]){
                    sum -=20000;
                }
            }
            sumLabel.setText("현재 "+sum+"원 입니다.");
        }
    }
    public static void main(String[] args) {
        new E5();
    }
}