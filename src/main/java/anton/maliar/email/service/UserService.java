package anton.maliar.email.service;

import anton.maliar.email.repository.UserRepository;
import anton.maliar.email.repository.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void saveUser(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        String addressMail = request.getParameter("address_mail");

        userRepository.save(new User(name, surname, password, addressMail));
    }

    public void updateUser(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        String addressMail = request.getParameter("address_mail");

        User user = (User) request.getSession(false).getAttribute("user");

        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);
        user.setAddressMail(addressMail);

        userRepository.save(user);
    }

    public boolean logIn(HttpServletRequest request) {
        String addressMail = request.getParameter("address_mail");
        String password = request.getParameter("password");
        User user = userRepository.findByField(addressMail, password);

        if(user != null){
            request.getSession(true).setAttribute("user", user);
            return true;
        }
        return false;
    }
}
