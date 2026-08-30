package online_food_order_system.online_food_order_system.Repository;
import online_food_order_system.online_food_order_system.models.Cart;
import online_food_order_system.online_food_order_system.models.Food;
import online_food_order_system.online_food_order_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    // Get all cart items of a user
    List<Cart> findByUser(User user);

    void deleteByFoodIn(List<Food> foods);

}

