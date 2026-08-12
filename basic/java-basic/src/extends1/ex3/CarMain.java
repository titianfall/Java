package extends1.ex3;

public class CarMain {
    public static void main(String[] args) {
        ElectricCar electricCar = new ElectricCar();
        electricCar.move();
        electricCar.openDoor();
        electricCar.charge();

        GasCar gasCar = new GasCar();
        gasCar.move();
        gasCar.openDoor();
        gasCar.fillUp();

        HydrogenCar hydrogenCar = new HydrogenCar();
        hydrogenCar.move();
        hydrogenCar.openDoor();
        hydrogenCar.fillHydrogen();
    }
}
