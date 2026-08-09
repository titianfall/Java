package ref.ex;

import java.util.Scanner;

public class ProductOrderMain3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("입력할 주문의 개수를 입력하세요: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        ProductOrder[] productOrders = new ProductOrder[n];
        for(int i = 1; i <= n; i++){
            System.out.println(i + "번째 주문 정보를 입력하세요.");

            String productName;
            int price;
            int quantity;
            System.out.print("상품명: ");
            productName = scanner.next();

            System.out.print("가격: ");
            price = scanner.nextInt();

            System.out.print("수량: ");
            quantity = scanner.nextInt();

            productOrders[i - 1] = createOrder(productName, price, quantity);
        }

        printOrders(productOrders);
        int totalAmount = getTotalAmount(productOrders);
        System.out.println("총 결제 금액: " + totalAmount);
    }

    static ProductOrder createOrder(String productName, int price, int quantity) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.productName = productName;
        productOrder.price = price;
        productOrder.quantity = quantity;

        return productOrder;
    }

    static void printOrders(ProductOrder[] productOrders) {
        for (ProductOrder productOrder : productOrders) {
            System.out.println("상품명: " + productOrder.productName + ", 가격: " + productOrder.price + ", 수량: " + productOrder.quantity);
        }
    }

    static int getTotalAmount(ProductOrder[] productOrders) {
        int total = 0;
        for (ProductOrder productOrder : productOrders) {
            total += productOrder.price * productOrder.quantity;
        }

        return total;
    }
}
