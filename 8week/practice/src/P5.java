import java.io.*;
import java.util.*;

public class P5 {
    public static boolean compareFile(FileInputStream src, FileInputStream dest) throws IOException {
        byte[] srcbuf = new byte[1024];
        byte[] destbuf = new byte[1024];

        int srccount = 0;
        int destcount = 0;

        while (true) { 
            srccount = src.read(srcbuf, 0, srcbuf.length);
            destcount = dest.read(destbuf, 0, destbuf.length);
            
            if(srccount != destcount){
                return false;//길이가 다름 >> 다른 파일임
            }

            if(srccount == -1) break; //파일의 끝
            for(int i=0;i<srccount;i++){
                if(srcbuf[i] != destbuf[i]) return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        FileInputStream src = null;
        FileInputStream dest = null;

        System.out.println("전체 경로명이  아닌 파일 이름만 입력하는 경우, 파일을 프로젝트 폴더에 있어야 합니다.");
            
        System.out.print("첫번째 파일 이름을 입력하세요>>");
        String name1 = scanner.nextLine();//sul.jpg
            
        System.out.print("두번째 파일 이름을 입력하세요>>");
        String name2 = scanner.nextLine();//img.jpg
        

        System.out.println(name1+"와 "+name2+"를 비교합니다.");
        //파일이 같은지 확인하기 위해 조건은?
        //각 바이트가 같은지 확인하면 된다!
        try {
            src = new FileInputStream(name1);
            dest = new FileInputStream(name2);
            if(compareFile(src,dest)){
                System.out.println("파일이 같습니다.");
            }
            else{
                System.out.println("파일이 다릅니다.");
            }
            if(src != null) src.close();
            if(dest != null) dest.close();
        } catch (IOException e) {
            System.out.println("파일 출력 오류 "+e.getMessage());
        }
        scanner.close();
    }
}
