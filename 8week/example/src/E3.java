import java.io.*;

public class E3 {
    public static void main(String[] args) {
        InputStreamReader in = null;
        FileInputStream fin = null; 
        
        try {
            
            fin = new FileInputStream("c:\\Temp\\hangul.txt");
            in = new InputStreamReader(fin,"US-ASCII");

            int c;
            System.out.println("인코딩 문자 집합은 "+in.getEncoding());
            while((c = in.read())!=-1){
                System.out.print((char)c);
            }
            in.close();
            fin.close();
        } catch (FileNotFoundException e) {
            System.out.println("파일 참조 에러");
        } catch(IOException e){
            System.out.println("입출력 에러");
        }
    }
}
