package online_food_order_system.online_food_order_system.services;

import online_food_order_system.online_food_order_system.Repository.CartRepository;
import online_food_order_system.online_food_order_system.Repository.OrderItemRepository;
import online_food_order_system.online_food_order_system.Repository.OrderRepository;
import online_food_order_system.online_food_order_system.models.Cart;
import online_food_order_system.online_food_order_system.models.Order;
import online_food_order_system.online_food_order_system.models.OrderItem;
import online_food_order_system.online_food_order_system.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    // Place Order
    @Transactional
    public Order placeOrder(User user) {

        List<Cart> cartList =
                cartRepository.findByUser(user);

        if (cartList == null || cartList.isEmpty()) {
            return null;
        }

        Order lastSavedOrder = null;

        for (Cart cart : cartList) {

            Order order = new Order();

            order.setUser(user);
            order.setFood(cart.getFood());
            order.setQuantity(cart.getQuantity());

            double totalPrice =
                    cart.getPrice() * cart.getQuantity();

            order.setTotalPrice(totalPrice);
            order.setPaymentMethod("Cash On Delivery");
            order.setDeliveryAddress(user.getAddress());
            order.setOrderStatus("Pending");

            lastSavedOrder =
                    orderRepository.save(order);

            OrderItem item = new OrderItem();

            item.setOrder(lastSavedOrder);
            item.setFood(cart.getFood());
            item.setQuantity(cart.getQuantity());
            item.setPrice(cart.getPrice());
            item.setTotalPrice(totalPrice);

            orderItemRepository.save(item);
        }

        // Cart clear after successful order
        cartRepository.deleteAll(cartList);

        return lastSavedOrder;
    }

    // User Order History
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    // All Orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get Order By ID
    public Order getOrderById(Integer id) {

        return orderRepository
                .findById(id)
                .orElse(null);
    }

    // Accept Order
    public boolean acceptOrder(Integer id) {

        Order order = orderRepository
                .findById(id)
                .orElse(null);

        if (order == null) {
            return false;
        }

        order.setOrderStatus("Accepted");
        orderRepository.save(order);

        return true;
    }

    // Reject Order
    public boolean rejectOrder(Integer id) {

        Order order = orderRepository
                .findById(id)
                .orElse(null);

        if (order == null) {
            return false;
        }

        order.setOrderStatus("Rejected");
        orderRepository.save(order);

        return true;
    }

    // Mark order as delivered
    public boolean deliverOrder(Integer id) {

        Order order = orderRepository
                .findById(id)
                .orElse(null);

        if (order == null) {
            return false;
        }

        order.setOrderStatus("Delivered");
        orderRepository.save(order);

        return true;
    }

    // Delete Order
    @Transactional
    public boolean deleteOrder(Integer id) {

        Order order = orderRepository
                .findById(id)
                .orElse(null);

        if (order == null) {
            return false;
        }

        orderItemRepository.deleteByOrder(order);
        orderRepository.delete(order);

        return true;
    }
}
