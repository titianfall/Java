package lang.Object.poly;

public class ObjectPolyExam1 {
    public static void main(String[] args) {
        Dog dog = new Dog();
        Car car = new Car();

        action(dog);
        action(car);
    }

    private static void action(Object object) {
        // obj.sound();
        // obj.move();

        if(object instanceof Dog dog) {
            dog.sound();
        } else if(object instanceof Car car) {
            car.move();
        }
    }
}
