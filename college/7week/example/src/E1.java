import java.util.Vector;

public class E1 {
    public static void main(String[] args){
        Vector<Integer> v= new Vector<Integer>();
        v.add(5); // 자동 박싱 v.add(Integer.valueOf(5)) 이런식으로
        v.add(4);
        v.add(-1);
        //중간에 삽입하기
        v.add(2,100);

        System.out.println("vector size: "+v.size()+" capacity: "+v.capacity());

        int sum=0;
        for(int i=0;i<v.size();i++){
            int n = v.get(i);
            int m = v.elementAt(i);
            sum+=m;
            System.out.println(n);
        }

        System.out.println("총합  = "+sum);

    }
}
