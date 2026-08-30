package online_food_order_system.online_food_order_system.Repository;

import online_food_order_system.online_food_order_system.models.Order_User;

import java.util.List;

public interface OrderUserService {

    List<Order_User> getAllOrders();
}