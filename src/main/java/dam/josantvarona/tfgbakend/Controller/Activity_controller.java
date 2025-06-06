package dam.josantvarona.tfgbakend.Controller;

import dam.josantvarona.tfgbakend.Model.Activity;
import dam.josantvarona.tfgbakend.Services.Activity_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("activity")
public class Activity_controller {
    @Autowired
    private Activity_service activityService;


    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable int id) {
        Activity activi = activityService.getActivityid(id);
        return new ResponseEntity<>(activi, new HttpHeaders(), HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/update_activity/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable int id, @RequestBody Activity activity) {
        Activity updatedActivity = activityService.updateActivity(id, activity);
        return ResponseEntity.status(HttpStatus.OK).body(updatedActivity);
    }
    @CrossOrigin
    @PutMapping("archive/{id}/{archive}")
    public ResponseEntity<Map<String, Object>> archiveActivity(@PathVariable Integer id, @PathVariable Integer archive ) {
        activityService.archiveActivity(id, archive);
        Map<String, Object> response = new HashMap<>();
        if (archive == 1) {
            response.put("Master Manager", "La actividad se archivó");
        }
        if (archive == 0) {
            response.put("Master Manager", "La actividad ya no esta archivada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @CrossOrigin
    @PutMapping("complit/{id}")
    public ResponseEntity<Activity> complitActivity(@PathVariable Integer id, @RequestBody Activity activity) {
        Activity updatedActivity = activityService.compliteActivity(id, activity);
        return ResponseEntity.status(HttpStatus.OK).body(updatedActivity);
    }

    @CrossOrigin
    @PutMapping("/update_state/{id}/{state}")
    public ResponseEntity<Map<String, Object>> updateState(@PathVariable Integer id, @PathVariable String state ) {
        activityService.stateActivity(id, state);
        Map<String, Object> response = new HashMap<>();
        response.put("Master Manager", "Estado de la actividad se a actualizado");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @CrossOrigin
    @DeleteMapping("delete/{id}")
    public HttpStatus deleteActivity(@PathVariable Integer id) {
        activityService.deleteActivity(id);
        return HttpStatus.ACCEPTED;
    }
    @CrossOrigin
    @GetMapping("/info_activity/{id}")
    public ResponseEntity<Object> getInfoActivity(@PathVariable Integer id) {
        Object object = activityService.allInfoActivities(id);
        return new ResponseEntity<>(object, new HttpHeaders(), HttpStatus.OK);
    }
    @CrossOrigin
    @PutMapping("/reassign/{id_acti}/{id_user}")
    public ResponseEntity<Map<String, Object>> reassing(@PathVariable Integer id_acti, @PathVariable Integer id_user ) {
        activityService.reassign(id_acti, id_user);
        Map<String, Object> response = new HashMap<>();
        response.put("Master Manager", "Se ha podido reassignar la actividad");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
