package anton.maliar.email.repository;

import anton.maliar.email.repository.model.Letter;
import anton.maliar.email.repository.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LetterRepositoryTest {
    private LetterRepository letterRepository;
    private UserRepository userRepository;
    private Letter letter1;
    private Letter letter2;
    private User sender;
    private User receiver;

    @Autowired
    public LetterRepositoryTest(LetterRepository letterRepository, UserRepository userRepository){
        this.letterRepository = letterRepository;
        this.userRepository = userRepository;

        sender = new User("some name", "some surname", "some password", "some mail");
        receiver = new User("some name1", "some surname1", "some password1", "some mail1");
        letter1 = new Letter("some body", LocalDate.now(), LocalTime.now(), "S", sender, receiver, sender);
        letter2 = new Letter("some body", LocalDate.now(), LocalTime.now(), "S", sender, receiver, sender);
    }

    @BeforeEach
    public void init(){
        userRepository.save(sender);
        userRepository.save(receiver);
        letterRepository.save(letter1);
        letterRepository.save(letter2);
    }
    @AfterEach
    public void destroy(){
        letterRepository.delete(letter1);
        letterRepository.delete(letter2);
        userRepository.delete(sender);
        userRepository.delete(receiver);
    }
    @Test
    public void getAllLettersIfSomeLettersExist(){
        assertEquals(2, letterRepository.getAllLetters(sender.getId()).size());
    }

}
