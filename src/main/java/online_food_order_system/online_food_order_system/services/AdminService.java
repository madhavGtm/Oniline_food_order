package online_food_order_system.online_food_order_system.services;

import online_food_order_system.online_food_order_system.Repository.AdminRepository;
import online_food_order_system.online_food_order_system.models.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService
{
    @Autowired
    private AdminRepository adminRepository;

    public Admin getAdminByEmail(String email) {
        return
                adminRepository.findByEmail(email);
    }
    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }

}
