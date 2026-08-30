package online_food_order_system.online_food_order_system.Repository;



import online_food_order_system.online_food_order_system.models.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, String> {

    Login findByEmail(String email);

    Login findByEmailAndPassword(String email, String password);

    void deleteByEmail(String email);

}