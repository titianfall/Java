import java.util.Scanner;

public class p10 {
    public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int[][] arr = new int[4][4];
		System.out.println("4x4 배열에 랜덤한 값을 저장한 후 출력합니다.");

		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[i].length; j++) {
				arr[i][j] = (int)(Math.random()*256);
				System.out.print(arr[i][j] + "\t");
			}
			System.out.println();
		}
		
		System.out.print("임계값 입력>>");
		int value = scanner.nextInt();
		
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[i].length; j++) {
				if(arr[i][j] > value) {
					arr[i][j] = 255;
				}
				else {
					arr[i][j] = 0;
				}
				System.out.print(arr[i][j] + "\t");
			}
			System.out.println();
		}
		
		scanner.close();
	}
}
