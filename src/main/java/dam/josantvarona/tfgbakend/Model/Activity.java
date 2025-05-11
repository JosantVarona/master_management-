package dam.josantvarona.tfgbakend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_center", nullable = false)
    private dam.josantvarona.tfgbakend.Model.Center idCenter;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 100)
    @NotNull
    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Size(max = 255)
    @NotNull
    @Column(name = "specifics", nullable = false)
    private String specifics;

    @Size(max = 300)
    @NotNull
    @Column(name = "picture", nullable = false, length = 300)
    private String picture;

    @NotNull
    @Column(name = "fecha_acti", nullable = false)
    private LocalDate fechaActi;

    @NotNull
    @Column(name = "archive", nullable = false)
    private Integer archive;

    @OneToMany(mappedBy = "idActividad")
    private Set<dam.josantvarona.tfgbakend.Model.User> users = new LinkedHashSet<>();

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

    public LocalDate getFechaActi() {
        return fechaActi;
    }

    public void setFechaActi(LocalDate fechaActi) {
        this.fechaActi = fechaActi;
    }

    public Integer getArchive() {
        return archive;
    }

    public void setArchive(Integer archive) {
        this.archive = archive;
    }

    public Set<dam.josantvarona.tfgbakend.Model.User> getUsers() {
        return users;
    }

    public void setUsers(Set<dam.josantvarona.tfgbakend.Model.User> users) {
        this.users = users;
    }

}