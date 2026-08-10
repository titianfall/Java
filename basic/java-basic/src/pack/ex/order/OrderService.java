package pack.ex.order;

import pack.ex.product.Product;
import pack.ex.user.User;

public class OrderService {
    public void order() {
        User user = new User();
        Product product = new Product();
        Order order = new Order(user, product);
    }
}
