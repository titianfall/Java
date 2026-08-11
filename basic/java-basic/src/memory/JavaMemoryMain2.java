package memory;

/**
 * 스택 + 힙 영역 확인용
 * Data 인스턴스는 힙에 만들어지고, 스택에는 참조값만 저장된다.
 * method1이 끝나 스택 프레임이 사라지면 Data 인스턴스를 참조하는 곳이 없어져
 * GC의 회수 대상이 된다.
 */
public class JavaMemoryMain2 {

    public static void main(String[] args) {
        System.out.println("main start");
        method1();
        System.out.println("main end");
    }

    static void method1() {
        System.out.println("method1 start");
        Data data1 = new Data(10);
        method2(data1);
        System.out.println("method1 end");
    }

    static void method2(Data data2) {
        System.out.println("method2 start");
        System.out.println("data.value=" + data2.getValue());
        System.out.println("method2 end");
    }
}
