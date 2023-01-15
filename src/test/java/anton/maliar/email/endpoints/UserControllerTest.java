package anton.maliar.email.endpoints;

import anton.maliar.email.repository.model.User;
import anton.maliar.email.service.ErrorService;
import anton.maliar.email.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private UserController userController;
    private UserService userService;
    private ErrorService errorService;
    private HttpServletRequest request;
    private Model model;
    private HttpSession session;

    @BeforeEach
    public void init(){
        userService = mock(UserService.class);
        errorService = mock(ErrorService.class);
        userController = new UserController(userService, errorService);
        request = mock(HttpServletRequest.class);
        model = mock(Model.class);
        session = mock(HttpSession.class);
    }
    @Test
    public void createUserTest(){
        assertEquals("user/create", userController.createUser());
    }
    @Test
    public void createUserPostTest(){
        when(errorService.pleaseLogIn()).thenReturn("?error=Please Log In");

        assertEquals("redirect:/?error=Please Log In", userController.createUserPost(request));
        verify(userService).saveUser(request);
    }
    @Test
    public void updateUserIfSessionNotNull(){
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(mock(User.class));

        assertEquals("user/update", userController.updateUser(request, model));
        verify(model).addAttribute("user", session.getAttribute("user"));
    }
    @Test
    public void updateUserIfSessionIsNull(){
        when(request.getSession(false)).thenReturn(null);
        when(errorService.pleaseLogIn()).thenReturn("?error=Please Log In");

        assertEquals("redirect:/?error=Please Log In", userController.updateUser(request, model));
    }
    @Test
    public void updateUserPostIfSessionNotNull(){
        when(request.getSession(false)).thenReturn(session);

        assertEquals("redirect:/user/account", userController.updateUserPost(request));
        verify(userService).updateUser(request);
    }
    @Test
    public void updateUserPostIfSessionIsNull(){
        when(request.getSession(false)).thenReturn(null);
        when(errorService.pleaseLogIn()).thenReturn("?error=Please Log In");

        assertEquals("redirect:/?error=Please Log In", userController.updateUserPost(request));
    }
    @Test
    public void accountIfSessionNotNull(){
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(mock(User.class));

        assertEquals("user/account", userController.account(request, model));
        verify(model).addAttribute("user", session.getAttribute("user"));
    }
    @Test
    public void accountIfSessionIsNull(){
        when(request.getSession(false)).thenReturn(null);
        when(errorService.pleaseLogIn()).thenReturn("?error=Please Log In");

        assertEquals("redirect:/?error=Please Log In", userController.account(request, model));
    }
    @Test
    public void logInTest(){
        assertEquals("user/log-in", userController.logIn());
    }
    @Test
    public void logInPostIfUserExist(){
        when(userService.logIn(request)).thenReturn(true);

        assertEquals("redirect:/user/account", userController.logInPost(request));
    }
    @Test
    public void logInPostIfUserNotExist(){
        when(userService.logIn(request)).thenReturn(false);
        when(errorService.noSuchUser()).thenReturn("?error=No Such User");

        assertEquals("redirect:/?error=No Such User", userController.logInPost(request));
    }
    @Test
    public void logOutTest(){
        when(request.getSession(false)).thenReturn(session);

        assertEquals("redirect:/", userController.logOut(request));
        verify(session).invalidate();

    }

}




























