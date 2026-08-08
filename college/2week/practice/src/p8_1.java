import java.util.Scanner;

public class p8_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("연산 입력>>");
        double x = scanner.nextDouble();
        String op = scanner.next();
        double y = scanner.nextDouble();
        double res=0;
        if(op.equals("더하기"))
			res = x+y;
		else if(op.equals("빼기"))
			res = x-y;
		else if(op.equals("곱하기"))
			res = x*y;
		else if(op.equals("나누기"))
			res = x/y;
		else {
			System.out.println("사칙연산이 아닙니다.");
			scanner.close();
            System.out.println("scanner.close()실행");
			return;
		}
        System.out.println(x + op + y +"의 계산 결과는 "+ res);
        scanner.close();
        System.out.println("scanner.close()실행");
    }
}
