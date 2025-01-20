package modelo;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "marcajes")
public class Marcaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMarcaje", nullable = false)
    private Integer id;

    //si se borra un producto, se borran todos los marcajes asociados
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto idProducto;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "idAula", nullable = false)
    private Aula idAula;

    @Column(name = "Tipo", nullable = false)
    private Integer tipo;

    @Column(name = "TimeStamp", nullable = false)
    private LocalDateTime timeStamp;

    @Column(name = "TipoTexto", length = 10)
    private String tipoTexto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Aula getIdAula() {
        return idAula;
    }

    public void setIdAula(Aula idAula) {
        this.idAula = idAula;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTipoTexto() {
        return tipoTexto;
    }

    public void setTipoTexto(String tipoTexto) {
        this.tipoTexto = tipoTexto;
    }

}