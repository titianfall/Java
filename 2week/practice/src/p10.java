import  java.util.Scanner;

public class p10 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("점 (x1, y1), (x2, y2)의 좌표 입력>>");
        int x1 = scanner.nextInt();
        int y1 = scanner.nextInt();
        int x2 = scanner.nextInt();
        int y2= scanner.nextInt();

        boolean in1 = (x1 >= 10 && x1 <= 200) && (y1 >= 10 && y1 <= 300);
        boolean in2 = (x2 >= 10 && x2 <= 200) && (y2 >= 10 && y2 <= 300);

        if (in1 && in2) {
            System.out.println("(" + x1 + "," + y1 + ") (" + x2 + "," + y2 + ")은 사각형 안에 있습니다.");
        } else {
            System.out.println("(" + x1 + "," + y1 + ") (" + x2 + "," + y2 + ")은 사각형 안에 없습니다.");
        }
    }
}
