package online_food_order_system.online_food_order_system.Repository;

import online_food_order_system.online_food_order_system.models.Food;
import online_food_order_system.online_food_order_system.models.Order;
import online_food_order_system.online_food_order_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser(User user);

    List<Order> findByFoodIn(List<Food> foods);
}
