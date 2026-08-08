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

class SortedArray extends BaseArray{
    
    public SortedArray(int size){
        super(size);
    }
    public void bubbleSort(){
        for(int i=0;i<nextIndex; i++){
            for(int j=i+1;j<nextIndex;j++){
                int temp;
                if(array[j]<array[i]){
                    temp = array[i];
                    array[i]= array[j];
                    array[j] = temp;
                }
            }
        }
    }
}

public class P10 {
    public static void main(String[] args) {
		SortedArray sArray = new SortedArray(10);
		Scanner scanner = new Scanner(System.in);
		System.out.print(">>");
		for(int i=0; i<sArray.length(); i++) { // sArray.length()=10
			int n = scanner.nextInt();
			sArray.add(n);
		}
        sArray.bubbleSort();
		sArray.print();
	    scanner.close();
	}
}
