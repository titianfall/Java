import java.util.Scanner;

public class p1 {
    public static void main(String[] args){
        System.out.print("$1=1400원입니다. 달러를 입력하세요>>");

        Scanner scanner = new Scanner(System.in);
        int dollor = scanner.nextInt();
        int won = dollor*1400;

        System.out.printf("$%d는 %d원 입니다.",dollor, won);
        scanner.close();
    }
}
