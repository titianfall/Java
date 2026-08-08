import java.util.Scanner;

public class p2 {
    public static void main(String[] args) {
        System.out.print("생년월일을 입력 하세요>>");
        Scanner scanner = new Scanner(System.in);

        int birth = scanner.nextInt();
        int year = birth / 10000;
        int month = birth %10000 / 100;
        int day = birth % 100;
        System.out.println(year+"년 "+month+"월 "+day + "일");

        scanner.close();
    }
}
