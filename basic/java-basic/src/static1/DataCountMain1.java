package static1;

public class DataCountMain1 {

    public static void main(String[] args) {
        Data1 data1 = new Data1("A");
        System.out.println("A count=" + data1.count);

        Data1 data2 = new Data1("B");
        System.out.println("B count=" + data2.count);

        Data1 data3 = new Data1("C");
        System.out.println("C count=" + data3.count);
        // 기대: 1, 2, 3 / 실제: 1, 1, 1
        // 인스턴스 변수는 인스턴스마다 별도로 존재하기 때문
    }
}
