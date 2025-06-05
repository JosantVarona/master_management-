package dam.josantvarona.tfgbakend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonBackReference(value = "center-activity")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_center", nullable = false)
    private dam.josantvarona.tfgbakend.Model.Center idCenter;

    @JsonBackReference(value = "user-activity")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private User idUser;


    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 100)
    @NotNull
    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Size(max = 255)
    @Column(name = "specifics")
    private String specifics;

    @Size(max = 1000000, message = "el tamaño debe estar entre 0 y 1000000")
    @Column(columnDefinition = "TEXT")
    private String picture;

    @NotNull
    @Column(name = "fecha_acti", nullable = false)
    private LocalDate fecha_acti;

    @NotNull
    @Column(name = "archive", nullable = false)
    private Integer archive;

    @Column(name = "state")
    private String state;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public dam.josantvarona.tfgbakend.Model.Center getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(dam.josantvarona.tfgbakend.Model.Center idCenter) {
        this.idCenter = idCenter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecifics() {
        return specifics;
    }

    public void setSpecifics(String specifics) {
        this.specifics = specifics;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public LocalDate getFecha_acti() {
        return fecha_acti;
    }

    public void setFecha_acti(LocalDate fechaActi) {
        this.fecha_acti = fechaActi;
    }

    public Integer getArchive() {
        return archive;
    }

    public void setArchive(Integer archive) {
        this.archive = archive;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }
}