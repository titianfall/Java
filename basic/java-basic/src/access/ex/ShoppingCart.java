package access.ex;

public class ShoppingCart {
    private Item[] items = new Item[10];
    private int itemCount = 0;

    public void addItem(Item item) {
        items[itemCount++] = item;
    }

    public void displayItems() {
        System.out.println("장바구니 상품 출력");
        int totalPrice = 0;
        for (int i = 0; i < itemCount; i++) {
            int sum = 0;
            System.out.println(items[i]);
            sum = items[i].getTotalPrice();
            System.out.println("sum : " + sum);

            totalPrice += sum;
        }

        System.out.println("totalPrice = " + totalPrice);
    }
}
