package anton.maliar.email.endpoints;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MainControllerTest {
    private MainController mainController;
    private HttpServletRequest request;
    private Model model;

    @BeforeEach
    public void init(){
        mainController = new MainController();
        request = mock(HttpServletRequest.class);
        model = mock(Model.class);
    }
    @Test
    public void mainPageIfRequestHasParameter(){
        when(request.getParameter("error")).thenReturn("some text of error");

        assertEquals("main-page", mainController.mainPage(request, model));
        verify(model).addAttribute("error", request.getParameter("error"));
    }
    @Test
    public void mainPageIfRequestHasNotParameter(){
        when(request.getParameter("error")).thenReturn(null);

        assertEquals("main-page", mainController.mainPage(request, model));
        verify(model, never()).addAttribute("error", request.getParameter("error"));
    }
}
