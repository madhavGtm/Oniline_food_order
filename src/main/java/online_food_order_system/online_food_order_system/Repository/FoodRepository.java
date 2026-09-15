package online_food_order_system.online_food_order_system.Repository;

import online_food_order_system.online_food_order_system.models.Category;
import online_food_order_system.online_food_order_system.models.Food;
import online_food_order_system.online_food_order_system.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    // Find foods by category
    List<Food> findByCategory(Category category);

    // Find foods by restaurant
    List<Food> findByRestaurant(Restaurant restaurant);

    // Find foods by restaurant ID
    List<Food> findByRestaurantId(Integer restaurantId);

}