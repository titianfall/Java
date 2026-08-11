package access.ex;

public class Item {
    private String name;
    private int price;
    private int quntity;
    public Item(String name, int price, int quntity) {
        this.name = name;
        this.price = price;
        this.quntity = quntity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuntity() {
        return quntity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quntity=" + quntity +
                '}';
    }

    public int getTotalPrice() {
        return price * quntity;
    }
}
