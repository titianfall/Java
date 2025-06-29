import java.util.Scanner;

public class ex14 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dividend;//나뉨수
        int divisor;//나눗수

        System.out.println("나뉨수를 입력하십시오");
        dividend = scanner.nextInt();
        System.out.println("나눗수를 입력하십시오");
        divisor = scanner.nextInt();

        System.out.println(dividend+"%"+divisor+" = "+dividend/divisor);
        scanner.close();
    }
}
