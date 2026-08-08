
import java.util.*;

public class P3 {
    public static void main(String[] args) {
        HashMap<String, Integer> map= new HashMap<String, Integer>(); 
        Scanner scanner = new Scanner(System.in);

        System.out.println("주식 종목과 주가를 입력하세요");
        while (true) { 
            System.out.print("종목, 주가>>");
            String stock = scanner.next();//주식
            if(stock.equals("그만")) break;
            Integer stockPrice = scanner.nextInt();//주가
            
            map.put(stock, stockPrice);
        }
        System.out.println("주가를 검색합니다.");
        while (true) { 
            System.out.print("종목>>");
            String select = scanner.next();
            if(select.equals("그만"))break;
            int cost = map.get(select); //바로 프린트문에 넣으면 null값이 나올수있음에 유의 때로는 예외처리 필요
            System.out.println(cost);
        }
    }
}
