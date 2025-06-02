package dam.josantvarona.tfgbakend.Controller;

import dam.josantvarona.tfgbakend.Model.User;
import dam.josantvarona.tfgbakend.Services.User_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class User_controller {
    @Autowired
    private User_service userService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<User>> getAllClient() {
        List<User> users = userService.findAll();
        return new ResponseEntity<List<User>>(users, new HttpHeaders(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/insert")
    public ResponseEntity<User> insert(@RequestBody User user) {
        User inserUser = userService.InsertUser(user);
        return new ResponseEntity<>(inserUser, new HttpHeaders(), HttpStatus.OK);
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
    @CrossOrigin
    @PutMapping("/state_user/{id}/{state}")
    public ResponseEntity<Map<String, Object>> disableUser(@PathVariable Integer id, @PathVariable String state) {
        userService.disableUser(id, state);
        Map<String, Object>response = new HashMap<>();
        if (state == "Habilitado") {
            response.put("Master Manager", "Usuario Habilitado");
        }
        if (state == "Desabilitado") {
            response.put("Master Manager", "Usuario Desabilitado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
