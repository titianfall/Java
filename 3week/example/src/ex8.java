import  java.util.Scanner;
public class ex8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int intArray[] = new int[5];
        int sum=0;

        System.out.println(intArray.length+"개의 정수를 입력하세요");
        for(int i=0;i<intArray.length;i++){
            intArray[i]=scanner.nextInt();
        }
        for(int i=0;i<intArray.length;i++){
            sum+=intArray[i];
        }
        System.out.println("평균은 "+(double)sum/intArray.length+"입니다.");
        scanner.close();
    }
}
