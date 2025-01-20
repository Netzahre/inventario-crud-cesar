package CRUD;

import modelo.Aula;
import modelo.Categoria;
import modelo.Producto;
import org.hibernate.Session;

import java.util.List;

/**
 * Clase CrudCategoria
 * Contiene los métodos necesarios para realizar operaciones CRUD sobre la tabla Categoria
 */
public class CrudCategoria {
    /**
     * Método crearCategoria
     * Permite crear una nueva categoría
     * @param categoria Objeto de tipo Categoria
     */
    public void crearCategoria(Categoria categoria) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            sesion.beginTransaction();
            sesion.persist(categoria);
            sesion.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método verCategoria
     * Permite ver una categoría
     * @param idCategoria ID de la categoría
     * @return Objeto de tipo Categoria
     */
    public Categoria verCategoria(int idCategoria) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
        return sesion.get(Categoria.class, idCategoria);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método actualizarCategoria
     * Permite actualizar una categoría
     * @param idCategoria ID de la categoría
     * @param categoria Objeto de tipo Categoria
     */
    public void actualizarCategoria(int idCategoria, Categoria categoria) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            sesion.beginTransaction();
            Categoria categoriaDB = sesion.get(Categoria.class, idCategoria);
            if (categoriaDB != null) {
                categoriaDB.setNombre(categoria.getNombre());
                categoriaDB.setEstado(categoria.getEstado());
                categoriaDB.setDescripcion(categoria.getDescripcion());
                sesion.merge(categoriaDB);
                sesion.getTransaction().commit();
            } else {
                System.out.println("Categoria no encontrada para ID: " + idCategoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método borrarCategoria
     * Permite borrar una categoría
     * @param idCategoria ID de la categoría
     */
    public void borrarCategoria(int idCategoria) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            sesion.beginTransaction();
            Categoria categoriaDB = sesion.get(Categoria.class, idCategoria);
            if (categoriaDB != null) {
                for (Producto producto : categoriaDB.getProductos()) {
                    producto.setCategoria(null);
                    sesion.merge(producto);
                }
                sesion.remove(categoriaDB);
                sesion.getTransaction().commit();
            } else {
                System.out.println("Categoria no encontrada para ID: " + idCategoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método verCategorias
     * Permite ver todas las categorías
     * @return Lista de objetos de tipo Categoria
     */
    public List<Categoria> verCategorias() {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Categoria";
            return sesion.createQuery(hql, Categoria.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
