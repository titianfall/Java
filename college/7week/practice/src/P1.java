import java.util.*;

public class P1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var v = new Vector<Integer>();
        System.out.print("정수 입력(-1 이면 입력 끝)>>");
        int low=999;
        while (true) { 
            int n = scanner.nextInt();
            if(n == -1) break;
            if(low>n) low = n;
            v.add(n);
        }
        System.out.println("제일 작은 수는 "+low);
        low = 999;
        for(int i=0;i<v.size();i++){
            int n = v.get(i);
            if(n<low) low =n;
        }
        System.out.println(low);
    }    
}
