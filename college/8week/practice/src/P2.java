import java.io.*;
import java.util.*;
public class P2 {
    public static void main(String[] args) {
         System.out.println("c:\\Temp\\phone.txt를 출력합니다.");

        Scanner scanner = new Scanner(System.in);
        try{
            FileReader fr = new FileReader("c:\\Temp\\phone.txt");

            int c;
            while((c =fr.read())!=-1){
                System.out.print((char)c);
            }
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다. 디렉토리를 확인하세요.");
        } catch (IOException e) {
            System.out.println("입출력 스트림 에러");
        }
        scanner.close();
    }
}
