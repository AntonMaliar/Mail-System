package anton.maliar.email.endpoints;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(HttpServletRequest request, Model model){
        if (request.getParameter("error") != null){
            model.addAttribute("error", request.getParameter("error"));
            return "main-page";
        }
        return "main-page";
    }
}
