import java.util.Scanner;

public class p3 {
    public static void main(String[] args) {
        // Scanner를 이용해 정수 입력받기
        Scanner scanner = new Scanner(System.in);
        int num;
        while (true) { 
            System.out.print("정수를 입력하시오>> ");
            num = scanner.nextInt();
            if(num>0){
                break;
            }
        }

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num - i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        scanner.close();
    }
}
