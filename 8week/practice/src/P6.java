import java.io.*;
import java.util.*;

public class P6 {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        FileInputStream src = null;
        FileInputStream dest = null;
        FileOutputStream add = null;
        System.out.println("전체 경로명이  아닌 파일 이름만 입력하는 경우, 파일을 프로젝트 폴더에 있어야 합니다.");
            
        System.out.print("첫번째 파일 이름을 입력하세요>>");
        String name1 = scanner.nextLine();//sul.jpg
            
        System.out.print("두번째 파일 이름을 입력하세요>>");
        String name2 = scanner.nextLine();//img.jpg
        

        System.out.println(name1+"와 "+name2+"를 합칩니다.");
        //파일이 같은지 확인하기 위해 조건은?
        //각 바이트가 같은지 확인하면 된다!
        try {
            src = new FileInputStream(name1);
            dest = new FileInputStream(name2);
            add = new FileOutputStream("append.txt");
            
            byte[] buf = new byte[1024];

            while (true) { 
                int n = src.read(buf);
                add.write(buf,0, n);
                if(n < buf.length) break; // 다 읽었음
            }

            while (true) { 
                int n = dest.read(buf);
                add.write(buf, 0, n);
                if(n<buf.length) break;   
            }

            System.out.println("append.txt 저장완료");
            if(src != null) src.close();    
            if(dest != null) dest.close();
        } catch (IOException e) {
            System.out.println("파일 출력 오류 "+e.getMessage());
        }
        scanner.close();

    }
}
