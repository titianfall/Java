import java.util.*;
//반복자 iterator 학습
public class E4 {
    public static void main(String[] args) {
        //Vector<Integer> v
        var v = new Vector<Integer>();
        v.add(5);
        v.add(4);
        v.add(-1);
        v.add(2,100);
        //Iterator<Integer> it
        var it = v.iterator();

        while(it.hasNext()){
            int n = it.next();
            System.out.println(n);
        }

        int sum=0;
        it = v.iterator();//다시 설정 다시 초기로 돌아감
        while(it.hasNext()){ //반복자를 사용하기 위한 기본적인 무한루프 조건
            int n = it.next();
            sum+=n;
        }
        System.out.println(sum);
    }    
}
