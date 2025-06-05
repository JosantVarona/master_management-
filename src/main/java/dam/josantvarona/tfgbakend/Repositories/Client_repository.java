package dam.josantvarona.tfgbakend.Repositories;

import dam.josantvarona.tfgbakend.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Client_repository extends JpaRepository<Client, Integer> {

}
