package lang.Object.tostring;

public class BadObjectPrinter {
    public static void print(Car car) {
        String string = "객체 정보 출력: " + car.carInfo(); // carInfo() 메서드 만듬
        System.out.println(string);
    }

    public static void print(Dog dog) {
        String string = "객체 정보 출력: " + dog.dogInfo(); // dogInfo() 메서드 만듬
        System.out.println(string);
    }
}
