package dam.josantvarona.tfgbakend.Services;

import dam.josantvarona.tfgbakend.Excepcions.RecordNotFoundException;
import dam.josantvarona.tfgbakend.Model.Activity;
import dam.josantvarona.tfgbakend.Model.Center;
import dam.josantvarona.tfgbakend.Model.User;
import dam.josantvarona.tfgbakend.Repositories.Activity_repository;
import dam.josantvarona.tfgbakend.Repositories.Center_repository;
import dam.josantvarona.tfgbakend.Repositories.User_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class Activity_service {
    @Autowired
    private Activity_repository repository;

    @Autowired
    private Center_repository centerRepository;

    @Autowired
    private User_repository userRepo;

    //Metodo para insertar Actividades en el centro seleccionado
    public Activity insertActivity(Integer center, Integer id_usuario, Activity activity) {
        Optional<User> userDB = userRepo.findById(id_usuario);
        Optional<Center> centerBD = centerRepository.findById(center);
        if (centerBD.isPresent() && userDB.isPresent()) {
            if (repository.existeActividad(center, activity.getName())){
                throw new RecordNotFoundException("Ya existe esta actividad en el centro ", 0);
            }
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
            if (activity.getName().equals(newactivity.getName())) {
                activity.setName(newactivity.getName());
                activity.setType(newactivity.getType());
                activity = repository.save(activity);
                return activity;
            }else {
                if (repository.existeActividad(activity.getIdCenter().getId(), newactivity.getName())){
                    throw new RecordNotFoundException("Ya existe esta actividad en el centro ", 0);
                }else {
                    activity.setName(newactivity.getName());
                    activity.setType(newactivity.getType());
                    activity = repository.save(activity);
                    return activity;
                }
            }

        } else {
            throw new RecordNotFoundException("Error al actualizar la actividad",id);
        }
    }
    // Metodo para actualizar los camos de especificacion y imagenes
    public Activity compliteActivity(Integer id, Activity activity) {
        if (id != null && activity != null ) {
            Optional<Activity> activityBD = repository.findById(id);
            if (activityBD.isPresent()) {
                Activity activity1 = activityBD.get();
                activity1.setSpecifics(activity.getSpecifics());
                activity1.setPicture(activity.getPicture());
                activity1 = repository.save(activity1);
                return activity1;
            }else {
                throw new RecordNotFoundException("No se hemos encontrado el actividad",id);
            }
        } else {
            throw new RecordNotFoundException("No hay datos del actividad",id);
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
    // Metodo para poner el estado de la actividad
    public Activity stateActivity(Integer id, String state) {
        if (id != null && state != null) {
            Optional<Activity> activityBD = repository.findById(id);
            if (activityBD.isPresent()) {
                Activity activity = activityBD.get();
                activity.setState(state);
                activity = repository.save(activity);
                return activity;
            }else {
                throw new RecordNotFoundException("No tenemos registro de la actividad",id);
            }
        }else {
            throw new RecordNotFoundException("No tenemos registro de la actividad",id);
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

    public Map<String, Object> allInfoActivities(Integer id) {
        if (id != null) {
            Optional<Activity> activityBD = repository.findById(id);
            if (activityBD.isPresent()) {
                Object[] infoActivity = (Object[]) repository.allinfoActivity(id);
                Map<String, Object> mappedResult = new LinkedHashMap<>();
                mappedResult.put("nombre_cliente", infoActivity[0]);
                mappedResult.put("ubicacion", infoActivity[1]);
                mappedResult.put("direccion", infoActivity[2]);
                mappedResult.put("nombre_usuario", infoActivity[3]);
                mappedResult.put("nombre_actividad", infoActivity[4]);
                mappedResult.put("fecha_actividad", infoActivity[5]);
                mappedResult.put("tipo", infoActivity[6]);
                mappedResult.put("especificaciones", infoActivity[7]);
                mappedResult.put("imagen", infoActivity[8]);

                return mappedResult;
            } else {
                throw new RecordNotFoundException("No hemos podido encontrar la actividad", id);
            }
        } else {
            throw new RecordNotFoundException("No hemos podido encontrar la actividad", id);
        }
    }
    public Activity reassign(Integer id_activity, Integer id_user) {
        if (id_activity != null && id_user != null) {
            Optional<Activity> activityBD = repository.findById(id_activity);
            Optional<User> userDB = userRepo.findById(id_user);
            if (activityBD.isPresent() && userDB.isPresent()) {
                Activity activity = activityBD.get();
                User user = userDB.get();
                activity.setIdUser(user);
                activity = repository.save(activity);
                return activity;
            }else {
                throw new RecordNotFoundException("No se ha podido asignar la actividad",id_activity);
            }
        }else {
            throw new RecordNotFoundException("No se ha podido asignar la actividad",id_activity);
        }

    }

}
