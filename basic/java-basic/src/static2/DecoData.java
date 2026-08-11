package static2;

/**
 * 정적 메서드와 인스턴스 메서드의 접근 범위 비교
 * - 정적 메서드는 정적 변수/정적 메서드만 사용할 수 있다.
 * - 인스턴스 메서드는 둘 다 사용할 수 있다.
 */
public class DecoData {

    private int instanceValue;
    private static int staticValue;

    public static void staticCall() {
        // instanceValue++;  // 인스턴스 변수 접근, compile error
        // instanceMethod(); // 인스턴스 메서드 접근, compile error

        staticValue++;  // 정적 변수 접근
        staticMethod(); // 정적 메서드 접근
    }

    public void instanceCall() {
        instanceValue++;  // 인스턴스 변수 접근
        instanceMethod(); // 인스턴스 메서드 접근

        staticValue++;  // 정적 변수 접근
        staticMethod(); // 정적 메서드 접근
    }

    private void instanceMethod() {
        System.out.println("instanceValue=" + instanceValue);
    }

    private static void staticMethod() {
        System.out.println("staticValue=" + staticValue);
    }
}
