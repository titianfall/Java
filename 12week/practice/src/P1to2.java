import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class P1to2 extends JFrame {
    private int n = 10;
    private JButton btn;

    public P1to2() {
        super("이미지 그리기 연습");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyPanel panel = new MyPanel();
        setContentPane(panel);
        setSize(300, 300);
        setVisible(true);

    }

    class MyPanel extends JPanel {
        private ImageIcon icon = new ImageIcon("sul3.jpg");
        private Image img = icon.getImage();
        private boolean showflag = true;
        public MyPanel() {
            setLayout(new FlowLayout());

            btn = new JButton("Hide/Show");
            add(btn);

            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    n += 1; // +1 증가
                    // 홀수면 그림 숨기기, 짝수면 그림 보이기 
                    // 플래그를 세우고 paintComponent에서 플래그 확인 및 출력제어
                    if (n % 2 == 1) {
                        showflag = false;
                    } else {
                        showflag = true;   
                    }
                    repaint();
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(showflag){
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        new P1to2();
    }
}
