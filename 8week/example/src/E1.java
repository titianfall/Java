import java.io.*;
public class E1 {
    public static void main(String[] args){
        FileReader fin = null;
        //= new FileReader("c:\\test.txt");
        try {
            fin = new FileReader("c:\\windows\\system.ini");
            int c;
            while((c=fin.read())!=-1){
                System.out.print((char)c);
            }
            fin.close();
        }
        catch (FileNotFoundException e) { //IOException을 상속받지만 
            System.out.println("파일이 없습니다.");
        }
        catch (IOException e){
            System.out.println("입출력 관련 오류입니다.");
        }
    }
}
