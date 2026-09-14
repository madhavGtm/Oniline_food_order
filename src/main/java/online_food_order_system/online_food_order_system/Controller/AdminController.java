package online_food_order_system.online_food_order_system.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import online_food_order_system.online_food_order_system.models.Admin;
import online_food_order_system.online_food_order_system.models.Login;

import online_food_order_system.online_food_order_system.services.AdminService;
import online_food_order_system.online_food_order_system.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminController {


        @Autowired
        private AdminService adminService;

        @Autowired
        private LoginService loginService;

        // Open Registration Page
        /* @GetMapping("Controller/adminRegister")
        public String adminRegisterPage() {
            return "AdminRegister";
        } */

    @GetMapping("Controller/adminRegister")
    public String registerPage(HttpSession session, Model model) {

        String usertype = (String) session.getAttribute("usertype");

        if (usertype == null || !usertype.equalsIgnoreCase("Restaurant")) {
            return "redirect:/login";
        }


        model.addAttribute("admin", new Admin());

        return "AdminRegister";
    }


        // Save Admin and Login Details
        @PostMapping("/Controller/saveAdmin")
        public String saveAdmin(Admin admin, @RequestParam("password") String password,
                                HttpServletRequest request) {

            adminService.saveAdmin(admin);

            Login login = new Login();
            login.setEmail(admin.getEmail());
            login.setPassword(password);
            login.setUsertype("admin");

            loginService.saveLogin(login);

            HttpSession session = request.getSession();
            session.setAttribute("email", admin.getEmail());
            session.setAttribute("usertype", "admin");

            return "redirect:/adminHome";
        }


        // Open Edit Admin Page
        @PostMapping("/edit_admin")
        public String editAdmin(Model model,
                                @RequestParam("email") String email) {

            Admin admin = adminService.getAdminByEmail(email);

            model.addAttribute("data", admin);

            return "edit_admin";
        }

        // Update Admin Details
        @PostMapping("/update_admin")
        public String updateAdmin(
                @RequestParam("name") String name,
                @RequestParam("email") String email,
                @RequestParam("password") String password,
                @RequestParam("contact") String contact) {

            Admin admin = adminService.getAdminByEmail(email);

            admin.setName(name);
            admin.setEmail(email);
            admin.setPassword(password);
            admin.setContact(contact);
            adminService.saveAdmin(admin);

            return "redirect:/adminHome";
        }

        // Admin Home Page
        @GetMapping("/adminHome")
        public String adminHome(HttpServletRequest request, Model model) {

            HttpSession session = request.getSession(false);

            if (session == null) {
                return "redirect:/authError";
            }

            try {
                String usertype = session.getAttribute("usertype").toString();

                if (!usertype.equalsIgnoreCase("admin")) {
                    return "redirect:/authError";
                }

                String email = session.getAttribute("email").toString();

                Admin admin = adminService.getAdminByEmail(email);

                model.addAttribute("admin", admin);

            } catch (Exception e) {
                return "redirect:/authError";
            }

            return "AdminHome";
        }
}

