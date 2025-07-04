import java.util.Scanner;

abstract class Calc {
	public String errorMsg;
	protected int a, b;
	public void setValue(int a, int b) {
		this.a = a;
		this.b = b;
	}
	public abstract int calculate();
}

// 덧셈
class Add extends Calc {
	@Override
	public int calculate() {
		errorMsg = null;
		return a+b; 
	}
}

// 뺄셈
class Sub extends Calc {
	@Override
	public int calculate() {
		errorMsg = null;
		return a-b; 
	}
}

// 곱셈
class Mul extends Calc {
	@Override
	public int calculate() {
		errorMsg = null;
		return a*b;
	}
}

// 나눗셈
class Div extends Calc {
	@Override
	public int calculate() {
		if(b == 0) {
			errorMsg = "0으로 나눌 수 없음.";
			return 0;
		}
		else {
			errorMsg = null;			
			return a/b;
		}
	}
}

class Calculator{
    public Calculator() { }
	public void run() {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("두 정수와 연산자를 입력하시오>>");
			int a = scanner.nextInt();
			int b = scanner.nextInt();
			String operator = scanner.next();
			Calc op;
			switch (operator) {
				case "+": op = new Add(); break;
				case "-": op = new Sub(); break; 
				case "*": op = new Mul(); break;
				case "/": op = new Div(); break;
				default:
					System.out.println("잘못된 연산자입니다.");
					scanner.close();
					return;
			}
			op.setValue(a, b);
			int res = op.calculate();
			if(op.errorMsg == null) {
				 System.out.println("계산 결과 " + res);				
			}
			else {
				 System.out.print(op.errorMsg);
				 System.out.println(" 프로그램 종료");
				 break;
			}
		}
		scanner.close();
	}
}

public class P13 {
    public static void main(String[] args) {
        Calculator mycal = new Calculator();
	    mycal.run();
    }
}
