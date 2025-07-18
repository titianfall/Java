import java.io.*;
import java.util.*;
public class E4 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        FileWriter fout = null;
        int c;
        try {
            fout = new FileWriter("c:\\Temp\\test.txt");

            while (true) { 
                String line = scanner.nextLine();//이때 line 에는 \n이 들어가지 않음
                //그냥 엔터키를 누르면 종료하도록
                if(line.length()==0){
                    break;
                }   
                fout.write(line,0,line.length()); //fout.write(line) 동일
                fout.write("\r\n",0,2); //fout.write("\r\n") 동일
            }
            fout.close();
        } catch (FileNotFoundException e) {
            System.out.println("파일 참조 에러");
        } catch (IOException e){
            System.out.println("입출력 스트림 에러");
        }
        scanner.close();
    }
}
