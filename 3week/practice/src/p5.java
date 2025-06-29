import java.util.Scanner;

public class p5 {
    
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n[] = new int[10];
		System.out.print("양의 정수 10개 입력>>");
		for(int i=0; i<n.length; i++) {
			n[i]=scanner.nextInt();
		}
		System.out.print("3의 배수는...");
		
		for(int i=0; i<n.length; i++) {
			if(n[i] % 3 == 0)
				System.out.print(n[i]+ " ");
		}
		scanner.close();
	}
}
