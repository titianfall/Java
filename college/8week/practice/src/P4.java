import java.io.*;
import java.util.*;

public class P4 {
    public static void main(String[] args) {
        try{
            File f = new File("c:\\windows\\system.ini");
            System.out.println(f.getPath()+" 파일을 읽어 출력합니다.");
            Scanner scanner = new Scanner(new FileReader(f));
            int n=1;
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                System.out.println("\t"+(n++)+": "+line);
            }
            //f.close(); >> 필요없음
            // 실제로 파일을 읽고 쓰는 클래스가 아니다!
            scanner.close();
        } catch(IOException e){
            System.out.println("파일 읽기 오류 : "+e.getMessage());
            e.getStackTrace();
        }
    }
}
