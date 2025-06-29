import java.util.InputMismatchException;
import java.util.Scanner;

public class ex17 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("정수 3개를 입력하세요");
        int sum=0, n=0;
        for(int i=0;i<3;i++){
            System.out.println(i+">>");
            try {
                n = scanner.nextInt();
            } catch (InputMismatchException e) {
                String ex = scanner.nextLine();
                System.out.println(n+"은 정수가 아닙니다. 다시 입력하세요");

                i--;
                continue;
            }
            sum+=n;
        }
        System.out.println("sum = "+sum);
        scanner.close();
    }
}
