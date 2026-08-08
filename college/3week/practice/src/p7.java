public class p7 {
    public static void main(String[] args) {
		System.out.print("랜덤한 정수들...");
		
		int[] array = new int[10];
		int sum = 0;
		double avg = 0;
		
		for(int i=0;i<10;i++) {
			array[i] = (int)(Math.random()*9)+11;
			System.out.print(array[i]+ " ");
			sum+= array[i];
		}
		avg = (double)sum/array.length;
		System.out.println();
		System.out.print("평균은 " + avg);
	}
}
