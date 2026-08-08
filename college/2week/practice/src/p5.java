import java.util.Scanner;

public class p5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("학생1>>");
        String name= scanner.next();
        int late = scanner.nextInt();
        int absence = scanner.nextInt();
        int total = late*3 + absence*8;
        System.out.print("학생2>>");
        String name2= scanner.next();
        int late2 = scanner.nextInt();
        int absence2 = scanner.nextInt();
        int total2 = late2*3 + absence2*8;

        System.out.print(name+"의 감점은 "+total+", ");
        System.out.println(name2+"의 감점은 "+total2+", ");
        if(total>total2){
            System.out.println(name2+"의 출석 점수가 더 높음. "+name2+"의 출석 점수는 "+(100-total2));
        }
        else if(total<total2){
            System.out.println(name+"의 출석 점수가 더 높음. "+name+"의 출석 점수는 "+(100-total));
        }
        else{
            System.out.println("점수 동일"+(100-total));
        }
        scanner.close();
    }
}
