import java.util.Scanner;
public class ex10 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("점수 : ");
        int score = scanner.nextInt();
        if(score >=89){
            System.out.println("축하합니다! 합격입니다.");
        }
        scanner.close();
    }
}
