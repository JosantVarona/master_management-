package dam.josantvarona.tfgbakend.Services;

import dam.josantvarona.tfgbakend.Model.User;
import dam.josantvarona.tfgbakend.Repositories.User_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class User_service {
    @Autowired
    private User_repository userRepo;

    public User InsertUser (User user) {
        User result = null;
        if (user != null) {
            User aux = userRepo.findByEmail(user.getEmail());
            if (aux == null) { // No ha encontrado un usuario con ese correo
               List<Integer> users = userRepo.getUserBD();
               if (users.size() < 2) { // Le damos el nivel de usuario en este caso nivel admi
                   user.setLevel(3);
               }
                result = userRepo.save(user);
            }
        }
        return result;
    }
}
