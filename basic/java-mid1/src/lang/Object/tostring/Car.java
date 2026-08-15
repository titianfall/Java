package lang.Object.tostring;

public class Car {
    private String carName;

    public Car(String carName) {
        this.carName = carName;
    }

    public String carInfo() {
        return toString();
    }
}
