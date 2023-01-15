package anton.maliar.email.service;

import anton.maliar.email.repository.LetterRepository;
import anton.maliar.email.repository.UserRepository;
import anton.maliar.email.repository.model.Letter;
import anton.maliar.email.repository.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class LetterService {
    private UserRepository userRepository;
    private LetterRepository letterRepository;

    public LetterService(UserRepository userRepository, LetterRepository letterRepository){
        this.userRepository = userRepository;
        this.letterRepository = letterRepository;
    }

    public void sendLetter(HttpServletRequest request) {
        String addressMailReceiver = request.getParameter("address_mail");
        String bodyMessage = request.getParameter("body_message");

        User receiver = userRepository.findByAddressMail(addressMailReceiver);
        User sender = (User)request.getSession(false).getAttribute("user");

        Letter letterReceiver = new Letter(
                bodyMessage,
                LocalDate.now(),
                LocalTime.now(),
                "I",
                sender,
                receiver,
                receiver
        );
        Letter letterSender = new Letter(
                bodyMessage,
                LocalDate.now(),
                LocalTime.now(),
                "S",
                sender,
                receiver,
                sender
        );

        letterRepository.save(letterSender);
        letterRepository.save(letterReceiver);
    }

    public List<Letter> getIncomingLetters(HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute("user");
        List<Letter> allLetters = letterRepository.getAllLetters(user.getId());

        List<Letter> incomingLetters = allLetters.stream()
                .filter(letter -> letter.getStatus().equals("I"))
                .toList();

        return incomingLetters;
    }

    public List<Letter> getSentLetters(HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute("user");
        List<Letter> allLetters = letterRepository.getAllLetters(user.getId());

        List<Letter> sentLetters = allLetters.stream()
                .filter(letter -> letter.getStatus().equals("S"))
                .toList();

        return sentLetters;
    }

    public List<Letter> getReadLetters(HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute("user");
        List<Letter> allLetters = letterRepository.getAllLetters(user.getId());

        List<Letter> readLetters = allLetters.stream()
                .filter(letter -> letter.getStatus().equals("R"))
                .toList();

        return readLetters;
    }

    public Letter getLetter(Long letterId) {
        Letter letter = letterRepository.getReferenceById(letterId);

        if(letter.getStatus().equals("I")){
            letter.setStatus("R");
            letterRepository.save(letter);
            return letter;
        }
        return letter;
    }

    public void deleteLetter(Long letterId) {
        letterRepository.deleteById(letterId);
    }
}
