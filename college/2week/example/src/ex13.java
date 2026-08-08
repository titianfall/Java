import java.util.Scanner;
public class ex13 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("점수 : ");
        int score = scanner.nextInt();
        System.out.println("학년 : ");
        int year = scanner.nextInt();

        if(score>=60){
            if(year!=4){
                System.out.println("합격");
            }
            else if(score>=70){
                System.out.println("합격");
            }
            else{
                System.out.println("불합격");
            }
        }
        else{
            System.out.println("불합격");
        }
        scanner.close();
    }
}
