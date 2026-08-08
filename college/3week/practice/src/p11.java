import java.util.Scanner;

public class p11 {
    public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("***** 구구단을 맞추는 퀴즈입니다. *****");
		int x, y, ans, wrongCount = 0;
		
		while(true) {

			
			x = (int)(Math.random()*9) + 1;
			y = (int)(Math.random()*9) + 1;
			System.out.print(x + "x" + y + "=");
			ans = scanner.nextInt();
			
			if(x*y == ans) {
				System.out.println("정답입니다. 잘했습니다.");
			}
			else {
				wrongCount++;
				if(wrongCount == 3) {
					System.out.println(wrongCount + "번 틀렸습니다. 퀴즈 종료합니다.");
					break;
				}
				System.out.println( wrongCount + "번 틀렸습니다. 분발하세요.");
			}
		}
		scanner.close();
	}
}
