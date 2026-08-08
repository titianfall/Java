import java.io.*;
public class E10 {
    public static void main(String[] args) {
        File src = new File("c:\\Temp\\sul.jpg");
        File dest = new File("c:\\Temp\\copysul.jpg");

        long start = System.currentTimeMillis();//현재 시간
        int c;
        try {
            FileInputStream fi = new FileInputStream(src);
            FileOutputStream fo = new FileOutputStream(dest);

            while((c=fi.read())!=-1){
                fo.write((byte)c); //바이트 단위 주의
            }
            fi.close();
            fo.close();

            long end = System.currentTimeMillis();//끝난 시간
            double seconds = (end-start)/1000.0;//초단위 복사 걸린시간
            System.out.println(src.getPath()+" to "+dest.getPath()+"복사 완료");
            System.out.println("걸린 시간 : "+seconds);
        } catch (IOException e) {
            System.out.println("경로 확인 필요");
        }
    }
}
