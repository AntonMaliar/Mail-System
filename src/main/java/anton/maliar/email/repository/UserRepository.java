package anton.maliar.email.repository;

import anton.maliar.email.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users " +
            "WHERE address_mail = :addressMail " +
            "AND password = :password", nativeQuery = true)
    User findByField(@Param("addressMail") String addressMail, @Param("password") String password);

    @Query(value = "SELECT * FROM users " +
            "WHERE address_mail = :addressMail", nativeQuery = true)
    User findByAddressMail(@Param("addressMail") String addressMailReceiver);
}