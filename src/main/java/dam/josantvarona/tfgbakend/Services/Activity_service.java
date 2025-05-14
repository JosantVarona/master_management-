package dam.josantvarona.tfgbakend.Services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dam.josantvarona.tfgbakend.Excepcions.RecordNotFoundException;
import dam.josantvarona.tfgbakend.Model.Activity;
import dam.josantvarona.tfgbakend.Model.Center;
import dam.josantvarona.tfgbakend.Model.User;
import dam.josantvarona.tfgbakend.Repositories.Activity_repository;
import dam.josantvarona.tfgbakend.Repositories.Center_repository;
import dam.josantvarona.tfgbakend.Repositories.User_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Activity_service {
    @Autowired
    private Activity_repository repository;

    @Autowired
    private Center_repository centerRepository;

    @Autowired
    private User_repository userRepo;

    //Metodo para insertar Actividades en el centro seleccionado
    public Activity insertActivity(Integer center,Activity activity) {
        Optional<User> userDB = userRepo.findById(activity.getIdUser().getId());
        Optional<Center> centerBD = centerRepository.findById(center);
        if (centerBD.isPresent() && userDB.isPresent()) {
            Activity newActivity = new Activity();
            newActivity = activity;
            newActivity.setArchive(0);
            newActivity.setState("Pendiente");
            newActivity.setIdCenter(centerBD.get());
            newActivity.setIdUser(userDB.get());
            newActivity = repository.save(newActivity);
            return newActivity;
        }else {
            throw new RecordNotFoundException("No se hemos encontrado el centro para añadir la actividad",center);
        }
    }
    // Actualiza los campos de la acticidad
    public Activity updateActivity(Integer id, Activity newactivity) {
        if (id == null) throw new RecordNotFoundException("No se hemos encontrado infomracion del centro",id);
        Optional<Activity> activityBD = repository.findById(id);
        if (activityBD.isPresent()) {
            Activity activity = activityBD.get();
            activity.setName(newactivity.getName());
            activity.setFecha_acti(newactivity.getFecha_acti());
            activity.setType(newactivity.getType());
            activity.setSpecifics(newactivity.getSpecifics());
            activity.setPicture(newactivity.getPicture());
            activity.setState(newactivity.getState());
            activity = repository.save(activity);
            return activity;
        } else {
            throw new RecordNotFoundException("Error al actualizar la actividad",id);
        }
    }
    // Archiva la actividad
    public Activity archiveActivity(Integer id, Integer archive) {
        if (id != null && archive != null) {
            Optional<Activity> activityBD = repository.findById(id);
            if (activityBD.isPresent()) {
                Activity activity = activityBD.get();
                activity.setArchive(archive);
                activity = repository.save(activity);
                return activity;
            }else {
                throw new RecordNotFoundException("No se a podido archivar la actividad",id);
            }
        }else {
            throw new RecordNotFoundException("No se hemos encontrado informacion del centro",id);
        }
    }

    // Busca actividad del centro por id de actividad
    public Activity getActivityid(Integer id) {
        if (id == null) throw new RecordNotFoundException("No pudimos buscar informacion de la actividad",id);
        Optional<Activity> activityBD = repository.findById(id);
        if (activityBD.isPresent()) {
            Activity activity = activityBD.get();

            return activity;
        }else {
            throw new RecordNotFoundException("No se hemos encontrado informacion de la actividad",id);
        }
    }
    // Elimina la actividad
    public void deleteActivity(Integer id) {
        Optional<Activity> activityBD = repository.findById(id);
        if (activityBD.isPresent()) {
            repository.delete(activityBD.get());
        }else {
            throw new RecordNotFoundException("No hemos podido eliminar la actividad",id);
        }
    }
}
