package online_food_order_system.online_food_order_system.services;

import online_food_order_system.online_food_order_system.Repository.*;
import online_food_order_system.online_food_order_system.models.Food;
import online_food_order_system.online_food_order_system.models.Order;
import online_food_order_system.online_food_order_system.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Save Restaurant
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    // Get All Restaurants
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    // Get Restaurant By Id
    public Restaurant getRestaurantById(Integer id) {

        Optional<Restaurant> optionalRestaurant =  // optional mean resturant are persent or not
                restaurantRepository.findById(id);

        return optionalRestaurant.orElse(null);
    }

    // Get Restaurant By Email
    public Restaurant getRestaurantByEmail(String email) {
        return restaurantRepository.findByEmail(email);
    }

    // Update Restaurant
    public Restaurant updateRestaurant(Restaurant restaurant) {

        Restaurant existingRestaurant =
                restaurantRepository.findById(restaurant.getId()).orElse(null);

        if (existingRestaurant == null) {
            return null;
        }

        existingRestaurant.setRestaurantName(restaurant.getRestaurantName());
        existingRestaurant.setAddress(restaurant.getAddress());
        existingRestaurant.setContact(restaurant.getContact());
        existingRestaurant.setEmail(restaurant.getEmail());
        existingRestaurant.setPassword(restaurant.getPassword());
        existingRestaurant.setOpeningTime(restaurant.getOpeningTime());
        existingRestaurant.setClosingTime(restaurant.getClosingTime());
        existingRestaurant.setImage(restaurant.getImage());

        return restaurantRepository.save(existingRestaurant);
    }

    // Delete Restaurant By Email
    @Transactional
    public void deleteRestaurantByEmail(String email) {

        Restaurant restaurant = restaurantRepository.findByEmail(email);

        if (restaurant != null) {
            List<Food> foods = foodRepository.findByRestaurant(restaurant);

            if (!foods.isEmpty()) {
                List<Order> orders = orderRepository.findByFoodIn(foods);

                // Food rows are referenced by carts and orders. Remove those
                // children first so the database foreign keys remain valid.
                cartRepository.deleteByFoodIn(foods);
                orderItemRepository.deleteByFoodIn(foods);

                if (!orders.isEmpty()) {
                    orderItemRepository.deleteByOrderIn(orders);
                    orderRepository.deleteAll(orders);
                }

                foodRepository.deleteAll(foods);
            }

            restaurantRepository.delete(restaurant);
        }
    }

    // Delete Restaurant By Id
    public void deleteRestaurantById(Integer id) {
        restaurantRepository.deleteById(id);
    }

    // Check Restaurant Exists
    public boolean existsByEmail(String email) {
        return restaurantRepository.findByEmail(email) != null;
    }
}
