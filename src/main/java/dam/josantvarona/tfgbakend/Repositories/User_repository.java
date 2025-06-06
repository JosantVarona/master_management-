package dam.josantvarona.tfgbakend.Repositories;

import dam.josantvarona.tfgbakend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface User_repository extends JpaRepository<User, Integer> {
    @Query(
            value = "SELECT * FROM users WHERE email = ?",
            nativeQuery = true
    )
    User findByEmail(@Param("?") String email);
    @Query(
            value = "SELECT id FROM users LIMIT 2",
            nativeQuery = true
    )
    List<Integer> getUserBD();
}
