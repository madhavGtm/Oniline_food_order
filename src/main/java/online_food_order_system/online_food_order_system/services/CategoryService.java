package online_food_order_system.online_food_order_system.services;


import online_food_order_system.online_food_order_system.Repository.CategoryRepository;
import online_food_order_system.online_food_order_system.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Save or Update Category
    public void save(Category category) {
        categoryRepository.save(category);
    }

    // Get All Categories
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    // Get Category By ID
    public Category getById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // Delete Category
    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }

}