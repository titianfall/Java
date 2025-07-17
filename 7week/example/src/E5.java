import java.util.*;
public class E5 {
    public static void main(String[] args) {
        HashMap<String, String> dic = new HashMap<String, String>();
        //var dic = 

        dic.put("baby", "아기");
        dic.put("love", "사랑");
        dic.put("apple", "사과");

        var scanner = new Scanner(System.in);
        //Scanner scanner = 
        while (true) { 
            System.out.print("찾고 싶은 단어는?");
            String key = scanner.nextLine();
            if(key.equals("exit")){
                System.out.println("종료합니다...");
                break;
            }
            String value = dic.get(key);
            if(value == null){
                System.out.println(key+"는 없는 단어입니다.");
            }
            else{
                System.out.println(value);
            }
        }
        scanner.close();
    }
}
