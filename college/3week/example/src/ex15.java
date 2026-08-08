import java.util.Scanner;

public class ex15 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dividend;//나뉨수
        int divisor;//나눗수

        while(true){
            System.out.println("나뉨수를 입력하십시오");
            dividend = scanner.nextInt();
            System.out.println("나눗수를 입력하십시오");
            divisor = scanner.nextInt();
            //나눗수로 0이 들어갈 경우에 프로그램이 강제 종료되는 예외 발생
            try {
                System.out.println(dividend+"%"+divisor+" = "+dividend/divisor);
                break;
            } catch (ArithmeticException e) {
                System.out.println("0으로는 나눌수 없습니다. 다시 입력해주세요");
            }
        }
        scanner.close();
    }
}
