package online_food_order_system.online_food_order_system.Repository;

import online_food_order_system.online_food_order_system.models.Order_User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderUserRepository
        extends JpaRepository<Order_User, Integer> {
}