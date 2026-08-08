import java.io.*;
import java.util.*;
public class P3_2{
    public static void main(String[] args) {
		try {
            File f = new File("c:\\windows\\system.ini");
            Scanner scanner = new Scanner(new FileReader(f));
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                line = line.toUpperCase();
                System.out.println(line);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("파일 읽기 오류");
            e.getStackTrace(); 
            //예외가 발생한 위치를 추적해서 출력해주는 디버깅 도구
        }
	}
}
