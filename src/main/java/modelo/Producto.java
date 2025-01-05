package modelo;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "Descripcion", nullable = false)
    private String descripcion;

    @Column(name = "EAN", nullable = false)
    private Integer ean;

    @Column(name = "keyRFID", nullable = false, length = 10)
    private String keyRFID;

    @OneToMany(mappedBy = "idProducto", cascade = CascadeType.ALL)
    private Set<Marcaje> marcajes = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "productoporcategoria",
            joinColumns = @JoinColumn(name = "idProducto"),
            inverseJoinColumns = @JoinColumn(name = "idCategoria"))
    private Set<Categoria> categorias = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEan() {
        return ean;
    }

    public void setEan(Integer ean) {
        this.ean = ean;
    }

    public String getKeyRFID() {
        return keyRFID;
    }

    public void setKeyRFID(String keyRFID) {
        this.keyRFID = keyRFID;
    }

    public Set<Marcaje> getMarcajes() {
        return marcajes;
    }

    public void setMarcajes(Set<Marcaje> marcajes) {
        this.marcajes = marcajes;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}