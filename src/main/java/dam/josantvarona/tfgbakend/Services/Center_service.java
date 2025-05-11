package dam.josantvarona.tfgbakend.Services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dam.josantvarona.tfgbakend.Excepcions.RecordNotFoundException;
import dam.josantvarona.tfgbakend.Model.Activity;
import dam.josantvarona.tfgbakend.Model.Center;
import dam.josantvarona.tfgbakend.Model.Client;
import dam.josantvarona.tfgbakend.Repositories.Center_repository;
import dam.josantvarona.tfgbakend.Repositories.Client_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Center_service {
    @Autowired
    private Center_repository repo;
    @Autowired
    private Client_repository clientRepo;

    // Inserta el centro del cliente
    public Center insertCenter(Integer id, Center newCenter) {
        Optional<Client> client = clientRepo.findById(id);
        if (client.isPresent()) {
            Center newCenterDB = new Center();

            newCenterDB.setLocation(newCenter.getLocation());
            newCenterDB.setZipCode(newCenter.getZipCode());
            newCenterDB.setAddress(newCenter.getAddress());
            newCenterDB.setTelephone(newCenter.getTelephone());
            newCenterDB.setIdClient(client.get());
            newCenterDB.setArchive(0);
            return repo.save(newCenterDB);
        }else {
            throw new RecordNotFoundException("No se encontro el cliente",id);
        }
    }
    //Actualiza los datos del centro
    public Center updateCenter(Integer id, Center newCenter) {
        if (id == null) throw new RuntimeException("No se encontro el id del cliente");
        Optional<Center> center = repo.findById(id);
        if (center.isPresent()) {
            Center newCenterDB = center.get();
            newCenterDB.setLocation(newCenter.getLocation());
            newCenterDB.setZipCode(newCenter.getZipCode());
            newCenterDB.setAddress(newCenter.getAddress());
            newCenterDB.setTelephone(newCenter.getTelephone());
            newCenterDB = repo.save(newCenterDB);
            return newCenterDB;
        }else {
            throw new RuntimeException("No siste el centro"+ id);
        }
    }
    public Center getcenterbyid(Integer id) {
        Optional<Center> center = repo.findById(id);
        if (center.isPresent()) {
            Center centerDB = center.get();
            CenterLazy centerLazy = new CenterLazy();
            centerLazy.setId(centerDB.getId());
            centerLazy.setIdClient(centerDB.getIdClient());
            centerLazy.setLocation(centerDB.getLocation());
            centerLazy.setZipCode(centerDB.getZipCode());
            centerLazy.setAddress(centerDB.getAddress());
            centerLazy.setTelephone(centerDB.getTelephone());
            centerLazy.setActivities(centerDB.getActivities());
            centerLazy.setArchive(centerDB.getArchive());
            return centerLazy;
        }else {
            throw new RuntimeException("No siste el centro"+ id);
        }
    }
    // Metodo para archivar centro del cliente
    public Center archiveCenter(Integer id, Integer archive) {
        if (id == null) throw new RuntimeException("No se encontro el centro");
        Optional<Center> center = repo.findById(id);
        if (center.isPresent()) {
            Center newCenterDB = center.get();
            newCenterDB.setArchive(archive);
            newCenterDB = repo.save(newCenterDB);
            return newCenterDB;
        }else {
            throw new RuntimeException("No siste el centro"+ id);
        }
    }
    // Metodo para borrar el centro del cliente
    public void deleteCenter( Integer id ) {
        Optional<Center> center = repo.findById(id);
        if ( center.isPresent() ) {
            repo.delete( center.get() );
        }else {
            throw new RecordNotFoundException("No se ha podido borrar el centro ", id);
        }
    }
}
class CenterLazy extends Center {
    @JsonProperty("activity")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Override
    public List<Activity> getActivities() {
        return super.getActivities();
    }
}
