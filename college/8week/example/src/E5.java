import java.io.*;
public class E5 {
    public static void main(String[] args) {
        byte b[] = {7,51,3,4,-1,24};
        byte c[] = {7,51,3,4,-1,22};
        try {
            FileOutputStream fout = new FileOutputStream("c:\\Temp\\test.out");
            //test.out 파일은 바이너리 파일로 메모장에서는 볼 수 없다.
            for(int i=0;i<b.length;i++){
                fout.write(b[i]);
            }
            //for문을 사용하지 않고 fout.write(b); 도 가능하다.
            //fout.write(c);
            fout.close();
        } catch (IOException e) {
            System.out.println("경로를 재확인해주세요");
            return;
        }
        System.out.println("저장 완료");
    }
}
