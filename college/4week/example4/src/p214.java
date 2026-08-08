public class p214 {
    static double getSum(double []d){
        double sum=0;
        for(int i=0; i<d.length;i++){
            sum+=d[i];
        }
        return sum;
    }
    public static void main(String[] args){
        double[] dArray = {1.1,2.2,3.3,4.4,5.5};

        System.out.println(getSum(dArray));
    }
}
