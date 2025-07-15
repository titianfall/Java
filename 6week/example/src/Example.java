import java.util.StringTokenizer;

public class Example{
    public static void main(String[] args){
        StringTokenizer st = new StringTokenizer("a=3,b=5,c=6",",=");
        int sum=0;
        while(st.hasMoreTokens()){
            String next = st.nextToken();
            try {
                sum+=Integer.parseInt(next);
                System.out.println(next);
            } catch (NumberFormatException e) {
                System.out.println(next);
            }
        }
        System.out.println("합은 "+sum);
    }
}