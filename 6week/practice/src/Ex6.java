import java.util.Scanner;

public class Ex6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("문자열을 입력하세요. 빈 칸이 있어도 되고 한글 영어 모두 됩니다.");

        String line = scanner.nextLine();
        for(int i=1; i<line.length();i++){
            //beginIndex 부터 시작하여 리턴
            System.out.print(line.substring(i));
            //beginIndex 부터 endIndex까지 리턴
            System.out.println(line.substring(0,i));
        }
        scanner.close();
    }
}
