package online_food_order_system.online_food_order_system.Controller;


import jakarta.servlet.http.HttpSession;
import online_food_order_system.online_food_order_system.models.Order;
import online_food_order_system.online_food_order_system.models.User;
import online_food_order_system.online_food_order_system.services.CartService;
import online_food_order_system.online_food_order_system.services.OrderService;
import online_food_order_system.online_food_order_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    // Checkout Page
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {

        String email = (String) session.getAttribute("email");

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(email);

        model.addAttribute("user", user);
        model.addAttribute("cartList", cartService.getCartByUser(user));
        model.addAttribute("total", cartService.getTotal(user));

        return "Checkout";
    }

    // Place Order
    @PostMapping("/placeOrder")
    public String placeOrder(HttpSession session, Model model) {

        String email = (String) session.getAttribute("email");

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(email);

        // Save order and get saved order object
        Order order = orderService.placeOrder(user);

        // Send order object to OrderSuccess.html
        model.addAttribute("order", order);

        return "OrderSuccess";
    }

    // Order History
    @GetMapping("/orderHistory")
    public String orderHistory(HttpSession session, Model model) {

        String email = (String) session.getAttribute("email");

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(email);

        model.addAttribute("orders",
                orderService.getOrdersByUser(user));

        return "OrderHistory";
    }
}
