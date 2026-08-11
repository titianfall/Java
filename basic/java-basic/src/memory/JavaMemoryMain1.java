package memory;

/**
 * 스택 영역 확인용
 * main -> method1 -> method2 순으로 스택 프레임이 쌓이고,
 * 종료는 반대 순서(method2 -> method1 -> main)로 제거된다.
 */
public class JavaMemoryMain1 {

    public static void main(String[] args) {
        System.out.println("main start");
        method1(10);
        System.out.println("main end");
    }

    static void method1(int m1) {
        System.out.println("method1 start");
        int cal = m1 * 2;
        method2(cal);
        System.out.println("method1 end");
    }

    static void method2(int m2) {
        System.out.println("method2 start");
        System.out.println("method2 end");
    }
}
