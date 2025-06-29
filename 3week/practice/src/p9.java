

public class p9 {
    public static void main(String[] args) {
		int[][] arr = new int[4][4];
		System.out.println("4x4 배열에 랜덤한 값을 저장한 후 출력합니다.");

		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				arr[i][j] = (int)(Math.random()*256);
				System.out.print(arr[i][j] + "\t");
			}
			System.out.println();
		}

    }
}
