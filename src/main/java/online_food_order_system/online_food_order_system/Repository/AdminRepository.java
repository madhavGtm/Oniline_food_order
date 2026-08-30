package online_food_order_system.online_food_order_system.Repository;

import online_food_order_system.online_food_order_system.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository  extends JpaRepository<Admin, String>
{
    Admin findByEmail(String email);
}
