import javax.swing.*;

public class E1 extends JFrame{
    public E1(){
        //JFrame의 멤버를 활용
        setTitle("300x300 사이즈의 스윙 프레임 만들기");
        setSize(300,300); //디폴트는 0x0(생성시 기본값이 0) 
        setVisible(true); //디폴트 속성은 invisible
    }
    public static void main(String[] args) {
        E1 frame = new E1();
    }
}
