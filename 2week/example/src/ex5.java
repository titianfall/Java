import java.util.Scanner;

public class ex5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("정수를 입력하세요: ");
        int time = scanner.nextInt();
        int second =time%60;
        int minute = (time/60)%60;
        int hour = (time/60)/60;

        System.out.println(time+"초는 ");
        System.out.println(hour+"시간, ");
        System.out.println(minute+"분, ");
        System.out.println(second+"초 입니다.");

        scanner.close();
    }
}
