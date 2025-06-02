package dam.josantvarona.tfgbakend.Services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dam.josantvarona.tfgbakend.Excepcions.RecordNotFoundException;
import dam.josantvarona.tfgbakend.Model.Activity;
import dam.josantvarona.tfgbakend.Model.User;
import dam.josantvarona.tfgbakend.Repositories.User_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class User_service {
    @Autowired
    private User_repository userRepo;

    public List<User> findAll() {
        List<User> users = userRepo.findAll();
        if ( users.size() > 0 ) {
            return users;
        }else {
            return new ArrayList<User>();
        }
    }

    public User InsertUser (User user) {
        User result = null;
        if (user != null) {
            User aux = userRepo.findByEmail(user.getEmail());
            if (aux == null) { // No ha encontrado un usuario con ese correo
               List<Integer> users = userRepo.getUserBD();
               if (users.size() < 2) { // Le damos el nivel de usuario en este caso nivel admi
                   user.setLevel(3);
                   user.setState("Habilitado");
               }else {
                   user.setLevel(1);
                   user.setState("Desabilitado");
               }
                result = userRepo.save(user);
            }
        }
        return result;
    }
    // Metodo para obtener el usuario por email
    public User userbyEmail(String email) {
        if (email != null && !email.isEmpty()) {
            return userRepo.findByEmail(email);
        }else {
            throw new RecordNotFoundException("No se encotrado ningun usuario", email);
        }
    }

    // Actualiza el usuario
    public User updateUser (Integer id, User user) {
        if (id != null && user != null) {
            Optional<User> userBD = userRepo.findById(id);
            if (userBD.isPresent()) {
                User usernew = userBD.get();
                usernew.setEmail(user.getEmail());
                usernew.setDni(user.getDni());
                usernew.setName(user.getName());
                usernew.setLevel(user.getLevel());
                usernew.setTelephone(user.getTelephone());
                userRepo.save(usernew);
                return usernew;
            }else {
                throw new RecordNotFoundException("No se encontro al usuario", id);
            }
        }else {
            throw new RecordNotFoundException("No hemos podido encotrar al usuario", id);
        }
    }
    // Borra el usuario
    public void deleteUser (Integer id) {
        Optional<User> userBD = userRepo.findById(id);
        if (userBD.isPresent()) {
            userRepo.delete(userBD.get());
        }else {
            throw new RecordNotFoundException("No se encontro al usuario", id);
        }
    }
    public User getClientbyId(Integer id) {
        Optional<User> userBD = userRepo.findById(id);
        if (userBD.isPresent()) {
            User user = userBD.get();
            UserLazy userLazy = new UserLazy();
            userLazy.setId(user.getId());
            userLazy.setEmail(user.getEmail());
            userLazy.setName(user.getName());
            userLazy.setDni(user.getDni());
            userLazy.setLevel(user.getLevel());
            userLazy.setState(user.getState());
            userLazy.setTelephone(user.getTelephone());
            userLazy.setActivities(user.getActivities());
            return userLazy;
        } else {
            throw new RecordNotFoundException("No se encontro al usuario", id);
        }
    }
    public User disableUser (Integer id, String state) {
        if (id != null) {
            Optional<User> userBD = userRepo.findById(id);
            if (userBD.isPresent()) {
                User user = userBD.get();
                user.setState(state);
                userRepo.save(user);
                return user;
            }else {
                throw new RecordNotFoundException("No se encontro al usuario", id);
            }
        }else {
            throw new RecordNotFoundException("No se encontro al usuario", id);
        }
    }
}
class UserLazy extends User {

    @JsonProperty("activities")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Override
    public List<Activity> getActivities() {
        return super.getActivities();
    }
}
