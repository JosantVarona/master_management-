package dam.josantvarona.tfgbakend.Services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dam.josantvarona.tfgbakend.Excepcions.RecordNotFoundException;
import dam.josantvarona.tfgbakend.Model.Activity;
import dam.josantvarona.tfgbakend.Model.User;
import dam.josantvarona.tfgbakend.Repositories.User_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class User_service {
    @Autowired
    private User_repository userRepo;
    @Autowired
    private JavaMailSender mailSender;

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
                return result = userRepo.save(user);
            }else {
                throw new RecordNotFoundException("Usuario ya exitente", 0);
            }
        } else {
            throw new RecordNotFoundException("Error al insertar usuario", 0);
        }

    }
    // Metodo para obtener el usuario por email
    public User userbyEmail(String email) {
        if (email != null && !email.isEmpty()) {
            return userRepo.findByEmail(email);
        }else {
            throw new RecordNotFoundException("No se encotrado ningun usuario", email);
        }
    }
    // Metodo para enviar un mensaje al correo
    public void enviarEmail(String email) {
        if (email != null && !email.isEmpty()) {
            User userBD = userRepo.findByEmail(email);
            if (userBD != null) {
                userBD.setPass("dobleM");
                userRepo.save(userBD);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject("Contraseña recuperada");
                message.setText("La contraseña es la siguiente: dobleM (Recomenados cambiar la contraseña)");
                mailSender.send(message);
            }else {
                throw new RecordNotFoundException("No se encotrado ningun usuario con ese correo", email);
            }
        }else {
            throw new RecordNotFoundException("No se encotrado ningun usuario", 0);
        }
    }

    // Actualiza el usuario
    public User updateUser (Integer id, User user) {
        if (id != null && user != null) {
            Optional<User> userBD = userRepo.findById(id);
            if (userBD.isPresent()) {

                User usernew = userBD.get();
                System.out.println("Usuario encontrado: " + usernew.getEmail() + " el que envia " + user.getEmail());
                if (usernew.getEmail().equals(user.getEmail())) {
                    usernew.setDni(user.getDni());
                    usernew.setName(user.getName());
                    usernew.setLevel(user.getLevel());
                    usernew.setTelephone(user.getTelephone());
                    usernew.setPass(user.getPass());
                    userRepo.save(usernew);
                    return usernew;
                }else {
                    User aux = userRepo.findByEmail(user.getEmail());
                    if (aux == null) {
                        usernew.setEmail(user.getEmail());
                        usernew.setDni(user.getDni());
                        usernew.setName(user.getName());
                        usernew.setLevel(user.getLevel());
                        usernew.setTelephone(user.getTelephone());
                        usernew.setPass(user.getPass());
                        userRepo.save(usernew);
                        return usernew;

                    }else {
                        throw new RecordNotFoundException("Correo ya exitente", id);
                    }
                }
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
            userLazy.setPass(user.getPass());
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
