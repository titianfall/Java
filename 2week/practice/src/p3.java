import java.util.Scanner;

public class p3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int total=0;
        System.out.println("**********분식집입니다***********");
        System.out.print("떡볶이 몇인분>>");
        int duk = scanner.nextInt();
        total += 2000 * duk;
        System.out.print("김말이 몇인분>>");
        int gim = scanner.nextInt();
        total += 1000 * gim;
        System.out.print("쫄면 몇 인분>>");
        int jjol = scanner.nextInt();
        total += 3000 * jjol;

        System.out.println("전체 금액은 " +total+"입니다.");
    }
}
