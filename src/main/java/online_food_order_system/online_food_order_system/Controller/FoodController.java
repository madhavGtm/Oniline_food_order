package online_food_order_system.online_food_order_system.Controller;


import jakarta.servlet.http.HttpSession;
import online_food_order_system.online_food_order_system.models.Category;
import online_food_order_system.online_food_order_system.models.Food;
import online_food_order_system.online_food_order_system.models.Restaurant;
import online_food_order_system.online_food_order_system.services.CategoryService;
import online_food_order_system.online_food_order_system.services.FoodService;
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
import java.util.List;

@Controller
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RestaurantService restaurantService;

    // Open Food Register Page
    @GetMapping("/foodRegister")
    public String foodRegister(HttpSession session, Model model) {

        String email = (String) session.getAttribute("email");

        Restaurant restaurant = restaurantService.getRestaurantByEmail(email);

        if (restaurant == null) {
            return "redirect:/login";
        }

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("food", new Food());
        model.addAttribute("categoryList", categoryService.getAll());

        return "FoodRegister";
    }

    // Save Food
    @PostMapping("/saveFood")
    public String saveFood(@RequestParam("foodName") String foodName,
                           @RequestParam("price") Double price,
                           @RequestParam("description") String description,
                           @RequestParam("categoryId") Integer categoryId,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           HttpSession session) throws IOException {

        String email = (String) session.getAttribute("email");

        Restaurant restaurant = restaurantService.getRestaurantByEmail(email);

        if (restaurant == null) {
            return "redirect:/login";
        }

        Food food = new Food();

        food.setFoodName(foodName);
        food.setPrice(price);
        food.setDescription(description);

        Category category = categoryService.getById(categoryId);
        food.setCategory(category);

        // Assign logged-in restaurant
        food.setRestaurant(restaurant);

        if (!imageFile.isEmpty()) {

            String fileName = imageFile.getOriginalFilename();

            Path uploadPath = Paths.get("src/main/resources/static/images");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(imageFile.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            food.setImage(fileName);
        }

        foodService.save(food);

        return "redirect:/showFood";
    }

    // Show All Foods
    @GetMapping("/showFood")
    public String showFood(Model model) {

        model.addAttribute("foodList", foodService.getAll());

        return "ShowFood";
    }

    // Open Edit Food Page
    @GetMapping("/editFood/{id}")
    public String editFood(@PathVariable Integer id,
                           Model model) {

        model.addAttribute("food", foodService.getById(id));
        model.addAttribute("categoryList", categoryService.getAll());

        return "EditFood";
    }

    // Update Food
    @PostMapping("/updateFood")
    public String updateFood(@ModelAttribute Food food,
                             @RequestParam("imageFile") MultipartFile imageFile)
            throws IOException {

        Food oldFood = foodService.getById(food.getId());

        if (oldFood == null) {
            return "redirect:/showFood";
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

            food.setImage(fileName);

        } else {

            food.setImage(oldFood.getImage());
        }

        // Keep existing restaurant
        food.setRestaurant(oldFood.getRestaurant());

        foodService.save(food);

        return "redirect:/showFood";
    }

    // Delete Food
    @GetMapping("/deleteFood/{id}")
    public String deleteFood(@PathVariable Integer id) {

        foodService.delete(id);

        return "redirect:/showFood";
    }

    // User clicks Restaurant -> Show Restaurant Menu
    @GetMapping("/restaurantMenu/{id}")
    public String restaurantMenu(@PathVariable Integer id,
                                 Model model) {

        Restaurant restaurant = restaurantService.getRestaurantById(id);

        if (restaurant == null) {
            return "redirect:/UserHome";
        }

        List<Food> restaurantFoods = foodService.getFoodByRestaurantId(id);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("foodList", restaurantFoods);
        model.addAttribute("categories", restaurantFoods.stream()
                .filter(food -> food.getCategory() != null)
                .map(food -> food.getCategory().getCategoryName())
                .filter(categoryName -> categoryName != null && !categoryName.isBlank())
                .distinct()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList());

        return "RestaurantMenu";
    }

}
