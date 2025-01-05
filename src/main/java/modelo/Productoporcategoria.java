package modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "productoporcategoria")
public class Productoporcategoria {
    @EmbeddedId
    private ProductoporcategoriaId id;

    @MapsId("idProducto")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto idProducto;

    @MapsId("idCategoria")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCategoria", nullable = false)
    private Categoria idCategoria;

    public ProductoporcategoriaId getId() {
        return id;
    }

    public void setId(ProductoporcategoriaId id) {
        this.id = id;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

}