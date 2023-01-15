package anton.maliar.email.endpoints;

import anton.maliar.email.service.ErrorService;
import anton.maliar.email.service.LetterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/letter")
public class LetterController {
    private LetterService letterService;
    private ErrorService errorService;

    @Autowired
    public LetterController(LetterService letterService, ErrorService errorService){
        this.letterService = letterService;
        this.errorService = errorService;
    }

    @GetMapping("/view/{letterId}")
    public String readLetter(HttpServletRequest request, Model model, @PathVariable Long letterId){
        if(request.getSession(false) != null){
            model.addAttribute("letter", letterService.getLetter(letterId));
            return "letter/view";
        }
        return "redirect:/"+errorService.pleaseLogIn();
    }

    @GetMapping("/write")
    public String createLetter(HttpServletRequest request){
        if(request.getSession(false) != null){
            return "letter/write";
        }
        return "redirect:/"+errorService.pleaseLogIn();
    }

    @PostMapping("/send")
    public String sendLetter(HttpServletRequest request){
        if(request.getSession(false) != null){
            letterService.sendLetter(request);
            return "redirect:/user/account";
        }
        return "redirect:/"+errorService.pleaseLogIn();
    }

    @GetMapping("/delete/{letterId}")
    public String deleteLetter(HttpServletRequest request, @PathVariable Long letterId ){
        if(request.getSession(false) != null){
            letterService.deleteLetter(letterId);
            return "redirect:/user/account";
        }
        return "redirect:/"+errorService.pleaseLogIn();
    }

    @GetMapping("/incoming")
    public String incomingLetters(HttpServletRequest request, Model model){
        if(request.getSession(false) != null) {
            model.addAttribute("incomingLetters", letterService.getIncomingLetters(request));
            return "letter/incoming";
        }
        return "redirect:/"+errorService.pleaseLogIn();
    }

    @GetMapping("/read")
    public String readLetters(HttpServletRequest request, Model model){
        if(request.getSession(false) != null){
            model.addAttribute("readLetters", letterService.getReadLetters(request));
            return "letter/read";
        }
        return "redirect:/"+errorService.pleaseLogIn();
    }

    @GetMapping("/sent")
    public String sentLetters(HttpServletRequest request, Model model){
        if(request.getSession(false) != null){
            model.addAttribute("sentLetters", letterService.getSentLetters(request));
            return "letter/sent";
        }
        return "redirect:/"+errorService.pleaseLogIn();
    }
}
