import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class P1 extends JFrame{
    
    private JCheckBox[] boxes = new JCheckBox[2];
    private String [] state = {"버튼 비활성화", "버튼 감추기"};
    private JButton btn = new JButton("test button");
    public P1(){
        super("체크박스와 버튼 생성 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        MyItemListener listener = new MyItemListener();
        for(int i=0; i<boxes.length;i++){
            boxes[i] = new JCheckBox(state[i]);
            boxes[i].addItemListener(listener); 
            c.add(boxes[i]);
        }
        c.add(btn);
        c.setVisible(true);
        c.setEnabled(true);
        setVisible(true);
        setSize(300,200);
    }
    class MyItemListener implements ItemListener{
        public void itemStateChanged(ItemEvent e){
            
            if(e.getStateChange() == ItemEvent.SELECTED){
                if(e.getItem() == boxes[1]){
                    btn.setVisible(false);
                }
                if(e.getItem() == boxes[0]){
                    btn.setEnabled(false);
                }
            }
            else{
                if(e.getItem()==boxes[1]){
                    btn.setVisible(true);
                }
                if(e.getItem()==boxes[0]){
                    btn.setEnabled(true);
                }
            }
        }
    }
    public static void main(String[] args){
        new P1();
    }
}