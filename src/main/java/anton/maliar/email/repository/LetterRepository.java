package anton.maliar.email.repository;

import anton.maliar.email.repository.model.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    @Query(value = "SELECT * FROM letters " +
            "WHERE owner = :userId", nativeQuery = true)
    List<Letter> getAllLetters(@Param("userId")Long id);
}
