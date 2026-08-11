package static1;

public class DataCountMain2 {

    public static void main(String[] args) {
        Counter counter = new Counter(); // 공용 카운터 인스턴스 1개

        Data2 data1 = new Data2("A", counter);
        System.out.println("A count=" + counter.count);

        Data2 data2 = new Data2("B", counter);
        System.out.println("B count=" + counter.count);

        Data2 data3 = new Data2("C", counter);
        System.out.println("C count=" + counter.count);
        // 1, 2, 3 -> 원하는 결과는 나오지만 Counter를 항상 넘겨야 한다
    }
}
