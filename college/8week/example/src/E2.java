import java.io.*;
public class E2 {
    public static void main(String[] args){
        InputStreamReader in = null; //바이트 스트림을 전달받고 문자 정보로 변환하는 스트림 객체
        FileInputStream fin = null;//바이트 파일 입력 스트림을 먼저 생성한다.

        try {
            //1. 바이트 파일 입력 스트림을 먼저 생성한다.
            fin = new FileInputStream("c:\\Temp\\hangul.txt");
            //2. fin 이라는 바이트 스트림을 전달받고 문자로 변환하는 스트림 객체 in을 사용한다.
            in = new InputStreamReader(fin, "UTF-8");
            //MS949는 fin 이라는 바이트들을 문자로 인코딩하기 위한 문자 집합을 지정한다.
            int c;
            System.out.println("인코딩 문자 집합은 "+in.getEncoding()); // MS949가 출력된다.
            while((c = in.read())!=-1){
                System.out.print((char)c); //hangul.txt 파일이 출력된다.
            }
            in.close();
            fin.close();
        } catch (FileNotFoundException e) {
            System.out.println("파일 탐색 에러");
        }
        catch(IOException e){
            System.out.println("입출력 스트림 에러");
        }
    }
}
