package class1;

public class ProductOrderMain {
    public static void main(String[] args) {
        ProductOrder[] productOrders = new ProductOrder[2];

        productOrders[0] = new ProductOrder();
        productOrders[0].productName = "상품1";
        productOrders[0].price = 100;
        productOrders[0].quantity = 10;

        productOrders[1] = new ProductOrder();
        productOrders[1].productName = "상품2";
        productOrders[1].price = 200;
        productOrders[1].quantity = 20;

        int totalPrice = 0;
        for (ProductOrder productOrder : productOrders) {
            System.out.println("상품 이름: " + productOrder.productName + " 상품 가격: " + productOrder.price + " 상품 수량: " + productOrder.quantity);
            totalPrice += productOrder.price * productOrder.quantity;
            System.out.println("총 가격: " + totalPrice);
        }

    }
}
