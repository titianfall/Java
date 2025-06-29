import java.util.Scanner;

public class p8_2 {
    public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.print("연산 입력>>");
		double x = s.nextDouble();
		String op = s.next();
		double y = s.nextDouble();
		double result;
		
		switch(op) {
			case "더하기" :
				result = x+y;
				break;
			case "빼기" :
				result = x-y;
				break;
			case "곱하기" :
				result = x*y;
				break;
			case "나누기" :
				result = x/y;
				break;
			default:
				System.out.println("사칙연산이 아닙니다.");
				s.close();
				return;
		}
		
		System.out.println(x + " " + op + " " + y + "의 계산 결과는 " + result);
		s.close();
	}
}
