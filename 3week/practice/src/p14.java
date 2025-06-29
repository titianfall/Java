import java.util.Scanner;

public class p14 {
    public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("***** 갬블링 게임을 시작합니다. *****");
		
		while(true) {
			System.out.print("엔터키 입력>>");
			scanner.nextLine();
		
			int x = (int)(Math.random()*3);
			int y = (int)(Math.random()*3);
			int z = (int)(Math.random()*3);
			System.out.println(x + " " + y + " " + z);
			
			if(x==y && y==z) {
				System.out.println("성공! 대박났어요!");
				System.out.print("계속하시겠습니까?(yes/no)>>");
				String choose = scanner.nextLine();
				if(choose.equals("no")) {
					System.out.println("게임을 종료합니다.");
					break;
				}
                else if (!choose.equals("yes")) {
                    System.out.println("yes/no 중에 입력해주세요.");
                    //continue;
                }		
			}
		}
		scanner.close();

	}
}
