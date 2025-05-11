package dam.josantvarona.tfgbakend.Controller;

import dam.josantvarona.tfgbakend.Model.Center;
import dam.josantvarona.tfgbakend.Model.Client;
import dam.josantvarona.tfgbakend.Services.Center_service;
import dam.josantvarona.tfgbakend.Services.Client_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class Client_controller {
    @Autowired
    private Client_service clientService;
    @Autowired
    private Center_service centerService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        Client addclient = clientService.inserclient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(addclient);
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Client>> getAllClient() {
        List<Client> clients = clientService.getAllclient();
        return new ResponseEntity<List<Client>>(clients, new HttpHeaders(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/{id}/center")
    public ResponseEntity<Center> addCenter(@PathVariable int id, @RequestBody Center newCenter) {
        System.out.println(newCenter);
        Center addcenter = centerService.insertCenter(id, newCenter);
        return ResponseEntity.status(HttpStatus.CREATED).body(addcenter);
    }
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Integer id) {
        Client client = clientService.getclientbyid(id);
        return new ResponseEntity<>(client, new HttpHeaders(), HttpStatus.OK);
    }
    @CrossOrigin
    @PutMapping("/update_client/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer id, @RequestBody Client client) {
        Client updateclient = clientService.updateclient(id, client);
        return ResponseEntity.status(HttpStatus.OK).body(updateclient);
    }
    @CrossOrigin
    @PutMapping("/archive/{id}/{archive}")
    public ResponseEntity<Map<String, Object>> archiveClient(@PathVariable Integer id, @PathVariable Integer archive) {
        clientService.archiveClient(id, archive);
        Map<String, Object>response = new HashMap<>();
        if (archive == 1) {
            response.put("Master Manager", "El cliente se archivó");
        }
        if (archive == 0) {
            response.put("Master Manager", "El cliente ya no esta archivado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @CrossOrigin
    @DeleteMapping("delete/{id}")
    public HttpStatus deleteClient(@PathVariable Integer id) {
        clientService.deleteclient(id);
        return HttpStatus.ACCEPTED;
    }
}
