package anton.maliar.email.service;

import anton.maliar.email.repository.UserRepository;
import anton.maliar.email.repository.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    public void init(){
        userRepository = mock(UserRepository.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        userService = new UserService(userRepository);
    }
    @Test
    public void logInIfUserExist(){
        when(request.getParameter("address_mail")).thenReturn("some mail");
        when(request.getParameter("password")).thenReturn("some password");
        when(request.getSession(true)).thenReturn(session);
        when(userRepository.findByField("some mail", "some password"))
                .thenReturn(mock(User.class));

        assertTrue(userService.logIn(request));
    }
    @Test
    public void logInIfUserNotExist(){
        when(request.getParameter("address_mail")).thenReturn("some mail");
        when(request.getParameter("password")).thenReturn("some password");
        when(request.getSession(true)).thenReturn(session);
        when(userRepository.findByField("some mail", "some password"))
                .thenReturn(null);

        assertFalse(userService.logIn(request));
    }
}
