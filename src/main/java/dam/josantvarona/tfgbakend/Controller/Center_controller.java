package dam.josantvarona.tfgbakend.Controller;

import dam.josantvarona.tfgbakend.Model.Center;
import dam.josantvarona.tfgbakend.Services.Center_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("center")
public class Center_controller {
    @Autowired
    private Center_service center_service;

    @CrossOrigin
    @PutMapping("/update_center/{id}")
    public ResponseEntity<Center> updateCenter(@PathVariable Integer id, @RequestBody Center center ) {
        Center new_center = center_service.updateCenter(id, center);
        return ResponseEntity.status(HttpStatus.OK).body(new_center);
    }
    @CrossOrigin
    @PutMapping("archive/{id}/{archive}")
    public ResponseEntity<Map<String, Object>> archiveCenter(@PathVariable Integer id, @PathVariable Integer archive ) {
        center_service.archiveCenter(id, archive);
        Map<String, Object> response = new HashMap<>();
        if (archive == 1) {
            response.put("Master Manager", "El centro se archivó");
        }
        if (archive == 0) {
            response.put("Master Manager", "El centro ya no esta archivado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @CrossOrigin
    @DeleteMapping("delete/{id}")
    public HttpStatus deleteClient(@PathVariable Integer id) {
        center_service.deleteCenter(id);
        return HttpStatus.ACCEPTED;
    }
}
