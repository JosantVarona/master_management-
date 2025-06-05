package dam.josantvarona.tfgbakend.Repositories;

import dam.josantvarona.tfgbakend.Model.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Center_repository extends JpaRepository<Center, Integer> {
    @Query("SELECT COUNT(*) > 0 FROM Center WHERE idClient.id = :client AND address = :address")
    boolean existeDireccion(@Param("client") Integer client, @Param("address") String address);
}
