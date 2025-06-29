import java.util.Scanner;

public class p8 {
    public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("정수 몇 개 저장하시겠습니까>>");
		int n = scanner.nextInt();
		int[] arr = new int[n];
		int sum = 0;
		double avg = 0;
		
		System.out.print("랜덤한 정수들...");
		
		for(int i=0; i<n; i++) {
			arr[i]=(int)(Math.random()*100) + 1;
			System.out.print(arr[i]+ " ");
			sum += arr[i];
		}
		
		avg = (double)sum/n;
		System.out.println();
		System.out.println("평균은 " + avg);
		scanner.close();
	}
}
