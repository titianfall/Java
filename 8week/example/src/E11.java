import java.io.*;
public class E11 {
    public static void main(String[] args) {
        File src = new File("c:\\Temp\\sul.jpg"); //32 kb
        File dest = new File("c:\\Temp\\copysul.jpg");

        long start = System.currentTimeMillis();//현재 시간
        int c;
        try {
            FileInputStream fi = new FileInputStream(src);
            FileOutputStream fo = new FileOutputStream(dest);

            byte [] buf = new byte[1024*10]; //한번에 10kb
            while((c=fi.read())!=-1){
                int n = fi.read(buf);
                fo.write(buf, 0 ,n);
                if(n<buf.length){
                    break;//모두 읽었다.
                }
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
