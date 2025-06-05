package dam.josantvarona.tfgbakend.Repositories;

import dam.josantvarona.tfgbakend.Model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Activity_repository extends JpaRepository<Activity, Integer> {
    @Query("SELECT c.name, ce.location, ce.address, u.name, " +
            "a.name, a.fecha_acti, a.type, a.specifics, a.picture " +
            "FROM Activity a " +
            "JOIN a.idCenter ce " +
            "JOIN ce.idClient c " +
            "JOIN a.idUser u " +
            "WHERE a.id = :activityId")
    Object allinfoActivity(@Param("activityId") Integer activityId);
    @Query("SELECT COUNT(a) > 0 FROM Activity a WHERE a.idCenter.id = :center AND a.name = :nameActi")
    boolean existeActividad(@Param("center") Integer center, @Param("nameActi") String nameActi);



}
