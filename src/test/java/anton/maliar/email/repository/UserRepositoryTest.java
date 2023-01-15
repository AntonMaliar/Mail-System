package anton.maliar.email.repository;

import anton.maliar.email.repository.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {
    private UserRepository userRepository;
    private User user;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository){
        this.userRepository = userRepository;
        user = new User("some name", "some surname", "some password", "some mail");
    }
    @BeforeEach
    public void init(){
        userRepository.save(user);
    }
    @AfterEach
    public void destroy(){
        userRepository.delete(user);
    }
    @Test
    public void findByFieldTest(){
        assertEquals(user.getId(), userRepository.findByField("some mail", "some password").getId());
    }
    @Test
    public void findByAddressMailTest(){
        assertEquals(user.getId(), userRepository.findByAddressMail("some mail").getId());
    }

}
