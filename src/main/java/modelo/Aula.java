package modelo;

import jakarta.persistence.*;
import javafx.fxml.FXML;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "aula")
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAula", nullable = false)
    private Integer id;

    @Column(name = "Numeracion", nullable = false)
    private String numeracion;

    @Lob
    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "IP", nullable = false, length = 15)
    private String ip;

    @OneToMany(mappedBy = "idAula", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Marcaje> marcajes = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(String numeracion) {
        this.numeracion = numeracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Set<Marcaje> getMarcajes() {
        return marcajes;
    }

    public void setMarcajes(Set<Marcaje> marcajes) {
        this.marcajes = marcajes;
    }

    @Override
    public String toString() {
        return numeracion;
    }
}