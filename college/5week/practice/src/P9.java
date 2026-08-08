import java.util.Scanner;

class BaseArray {
	protected int array []; // 배열 메모리
	protected int nextIndex = 0; // 다음에 삽입할 배열에 대한 인덱스
	public BaseArray(int size) {
		array = new int [size];
	}
	public int length() { return array.length; }
	public void add(int n) { // 정수 n을 배열 끝에 추가
		if(nextIndex == array.length) return; // 배열이 꽉찼으면 추가 안 함
        array[nextIndex++] = n;
	}
	public void print() {
		for(int n : array) 	System.out.print(n + " ");
		System.out.println();
	}
}   

class BinaryArray extends BaseArray{
    private int threshold;
    public BinaryArray(int size, int threshold){
        super(size);
        this.threshold = threshold;
    }
    public void thresholdCheck(){
        for(int index = 0; index<nextIndex; index++){
            if(array[index]>threshold) array[index] = 1;
            else array[index] = 0;
        }
    }
}
public class P9 {
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
			int threshold = 50;
			BinaryArray bArray = new BinaryArray(10, threshold);
			System.out.print(">>");
			for(int i=0; i<bArray.length(); i++) {
				int n = scanner.nextInt();
				bArray.add(n);
			}
            bArray.thresholdCheck();
			bArray.print();
			scanner.close();
		}
}
