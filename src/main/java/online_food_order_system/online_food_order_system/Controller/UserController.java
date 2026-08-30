package online_food_order_system.online_food_order_system.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import online_food_order_system.online_food_order_system.models.Login;
import online_food_order_system.online_food_order_system.models.User;
import online_food_order_system.online_food_order_system.services.FoodService;
import online_food_order_system.online_food_order_system.services.LoginService;
import online_food_order_system.online_food_order_system.services.RestaurantService;
import online_food_order_system.online_food_order_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private FoodService foodService;

    // User Registration Page
    @GetMapping("/UserRegister")
    public String userRegisterPage() {
        return "UserRegister";
    }

    // Save User
    @PostMapping("/saveUser")
    public String saveUser(User user,
                           @RequestParam("password") String password,
                           HttpServletRequest request) {

        userService.saveUser(user);

        Login login = new Login();
        login.setEmail(user.getEmail());
        login.setPassword(password);
        login.setUsertype("user");

        loginService.saveLogin(login);

        HttpSession session = request.getSession();
        session.setAttribute("email", user.getEmail());
        session.setAttribute("usertype", "user");

        System.out.println("User Registered Successfully");
        System.out.println("Email : " + user.getEmail());

        return "saveUser";
    }

    // User Home
    @GetMapping("/UserHome")
    public String userHome(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        String email = (String) session.getAttribute("email");
        String usertype = (String) session.getAttribute("usertype");

        if (email == null || usertype == null) {
            return "redirect:/login";
        }

        if (!usertype.equalsIgnoreCase("user")) {
            return "redirect:/authError";
        }

        User user = userService.getUserByEmail(email);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        model.addAttribute("restaurants",
                restaurantService.getAllRestaurants());
        model.addAttribute("foods", foodService.getAll());

        return "UserHome";
    }

    // Edit User
    @PostMapping("/edit_user")
    public String editUser(@RequestParam("email") String email,
                           Model model) {

        User user = userService.getUserByEmail(email);

        model.addAttribute("user", user);

        return "edit_user";
    }

    // Update User
    @PostMapping("/update_user")
    public String updateUser(@RequestParam("email") String email,
                             @RequestParam("name") String name,
                             @RequestParam("address") String address,
                             @RequestParam("contact") String contact,
                             @RequestParam("pincode") String pincode,
                             @RequestParam("password") String password) {

        User user = userService.getUserByEmail(email);

        if (user != null) {

            user.setName(name);
            user.setAddress(address);
            user.setContact(contact);
            user.setPincode(pincode);
            user.setPassword(password);

            userService.saveUser(user);

            Login login = loginService.getByEmail(email);

            if (login != null) {
                login.setPassword(password);
                loginService.saveLogin(login);
            }
        }

        return "redirect:/UserHome";
    }

    // Delete User
    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam("email") String email,
                             HttpServletRequest request) {

        User user = userService.getUserByEmail(email);

        if (user != null) {
            userService.deleteUser(user);
        }

        loginService.deleteLoginByEmail(email);

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }

}
