package online_food_order_system.online_food_order_system.Controller;


import online_food_order_system.online_food_order_system.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderUserController {

    @Autowired
    private OrderService orderService;

    // Show all orders
    @GetMapping("/showOrders")
    public String showOrders(Model model) {

        model.addAttribute(
                "orders",
                orderService.getAllOrders()
        );

        return "show_orders";
    }

    // Accept order
    @GetMapping("/acceptOrder")
    public String acceptOrder(
            @RequestParam("id") Integer id,
            RedirectAttributes redirectAttributes) {

        boolean accepted =
                orderService.acceptOrder(id);

        if (accepted) {
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Order accepted successfully"
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Order not found"
            );
        }

        return "redirect:/showOrders";
    }

    // Reject order
    @GetMapping("/rejectOrder")
    public String rejectOrder(
            @RequestParam("id") Integer id,
            RedirectAttributes redirectAttributes) {

        boolean rejected =
                orderService.rejectOrder(id);

        if (rejected) {
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Order rejected successfully"
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Order not found"
            );
        }

        return "redirect:/showOrders";
    }

    // Deliver order
    @GetMapping("/deliverOrder")
    public String deliverOrder(
            @RequestParam("id") Integer id,
            RedirectAttributes redirectAttributes) {

        boolean delivered =
                orderService.deliverOrder(id);

        if (delivered) {
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Order delivered successfully"
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Order not found"
            );
        }

        return "redirect:/showOrders";
    }

    // Delete order
    @GetMapping("/deleteOrder")
    public String deleteOrder(
            @RequestParam("id") Integer id,
            RedirectAttributes redirectAttributes) {

        boolean deleted =
                orderService.deleteOrder(id);

        if (deleted) {
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Order deleted successfully"
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Order not found"
            );
        }

        return "redirect:/showOrders";
    }
}