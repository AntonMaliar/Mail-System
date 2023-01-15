package anton.maliar.email.endpoints;

import anton.maliar.email.repository.model.Letter;
import anton.maliar.email.service.ErrorService;
import anton.maliar.email.service.LetterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LetterControllerTest {
    private LetterController letterController;
    private LetterService letterService;
    private ErrorService errorService;
    private HttpServletRequest request;
    private Model model;

    @BeforeEach
    public void init(){
        letterService = mock(LetterService.class);
        errorService = mock(ErrorService.class);
        letterController = new LetterController(letterService, errorService);

        request = mock(HttpServletRequest.class);
        model = mock(Model.class);
    }
    @Test
    public void readLetterIfSessionNotNull(){
        when(request.getSession(false)).thenReturn(mock(HttpSession.class));
        when(letterService.getLetter(1L)).thenReturn(mock(Letter.class));

        assertEquals("letter/view", letterController.readLetter(request, model, 1L));
        verify(model).addAttribute("letter", letterService.getLetter(1L));
    }
    @Test
    public void readLetterIfSessionIsNull(){
        when(request.getSession(false)).thenReturn(null);
        when(errorService.pleaseLogIn()).thenReturn("?error=Please Log In");

        assertEquals(
                "redirect:/?error=Please Log In",
                letterController.readLetter(request, model, 1L)
        );
    }
    @Test
    public void createLetterIfSessionNotNull(){
        when(request.getSession(false)).thenReturn(mock(HttpSession.class));

        assertEquals("letter/write", letterController.createLetter(request));
    }
    @Test
    public void createLetterIfSessionIsNull(){
        when(request.getSession(false)).thenReturn(null);
        when(errorService.pleaseLogIn()).thenReturn("?error=Please Log In");

        assertEquals(
                "redirect:/?error=Please Log In",
                letterController.createLetter(request)
        );
    }
    @Test
    public void sendLetterIfSessionNotNull(){
        when(request.getSession(false)).thenReturn(mock(HttpSession.class));

        assertEquals("redirect:/user/account", letterController.sendLetter(request));
        verify(letterService).sendLetter(request);
    }
    @Test
    public void sendLetterIfSessionIsNull(){
        when(request.getSession(false)).thenReturn(null);
        when(errorService.pleaseLogIn()).thenReturn("?error=Please Log In");

        assertEquals(
                "redirect:/?error=Please Log In",
                letterController.sendLetter(request)
        );
    }
    @Test
    public void deleteLetterIfSessionNotNull(){
        when(request.getSession(false)).thenReturn(mock(HttpSession.class));

        assertEquals(
                "redirect:/user/account",
                letterController.deleteLetter(request, 1L)
        );
        verify(letterService).deleteLetter(1L);
    }
    @Test
    public void deleteLetterIfSessionIsNull(){
        when(request.getSession(false)).thenReturn(null);
        when(errorService.pleaseLogIn()).thenReturn("?error=Please Log In");

        assertEquals("redirect:/?error=Please Log In", letterController.deleteLetter(request, 1L));
    }
    @Test
    public void incomingLettersIfSessionNotNull(){
        when(request.getSession(false)).thenReturn(mock(HttpSession.class));

        assertEquals("letter/incoming", letterController.incomingLetters(request, model));
        verify(model).addAttribute("incomingLetters", letterService.getIncomingLetters(request));
    }
    @Test
    public void incomingLettersIfSessionIsNull(){
        when(request.getSession(false)).thenReturn(null);
        when(errorService.pleaseLogIn()).thenReturn("?error=Please Log In");

        assertEquals("redirect:/?error=Please Log In", letterController.incomingLetters(request, model));
    }
   @Test
   public void readLettersIfSessionNotNull(){
       when(request.getSession(false)).thenReturn(mock(HttpSession.class));

       assertEquals("letter/read", letterController.readLetters(request, model));
       verify(model).addAttribute("readLetters", letterService.getReadLetters(request));
   }
   @Test
   public void readLettersIfSessionIsNull(){
       when(request.getSession(false)).thenReturn(null);
       when(errorService.pleaseLogIn()).thenReturn("?error=Please Log In");

       assertEquals("redirect:/?error=Please Log In", letterController.readLetters(request, model));
    }
    @Test
    public void sentLetterIfSessionNotNull(){
        when(request.getSession(false)).thenReturn(mock(HttpSession.class));

        assertEquals("letter/sent", letterController.sentLetters(request, model));
        verify(model).addAttribute("sentLetters", letterService.getSentLetters(request));
    }
    @Test
    public void sentLettersIfSessionIsNull(){
        when(request.getSession(false)).thenReturn(null);
        when(errorService.pleaseLogIn()).thenReturn("?error=Please Log In");

        assertEquals("redirect:/?error=Please Log In", letterController.sentLetters(request,model));
    }
}
























