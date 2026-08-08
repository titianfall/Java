import java.io.*;
import java.util.*;
public class P1 {
    public static void main(String[] args) {
        System.out.println("전화번호 입력 프로그램입니다.");

        Scanner scanner = new Scanner(System.in);
        try{
            FileWriter fw = new FileWriter("c:\\Temp\\phone.txt");
            //지정한 파일이 없을경우 자동으로 새 파일을 만듬 
            while (true) { 
                System.out.print("이름 전화번호 >> ");
                //String line = scanner.nextLine();
                String name= scanner.next();
                if(name.equals("그만")) break;
                //if(line.equals("그만")) break;
                String tel = scanner.next();
                //fw.write(line+"\r\n");
                fw.write(name);
                fw.write(tel);
                //fw.flush();
                fw.write("\r\n");
                //System.out.println("");
                }
                fw.close();
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다. 디렉토리를 확인하세요.");
        } catch (IOException e) {
            System.out.println("입출력 스트림 에러");
            //e.printStackTrace();
        }
        System.out.println("c:\\Temp\\phone.txt에 저장하였습니다.");
        scanner.close();
    }
}
