package anton.maliar.email.service;

import org.springframework.stereotype.Service;

@Service
public class ErrorService {
    public String pleaseLogIn(){
        return "?error=Please Log In";
    }
    public String noSuchUser(){
        return "?error=No Such User";
    }
}
