package online_food_order_system.online_food_order_system.Repository;

import online_food_order_system.online_food_order_system.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant ,Integer>
{
    Restaurant findByEmail(String restaurantEmail);
}