import java.util.*;

public class E3 {
    public static void main(String[] args) {
        var a = new ArrayList<String>();
        //Java 10 이후부터
        //ArrayList<String> = new ArrayList<String>();

        Scanner scanner = new Scanner(System.in);

        for(int i=0;i<4;i++){
            System.out.println("이름입력");
            String name = scanner.next();
            a.add(name);
        }
        for(int i=0;i<a.size();i++){
            String n = a.get(i);
            System.out.println(n);
        }
        int max = a.get(0).length();
        //이런식으로 연쇄 호출이 가능
        int index=0;
        for(int i =1;i<a.size();i++){
            if(a.get(i).length()>max){
                max = a.get(i).length();
                index= i;
            }
        }
        System.out.println("가장 긴 이름 "+a.get(index));
        scanner.close();
    }    
}
