package dam.josantvarona.tfgbakend.Services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dam.josantvarona.tfgbakend.Excepcions.RecordNotFoundException;
import dam.josantvarona.tfgbakend.Model.Center;
import dam.josantvarona.tfgbakend.Model.Client;
import dam.josantvarona.tfgbakend.Repositories.Client_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Client_service {
    @Autowired
    private Client_repository client_repository;

    /**
     *  Metodo para insertar un cliente
     */
    public Client inserclient( Client newclient ) {
        Client client = null;
        if ( newclient != null ) {
            newclient.setArchive(0);
            client = client_repository.save( newclient );
        }
    return client;
    }
    /**
     * Metodo para obtener todos los clientes
     */
    public List<Client> getAllclient() {
        List<Client> clients = client_repository.findAll();
        if ( clients.size() > 0 ) {
            return clients;
        }else {
            return new ArrayList<Client>();
        }
    }

    /**
     * Obtiene los centros del cliente mas al informacion de este
     * @param id
     * @return
     */
    public Client getclientbyid( Integer id ) {
        Optional<Client> client = client_repository.findById(id);
        if ( client.isPresent() ) {
            Client clientBD = client.get();
            ClientLazy clientLazy = new ClientLazy();
            clientLazy.setId(clientBD.getId());
            clientLazy.setName(clientBD.getName());
            clientLazy.setEmail(clientBD.getEmail());
            clientLazy.setCif(clientBD.getCif());
            clientLazy.setArchive(clientBD.getArchive());
            clientLazy.setCenters(clientBD.getCenters());
            return clientLazy;
        }else {
            throw new RecordNotFoundException("No hay informacion del cliente", id);
        }
    }

    /**
     * Actualiza los datos del cliente
     * @param id
     * @param newclient
     * @return
     */
    public Client updateclient( Integer id, Client newclient ) {
        if (id != null) {
            Optional<Client> client = client_repository.findById(id);
            if ( client.isPresent() ) {
                Client clientBD = client.get();
                clientBD.setCif(newclient.getCif());
                clientBD.setName(newclient.getName());
                clientBD.setEmail(newclient.getEmail());
                clientBD = client_repository.save(clientBD);
                return clientBD;
            }else {
                throw new RecordNotFoundException("No podemos actualizar el cliente", id);
            }
        }else {
            throw new RecordNotFoundException("No hay informacion del cliente", id);
        }
    }

    /**
     * Archiva el cliente
     * @param id busca el cliente por id
     * @param archive archiva el cliente este solo sera visible por el usuario admin
     * @return
     */
    public Client archiveClient(Integer id,Integer archive) {
        if (id != null) {
            Optional<Client> client = client_repository.findById(id);
            if ( client.isPresent() ) {
                Client clientBD = client.get();
                clientBD.setArchive(archive);
                clientBD = client_repository.save(clientBD);
                return clientBD;
            }else {
                throw new RecordNotFoundException("No se a podido Archivar", id);
            }
        }else {
            throw new RecordNotFoundException("No hay informacion del cliente", id);
        }
    }
    // Borra el cliente
    public void deleteclient( Integer id ) {
        Optional<Client> client = client_repository.findById(id);
        if ( client.isPresent() ) {
            client_repository.delete( client.get() );
        }else {
            throw new RecordNotFoundException("No se ha podido borrar el cliente", id);
        }
    }
}
class ClientLazy extends Client{

    @JsonProperty("center")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Override
    public List<Center> getCenters(){
        return super.getCenters();
    }
}
