import java.io.*;
import java.util.Scanner;
public class E7 {
    public static void main(String[] args) {
        FileReader fin = null;
        int c;
        try {
            fin = new FileReader("c:\\Temp\\test2.txt");
            BufferedOutputStream out = new BufferedOutputStream(System.out,5);
            while((c=fin.read())!=-1){
                out.write((char)c);
            }
            new Scanner(System.in).nextLine();//엔터키 입력
            out.flush();//버퍼는 꽉차지 않으면 출력되지 않아서 강제출력
            fin.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
