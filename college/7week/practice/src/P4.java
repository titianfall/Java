import  java.util.*;
public class P4 {
    Scanner scanner = new Scanner(System.in);
    HashMap<String, Integer> list = new HashMap<String, Integer>();
    public void fill(){
        System.out.println("쇼핑 비용을 계산해드립니다. 구입 가능 물건과 가격은 다음과 같습니다.");
        list.put("고추장",3000);
        list.put("만두",500);
        list.put("새우깡",1500);
        list.put("콜라",600);
        list.put("참치캔",2000);
        list.put("치약",1000);
        list.put("연어",2500);
        list.put("삼겹살",2500);
    }
    public void show(){
        Set<String> keys = list.keySet();
        var it = keys.iterator();
        while(it.hasNext()){
            String key = it.next();
            int price = list.get(key);
            System.out.print("[" + key + "," + price + "]");
        }
        System.out.println();
    }
    public void shopping(){
        int sum=0;
        while(true){
            System.out.print("물건과 개수를 입력하세요>>");
            String items = scanner.nextLine();
            if(items.equals("그만"))break;
            StringTokenizer token = new StringTokenizer(items);
            if(token.countTokens()%2!=0){
                System.out.println("입력에 문제가 있습니다! 개수를 올바르게 입력해주십시오");
                continue;
            }
            boolean valid = false;
            while(token.hasMoreTokens()){
                String food = token.nextToken().trim();
                int count = Integer.parseInt(token.nextToken());
                Integer price = list.get(food);
                if(price==null){
                    System.out.println(food+"는 없는 상품입니다!");
                    valid = true;
                    continue;
                }
                sum+=price*count;
                //System.out.println("전체 비용은 "+sum);
                
            }
            if(valid == false){
                System.out.println("전체 비용은 "+sum);
            }
            
        }
    }
    public static void main(String[] args) {
        P4 sp = new P4();
        sp.fill();
        sp.show();
        sp.shopping();
    }
}
