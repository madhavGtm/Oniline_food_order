package online_food_order_system.online_food_order_system.Controller;

import online_food_order_system.online_food_order_system.models.Category;
import online_food_order_system.online_food_order_system.services.CategoryService;
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
public class CategoryController
{

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categoryRegister")
    public String categoryRegister(Model model) {
        model.addAttribute("category", new Category());
        return "CategoryRegister";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@RequestParam("categoryName") String categoryName,
                               @RequestParam("imageFile") MultipartFile imageFile)
            throws IOException {

        Category category = new Category();
        category.setCategoryName(categoryName);

        String fileName = imageFile.getOriginalFilename();

        Path uploadPath = Paths.get("src/main/resources/static/images");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(),
                uploadPath.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING);

        category.setImage(fileName);

        categoryService.save(category);

        return "redirect:/showCategory";
    }

    @GetMapping("/showCategory")
    public String showCategory(Model model) {
        model.addAttribute("list", categoryService.getAll());
        return "ShowCategory";
    }

    @GetMapping("/editCategory/{id}")
    public String editCategory(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        return "EditCategory";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/showCategory";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.delete(id);
        return "redirect:/showCategory";
    }

}
