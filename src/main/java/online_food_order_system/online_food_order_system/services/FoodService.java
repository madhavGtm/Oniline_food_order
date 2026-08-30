package online_food_order_system.online_food_order_system.services;


import online_food_order_system.online_food_order_system.Repository.FoodRepository;
import online_food_order_system.online_food_order_system.models.Food;
import online_food_order_system.online_food_order_system.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    // Save or Update Food
    public void save(Food food) {
        foodRepository.save(food);
    }

    // Get All Foods
    public List<Food> getAll() {
        return foodRepository.findAll();
    }

    // Get Food By Id
    public Food getById(Integer id) {
        return foodRepository.findById(id).orElse(null);
    }

    // Delete Food
    public void delete(Integer id) {
        foodRepository.deleteById(id);
    }

    // Get Foods By Restaurant Object
    public List<Food> getFoodByRestaurant(Restaurant restaurant) {
        return foodRepository.findByRestaurant(restaurant);
    }

    // Get Foods By Restaurant Id
    public List<Food> getFoodByRestaurantId(Integer restaurantId) {
        return foodRepository.findByRestaurantId(restaurantId);
    }

}
