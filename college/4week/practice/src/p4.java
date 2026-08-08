class Average{
    private int [] intArray;
    private int index;

    public Average(){
        intArray = new int[10];
        index = 0;
    }
    public void put(int j){
        intArray[index++]=j;
    }

    public void showAll(){
        for(int i=0;i<index;i++){
            System.out.print(intArray[i]+" ");
        }
        System.out.println();
    }
    public double getAvg(){
        double sum=0;
        for(int i=0;i<index;i++){
            sum+=intArray[i];
        }
        return sum/index;
    }
}
 
public class p4 {
    public static void main(String[] args) {
        Average avg = new Average();
        avg.put(10);
        avg.put(15);
        avg.put(100);
        avg.showAll();
        System.out.println("평균은 "+avg.getAvg());
    }
}
