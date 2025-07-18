import java.io.*;

public class E6 {
    public static void main(String[] args) {
        byte b[]= new byte[6];

        try {
            FileInputStream fin = new FileInputStream("c:\\Temp\\test.out");
            int n=0;
            int c;

            while((c=fin.read())!=-1){
                b[n++]=(byte)c;
            }
            System.out.println("test.out에서 읽은 배열을 출력");
            for(int i=0;i<n;i++){
                System.out.print(b[i]+" ");
            }
            System.out.println();
            fin.close();
        } catch (IOException e) {
            System.out.println("경로 재확인 필요");
        }
    }
}
