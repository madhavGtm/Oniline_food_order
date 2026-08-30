package online_food_order_system.online_food_order_system.Controller;

import jakarta.servlet.http.HttpSession;
import online_food_order_system.online_food_order_system.models.Login;
import online_food_order_system.online_food_order_system.models.Restaurant;
import online_food_order_system.online_food_order_system.services.LoginService;
import online_food_order_system.online_food_order_system.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private LoginService loginService;

    // Restaurant Registration Page
    @GetMapping("/RestaurantRegister")
    public String registerPage(HttpSession session, Model model) {

        String usertype = (String) session.getAttribute("usertype");

        if (usertype == null || !usertype.equalsIgnoreCase("admin")) {
            return "redirect:/login";
        }

        model.addAttribute("restaurant", new Restaurant());

        return "RestaurantRegister";
    }

    // Save Restaurant
    @PostMapping("/RestaurantRegister")
    public String saveRestaurant(@ModelAttribute("restaurant") Restaurant restaurant,
                                 @RequestParam("imageFile") MultipartFile imageFile,
                                 HttpSession session) throws IOException {

        String usertype = (String) session.getAttribute("usertype");

        if (usertype == null || !usertype.equalsIgnoreCase("admin")) {
            return "redirect:/login";
        }

        if (!imageFile.isEmpty()) {

            String fileName = imageFile.getOriginalFilename();

            Path uploadPath = Paths.get("src/main/resources/static/images");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(imageFile.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            restaurant.setImage(fileName);
        }

        restaurantService.saveRestaurant(restaurant);

        Login login = new Login();
        login.setEmail(restaurant.getEmail());
        login.setPassword(restaurant.getPassword());
        login.setUsertype("restaurant");

        loginService.saveLogin(login);

        return "redirect:/adminHome";
    }

    // Show Restaurants
    @GetMapping("/ShowRestaurants")
    public String showRestaurants(HttpSession session, Model model) {

        String usertype = (String) session.getAttribute("usertype");

        if (usertype == null || !usertype.equalsIgnoreCase("admin")) {
            return "redirect:/login";
        }

        model.addAttribute("restaurants", restaurantService.getAllRestaurants());

        return "ShowRestaurants";
    }

    // Edit Restaurant Page
    @GetMapping("/EditRestaurant")
    public String editRestaurant(HttpSession session, Model model) {

        String email = (String) session.getAttribute("email");
        String usertype = (String) session.getAttribute("usertype");

        if (email == null || usertype == null) {
            return "redirect:/login";
        }

        Restaurant restaurant = restaurantService.getRestaurantByEmail(email);

        if (restaurant == null) {
            return "redirect:/login";
        }

        model.addAttribute("restaurant", restaurant);

        return "EditRestaurant";
    }

    // Update Restaurant
    @PostMapping("/EditRestaurant")
    public String updateRestaurant(@ModelAttribute("restaurant") Restaurant restaurant,
                                   @RequestParam("imageFile") MultipartFile imageFile,
                                   HttpSession session) throws IOException {

        String usertype = (String) session.getAttribute("usertype");

        if (usertype == null || !usertype.equalsIgnoreCase("restaurant")) {
            return "redirect:/login";
        }

        Restaurant oldRestaurant =
                restaurantService.getRestaurantByEmail(restaurant.getEmail());

        if (!imageFile.isEmpty()) {

            String fileName = imageFile.getOriginalFilename();

            Path uploadPath = Paths.get("src/main/resources/static/images");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(imageFile.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            restaurant.setImage(fileName);

        } else if (oldRestaurant != null) {

            restaurant.setImage(oldRestaurant.getImage());
        }

        restaurantService.updateRestaurant(restaurant);

        Login login = loginService.getByEmail(restaurant.getEmail());

        if (login != null) {
            login.setPassword(restaurant.getPassword());
            loginService.saveLogin(login);
        }

        return "redirect:/RestaurantHome";
    }

    // Delete Restaurant
    @PostMapping("/DeleteRestaurant")
    public String deleteOwnRestaurant(HttpSession session) {

        String email = (String) session.getAttribute("email");
        String usertype = (String) session.getAttribute("usertype");

        if (email == null || usertype == null) {
            return "redirect:/login";
        }

        if (!usertype.equalsIgnoreCase("restaurant")) {
            return "redirect:/authError";
        }

        restaurantService.deleteRestaurantByEmail(email);
        loginService.deleteLoginByEmail(email);

        session.invalidate();

        return "redirect:/login";
    }

    // ===========================
    // Delete Restaurant By Admin
    // ===========================
    @GetMapping("/DeleteRestaurant/{email}")
    public String deleteRestaurantByAdmin(@PathVariable("email") String email,
                                          HttpSession session) {

        String usertype = (String) session.getAttribute("usertype");

        if (usertype == null || !usertype.equalsIgnoreCase("admin")) {
            return "redirect:/login";
        }

        restaurantService.deleteRestaurantByEmail(email);
        loginService.deleteLoginByEmail(email);

        return "redirect:/ShowRestaurants";
    }

    // Restaurant Home
    @GetMapping("/RestaurantHome")
    public String restaurantHome(HttpSession session, Model model) {

        String email = (String) session.getAttribute("email");
        String usertype = (String) session.getAttribute("usertype");

        if (email == null || usertype == null) {
            return "redirect:/login";
        }

        if (!usertype.equalsIgnoreCase("restaurant")) {
            return "redirect:/authError";
        }

        Restaurant restaurant = restaurantService.getRestaurantByEmail(email);

        if (restaurant == null) {
            return "redirect:/authError";
        }

        model.addAttribute("restaurant", restaurant);

        return "RestaurantHome";
    }

    // Logout
    @GetMapping("/RestaurantLogout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }

}
