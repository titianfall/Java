import java.util.Scanner;

public class p16 {
    public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int sum = 0, count = 0;
		System.out.print("양의 정수를 입력하세요. -1은 입력 끝>>");
		while(true) {
			String input = scanner.next();
			
			try {
				int n = Integer.parseInt(input);
				
				if(n == -1) break;
				
				if (n > 0) {
                    sum += n;
                    count++;
                } 
				else {
                    //0과 음수 처리
                    System.out.println(n + " 제외");
                }
				
			} catch (NumberFormatException e) {
				System.out.println(input + " 제외");
                //hello, 3,14 를 치면 여기로 오고 출력됨 
			}
		}
		
        if (count > 0) {
            System.out.println("평균은 " + sum/count);
        } 
        else {
            System.out.println("입력된 양의 정수가 없습니다.");
        }
		scanner.close();
	}

}
