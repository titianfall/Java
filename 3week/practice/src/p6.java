import java.util.Scanner;

public class p6 {
    public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("양의 정수 10개 입력>>");
		int []n = new int[10];
		
		for(int i=0; i<10; i++) {
			n[i]=scanner.nextInt();
		}
		
		System.out.print("자리수의 합이 9인 것은 ...");
		for(int j=0; j<10; j++) {
			// 최대 3자리 수라고 가정
			if((n[j]/100 + n[j]%100/10 + n[j]%10 )== 9) {
				System.out.print(n[j] + " ");
			}
		}
		scanner.close();
	}
}
