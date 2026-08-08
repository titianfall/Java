import java.util.Scanner;

public class ex14 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        char grade;
        System.out.println("성적 : ");
        int score = scanner.nextInt();
        switch(score/10){
            case 10:
            case 9:
                grade = 'A';
                break;
            case 8:
                grade = 'B';
                break;
            case 7:
                grade = 'C';
                break;
            default:
                grade = 'F';
                break;
        }
        System.out.println("학점 "+grade);

        scanner.close();
    }
}
