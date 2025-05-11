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
    @DeleteMapping("delete/{id}")
    public HttpStatus deleteActivity(@PathVariable Integer id) {
        activityService.deleteActivity(id);
        return HttpStatus.ACCEPTED;
    }
}
