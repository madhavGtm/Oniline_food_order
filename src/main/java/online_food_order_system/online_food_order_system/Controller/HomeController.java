package online_food_order_system.online_food_order_system.Controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import online_food_order_system.online_food_order_system.dtos.LoginDto;
import online_food_order_system.online_food_order_system.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController
{

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        LoginDto loginDto=new LoginDto();
        model.addAttribute(loginDto);
        return "loginForm";
    }

    @PostMapping("/login")

    public String login(HttpServletRequest request, Model model, @Valid @ModelAttribute LoginDto loginDto, BindingResult result)
    {
        String ut=loginService.checkLogin(loginDto);
        if(ut!=null)
        {
            HttpSession session=request.getSession(true);
            session.setAttribute("email",loginDto.getEmail());
            session.setAttribute("usertype",ut);


            System.out.println("Email    : " + loginDto.getEmail());
            System.out.println("password    : " + loginDto.getPassword());
            System.out.println("User Type: " + ut);

            if(ut.equalsIgnoreCase("admin")){
                return "redirect:/adminHome";
            }
            else if(ut.equalsIgnoreCase("user")){
                return "redirect:/UserHome";
            }
            else if(ut.equalsIgnoreCase("restaurant")){
                return "redirect:/RestaurantHome";
            }
        }

        return "loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        try
        {
            session.removeAttribute("email");
            session.removeAttribute("usertype");
            session.invalidate();
        }
        catch(Exception e)
        {

        }
        return "redirect/:";
    }
    @GetMapping("/authError")
    public String authError(){
        return "AuthError";
    }

}