package ref;

public class NullMain4 {
    public static void main(String[] args) {
        BigData bigData = new BigData();
        bigData.data = new Data();

        System.out.println(bigData.count); // 0
        System.out.println(bigData.data); // ref.Data@776ec8df

        System.out.println(bigData.data.value); // 0
    }
}
