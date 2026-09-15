package online_food_order_system.online_food_order_system.Repository;

import online_food_order_system.online_food_order_system.models.Food;
import online_food_order_system.online_food_order_system.models.Order;
import online_food_order_system.online_food_order_system.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository
        extends JpaRepository<OrderItem, Integer> {

    void deleteByOrder(Order order);

    List<OrderItem> findByOrder(Order order);

    void deleteByFoodIn(List<Food> foods);

    void deleteByOrderIn(List<Order> orders);
}
