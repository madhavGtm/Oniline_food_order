package online_food_order_system.online_food_order_system.services;

import online_food_order_system.online_food_order_system.Repository.LoginRepository;
import online_food_order_system.online_food_order_system.dtos.LoginDto;
import online_food_order_system.online_food_order_system.models.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

    @Service
    public class LoginService {

        @Autowired
        private LoginRepository loginRepository;


        // Save or Update Login
        public Login saveLogin(Login login) {
            return loginRepository.save(login);
        }


        // Check Login
        public String checkLogin(LoginDto dto) {

            Login login = loginRepository.findByEmailAndPassword(
                    dto.getEmail(),
                    dto.getPassword()
            );

            if (login != null) {
                return login.getUsertype();
            }

            return null;
        }

        // Get Login By Email
        public Login getByEmail(String email) {
            return loginRepository.findByEmail(email);
        }


        // Delete Login By Email
        public boolean deleteLoginByEmail(String email) {

            Login login = loginRepository.findByEmail(email);

            if (login != null) {
                loginRepository.delete(login);
                return true;
            }

            return false;
        }

    }


