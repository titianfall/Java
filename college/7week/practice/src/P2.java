import java.util.*;

public class P2 {
    private Vector<Integer> v = new Vector<Integer>();
    Scanner scanner = new Scanner(System.in);
    public void read(){
        System.out.print("0이 입력될 때까지 정수 입력>>");
        while (true) { 
            int n = scanner.nextInt();
            if(n == 0) break;
            v.add(n);
        }
    }
    public void changeToZero(){
        for(int i=0;i<v.size();i++){
            int n = v.get(i);
            if(n<0) v.set(i,0);
        }
    }
    public void showAll(){
        for(int i=0;i<v.size();i++){
            System.out.print(v.get(i)+" ");
        }
        System.out.println();
    }
    public int add(){
        int sum=0;
        for(int i=0;i<v.size();i++){
            sum+=v.get(i);
        }
        return sum;
    }
    public static void main(String[] args) {
        P2 sp = new P2();
        sp.read();
        sp.changeToZero();
        System.out.println("음수를 0으로 바꿈");
        sp.showAll();
        System.out.println("양수의 합은 "+sp.add());
    }
}
