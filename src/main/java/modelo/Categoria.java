package modelo;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoria", nullable = false)
    private Integer id;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Lob
    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Estado", nullable = false, length = 20)
    private String estado;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.EAGER)
    private Set<Producto> productos = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public int getCantidadProductos() {
        return productos.size();
    }

    @Override
    public String toString() {
        return nombre;
    }
}