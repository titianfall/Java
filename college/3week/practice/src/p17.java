import java.util.InputMismatchException;
import java.util.Scanner;

public class p17 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
		String coffee[] = {"핫아메리카노", "아이스아메리카노", "카푸치노", "라떼"};
		int price[] = {3000, 3500, 4000, 5000};
		System.out.println("핫아메리카노, 아이스아메리카노, 카푸치노, 라떼 있습니다.");
        while (true) { //메뉴 개수
            System.out.print("주문>>");
            String menu= scanner.next();
            if(menu.equals("그만")){
                    break;
            }
            try{
                int count = scanner.nextInt();
                for(int i=0;i<coffee.length;i++){
                    if(coffee[i].equals(menu)){
                        System.out.println("가격은 "+count*price[i]+"원 입니다.");
                        break;
                    }
                    else if(i == coffee.length -1 ){
                        System.out.println(menu+"는 없는 메뉴입니다.");
                    }
                }
            }
            catch(InputMismatchException e){
                System.out.println("잔 수는 양의 정수로 입력해주세요!");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}
