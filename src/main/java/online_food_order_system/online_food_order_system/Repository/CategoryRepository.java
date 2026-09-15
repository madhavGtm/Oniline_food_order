package online_food_order_system.online_food_order_system.Repository;


import online_food_order_system.online_food_order_system.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByCategoryNameIgnoreCase(String categoryName);
}
