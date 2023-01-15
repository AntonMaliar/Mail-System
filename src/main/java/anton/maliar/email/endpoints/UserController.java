package anton.maliar.email.endpoints;

import anton.maliar.email.repository.model.User;
import anton.maliar.email.service.ErrorService;
import anton.maliar.email.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private ErrorService errorService;

    @Autowired
    public UserController(UserService userService, ErrorService errorService){
        this.userService = userService;
        this.errorService = errorService;
    }

    @GetMapping("/create")
    public String createUser(){
        return "user/create";
    }

    @PostMapping("/create")
    public String createUserPost(HttpServletRequest request){
        userService.saveUser(request);
        return "redirect:/"+errorService.pleaseLogIn();
    }

    @GetMapping("/update")
    public String updateUser(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session != null){
            model.addAttribute("user", (User)session.getAttribute("user"));
            return "user/update";
        }
        return "redirect:/"+errorService.pleaseLogIn();
    }

    @PostMapping("/update")
    public String updateUserPost(HttpServletRequest request){
        if(request.getSession(false) != null){
            userService.updateUser(request);
            return "redirect:/user/account";
        }
        return "redirect:/"+errorService.pleaseLogIn();
    }

    @GetMapping("/account")
    public String account(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session != null){
            model.addAttribute("user", (User)session.getAttribute("user"));
            return "user/account";
        }
        return "redirect:/"+errorService.pleaseLogIn();
    }

    @GetMapping("/log-in")
    public String logIn(){
        return "user/log-in";
    }

    @PostMapping("/log-in")
    public String logInPost(HttpServletRequest request){
        if(userService.logIn(request)){
            return "redirect:/user/account";
        }
        return "redirect:/"+errorService.noSuchUser();
    }

    @GetMapping("/log-out")
    public String logOut(HttpServletRequest request){
        request.getSession(false).invalidate();
        return "redirect:/";
    }

}
