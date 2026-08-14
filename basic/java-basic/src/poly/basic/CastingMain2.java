package poly.basic;

public class CastingMain2 {
    public static void main(String[] args) {
        Parent poly = new Child();
        // poly.childMethod();

        // 일시적 다운캐스팅
        ((Child) poly).childMethod();


    }
}
