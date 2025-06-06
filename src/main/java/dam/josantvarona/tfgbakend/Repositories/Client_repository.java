package dam.josantvarona.tfgbakend.Repositories;

import dam.josantvarona.tfgbakend.Model.Client;
import dam.josantvarona.tfgbakend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Client_repository extends JpaRepository<Client, Integer> {
    @Query(
            value = "SELECT * FROM clients WHERE archive = 0",
            nativeQuery = true
    )
    List<Client> listlevel();
}
