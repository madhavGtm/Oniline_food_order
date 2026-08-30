package online_food_order_system.online_food_order_system.Repository;


import online_food_order_system.online_food_order_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);

}