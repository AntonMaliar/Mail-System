package anton.maliar.email.service;

import anton.maliar.email.repository.LetterRepository;
import anton.maliar.email.repository.UserRepository;
import anton.maliar.email.repository.model.Letter;
import anton.maliar.email.repository.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LetterServiceTest {
    private LetterService letterService;
    private UserRepository userRepository;
    private LetterRepository letterRepository;
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    public void init(){
        userRepository = mock(UserRepository.class);
        letterRepository = mock(LetterRepository.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        letterService = new LetterService(userRepository, letterRepository);
    }
    @Test
    public void getIncomingLettersTest(){
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);

        Letter letter1 = mock(Letter.class);
        when(letter1.getStatus()).thenReturn("S");
        Letter letter2 = mock(Letter.class);
        when(letter2.getStatus()).thenReturn("I");

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(letterRepository.getAllLetters(user.getId())).thenReturn(List.of(letter1,letter2));

        assertEquals(1, letterService.getIncomingLetters(request).size());
    }
    @Test
    public void getSentLettersTest(){
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);

        Letter letter1 = mock(Letter.class);
        when(letter1.getStatus()).thenReturn("S");
        Letter letter2 = mock(Letter.class);
        when(letter2.getStatus()).thenReturn("I");

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(letterRepository.getAllLetters(user.getId())).thenReturn(List.of(letter1,letter2));

        assertEquals(1, letterService.getSentLetters(request).size());
    }
    @Test
    public void getReadLettersTest(){
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);

        Letter letter1 = mock(Letter.class);
        when(letter1.getStatus()).thenReturn("R");
        Letter letter2 = mock(Letter.class);
        when(letter2.getStatus()).thenReturn("I");

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(letterRepository.getAllLetters(user.getId())).thenReturn(List.of(letter1,letter2));

        assertEquals(1, letterService.getReadLetters(request).size());
    }
    @Test
    public void getLetterIfStatusLetterEqual_I(){
        Letter letter = new Letter();
        letter.setStatus("I");

        when(letterRepository.getReferenceById(1L)).thenReturn(letter);

        assertEquals("R", letterService.getLetter(1L).getStatus());
    }
    @Test
    public void getLetterIfStatusLetterEqual_S(){
        Letter letter = new Letter();
        letter.setStatus("S");

        when(letterRepository.getReferenceById(1L)).thenReturn(letter);

        assertNotEquals("R", letterService.getLetter(1L).getStatus());
    }
}
