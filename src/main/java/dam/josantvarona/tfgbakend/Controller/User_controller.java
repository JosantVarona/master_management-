package dam.josantvarona.tfgbakend.Controller;

import dam.josantvarona.tfgbakend.Model.User;
import dam.josantvarona.tfgbakend.Services.User_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class User_controller {
    @Autowired
    private User_service userService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<User>> getAllClient() {
        List<User> clients = userService.findAll();
        return new ResponseEntity<List<User>>(clients, new HttpHeaders(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody User user) {
        User inserUser = userService.InsertUser(user);
        if (inserUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Se a insertado correctamente");
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body("No se a insertado correctamente");
        }
    }
    @CrossOrigin
    @PutMapping("/update_user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id ,@RequestBody User user) {
        User updateUser = userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }
    @CrossOrigin
    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return HttpStatus.ACCEPTED;
    }
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<User> getClient(@PathVariable Integer id) {
        User user = userService.getClientbyId(id);
        return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping("findemail/{email}")
    public ResponseEntity<User> getClient(@PathVariable String email) {
        User user = userService.userbyEmail(email);
        return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
    }
}
