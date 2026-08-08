import java.util.*;

public class E8 {
    public static void main(String[] args) {
        var map = new HashMap<String, Vector<Integer>>(); 
        var scanner = new Scanner(System.in);

        map.put("김하진", new Vector<Integer>());
        map.put("하여린", new Vector<Integer>());
        map.put("윤단비", new Vector<Integer>());
        map.put("한지윤", new Vector<Integer>());

        System.out.println("등록된 학생: 김하진 하여린 윤단비 한지윤");
        while (true) { 
            System.out.println("이름과 점수들>>");
            String line = scanner.nextLine();
            if(line.equals("exit")) break;
            
            String[] Tokens = line.split(" ");
            String name= Tokens[0];
            Vector<Integer> v = map.get(name);
            if(v == null){
                System.out.println(name+"없는 학생");
                break;
            }
            for(int i=0;i<Tokens.length-1;i++){
                v.add(Integer.parseInt(Tokens[i+1]));
            }
        }
        while(true){
            System.out.println("검색할 이름>>");
            String name = scanner.next();
            if(name.equals("그만")) break;
            Vector<Integer> v = map.get(name);

            if(v == null){
                System.out.println(name+"없는 학생임");
                break;
            }
            if(v.size() == 0){
                System.out.println(name+ "토익점수가 없음");
                break;
            }
            for(int score : v){
                System.out.print(score+" ");

            }
            System.out.println();
        }
        scanner.close();
    }
}
