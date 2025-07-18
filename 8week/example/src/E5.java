import java.io.*;
public class E5 {
    public static void main(String[] args) {
        byte b[] = {7,51,3,4,-1,24};

        try {
            FileOutputStream fout = new FileOutputStream("c:\\Temp\\test.out");
            for(int i=0;i<b.length;i++){
                fout.write(b[i]);
            }
            fout.close();
        } catch (IOException e) {
            System.out.println("경로를 재확인해주세요");
            return;
        }
        System.out.println("저장 완료");
    }
}
