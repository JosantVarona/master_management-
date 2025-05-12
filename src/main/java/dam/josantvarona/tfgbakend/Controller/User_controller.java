package dam.josantvarona.tfgbakend.Controller;

import dam.josantvarona.tfgbakend.Model.User;
import dam.josantvarona.tfgbakend.Services.User_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class User_controller {
    @Autowired
    private User_service userService;

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
}
