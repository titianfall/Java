import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class E1 extends JFrame{
    public E1(){
    //setTitle("")
    super("JComponent의 공통된 특성");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container c = getContentPane();
    c.setLayout(new FlowLayout());

    JButton b1 = new JButton("Magenta/Yellow Button");
    JButton b2 = new JButton("  Disabled Button    ");
    JButton b3 = new JButton("getX(), getY()");

    b1.setBackground(Color.YELLOW);//배경색
    b1.setForeground(Color.MAGENTA);//글자색
    b1.setFont(new Font("Arial", Font.ITALIC, 20));//버튼 Arial 체 20픽셀 폰트 고정
    
    b2.setEnabled(false); //불가능한 버튼

    //클릭하면 타이틀바에 좌표 출력버튼
    b3.addActionListener(new ActionListener(){ //클래스 작성과 인스턴스 생성을 동시에
        public void actionPerformed(ActionEvent e){
            JButton b = (JButton)e.getSource(); //버튼 객체 알아냄
            E1 frame = (E1)b.getTopLevelAncestor();//최상위 스윙 프레임 알아내기
            frame.setTitle(b.getX()+ ", " +b.getY());
        }
    });

    c.add(b1); c.add(b2); c.add(b3);

    //버튼을 삭제하는법
    c.remove(b2);
    c.revalidate();//컨테이너 재배치
    c.repaint(); //컴포넌트 다시 그리기 
    //b2가 정상적으로 삭제된 걸 볼 수 있다.
    setSize(260,200);
    setVisible(true);

    }
    public static void main(String[] args) {
        new E1();
    }
}