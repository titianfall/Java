import java.util.*;

public class E6 {
    public static void main(String[] args) {
        var scoreMap = new HashMap<String, Integer>();
        var scanner = new Scanner(System.in);
        
        scoreMap.put("박지호", 100);
        scoreMap.put("하이솝", 95);

        System.out.print("요소 개수 : "+scoreMap.size());

        Set<String> key = scoreMap.keySet();
        var it = key.iterator();

        while(it.hasNext()){
            String name = it.next();
            Integer s = scoreMap.get(name);//NullPointerException 예외 발생가능
            int score=0;
            if(s != null){
                score = scoreMap.get(name);
            }
            System.out.println(name+"의 점수는 "+score);
        }
        scanner.close();
    }
}
