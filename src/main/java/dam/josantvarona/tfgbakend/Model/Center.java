package dam.josantvarona.tfgbakend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "center")
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonBackReference
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_client", nullable = false)
    private dam.josantvarona.tfgbakend.Model.Client idClient;

    @Size(max = 100)
    @NotNull
    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Size(max = 100)
    @NotNull
    @Column(name = "zip_code", nullable = false, length = 100)
    private String zipCode;

    @Size(max = 255)
    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Size(max = 15)
    @NotNull
    @Column(name = "telephone", nullable = false, length = 15)
    private String telephone;


    @Column(name = "archive", nullable = false)
    private Integer archive;

    @JsonManagedReference(value = "center-activity")
    @OneToMany(mappedBy = "idCenter")
    private List<Activity> activities = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public dam.josantvarona.tfgbakend.Model.Client getIdClient() {
        return idClient;
    }

    public void setIdClient(dam.josantvarona.tfgbakend.Model.Client idClient) {
        this.idClient = idClient;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getArchive() {
        return archive;
    }

    public void setArchive(Integer archive) {
        this.archive = archive;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

}