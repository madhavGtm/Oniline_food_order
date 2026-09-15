package online_food_order_system.online_food_order_system.Controller;

import jakarta.servlet.http.HttpSession;
import online_food_order_system.online_food_order_system.models.Cart;
import online_food_order_system.online_food_order_system.models.Food;
import online_food_order_system.online_food_order_system.models.User;
import online_food_order_system.online_food_order_system.services.CartService;
import online_food_order_system.online_food_order_system.services.FoodService;
import online_food_order_system.online_food_order_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController
{

    @Autowired
    private CartService cartService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    // Add Food To Cart
    @GetMapping("/addToCart/{foodId}")
    public String addToCart(@PathVariable Integer foodId,
                            HttpSession session) {

        String email = (String) session.getAttribute("email");

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(email);

        Food food = foodService.getById(foodId);

        if (user == null || food == null) {
            return "redirect:/UserHome";
        }

        Cart cart = new Cart();

        cart.setUser(user);
        cart.setFood(food);
        cart.setQuantity(1);
        cart.setPrice(food.getPrice());

        cartService.save(cart);

        return "redirect:/showCart";
    }

    // Show Cart
    @GetMapping("/showCart")
    public String showCart(HttpSession session,
                           Model model) {

        String email = (String) session.getAttribute("email");

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(email);

        model.addAttribute("cartList",
                cartService.getCartByUser(user));

        return "Cart";
    }

    // Remove Item
    @GetMapping("/removeCart/{id}")
    public String removeCart(@PathVariable Integer id) {

        cartService.delete(id);

        return "redirect:/showCart";
    }

    // Increase Quantity
    @GetMapping("/increase/{id}")
    public String increase(@PathVariable Integer id) {

        cartService.increaseQuantity(id);

        return "redirect:/showCart";
    }

    // Decrease Quantity
    @GetMapping("/decrease/{id}")
    public String decrease(@PathVariable Integer id) {

        cartService.decreaseQuantity(id);

        return "redirect:/showCart";
    }

}
