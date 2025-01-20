package CRUD;

import modelo.Categoria;
import modelo.Producto;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

/**
 * Clase que contiene los métodos para realizar operaciones CRUD sobre la tabla Producto de la base de datos.
 */
public class CrudProducto {
    /**
     * Método que crea un nuevo producto en la base de datos.
     * @param producto Producto a crear.
     */
    public void crearProducto(Producto producto) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            sesion.beginTransaction();

            sesion.merge(producto);

            sesion.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Método que devuelve un producto de la base de datos.
     * @param idProducto ID del producto a buscar.
     * @return Producto encontrado.
     */
    public Producto verProducto(int idProducto) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            return sesion.get(Producto.class, idProducto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método que actualiza un producto en la base de datos.
     * @param idProducto ID del producto a actualizar.
     * @param producto Producto con los nuevos datos.
     */
    public void actualizarProducto(int idProducto, Producto producto) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            sesion.beginTransaction();
            Producto productoDB = sesion.get(Producto.class, idProducto);
            if (productoDB != null) {
                productoDB.setDescripcion(producto.getDescripcion());
                productoDB.setKeyRFID(producto.getKeyRFID()); // Corregido
                productoDB.setEan(producto.getEan());
                productoDB.setCategoria(producto.getCategoria());
                sesion.merge(productoDB);
                sesion.getTransaction().commit();
            } else {
                System.out.println("Producto no encontrado para ID: " + idProducto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que borra un producto de la base de datos.
     * @param idProducto ID del producto a borrar.
     */
    public void borrarProducto(int idProducto) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            sesion.beginTransaction();
            Producto producto = sesion.get(Producto.class, idProducto);

            if (producto != null) {
                producto.setCategoria(null);

                sesion.remove(producto);

                sesion.getTransaction().commit();
            } else {
                System.out.println("Producto no encontrado para ID: " + idProducto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Método que devuelve todos los productos de la base de datos.
     * @return Lista de productos.
     */
    public List<Producto> verTodosProductos() {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Producto";
            return sesion.createQuery(hql, Producto.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método que cambia las categorías de un producto.
     * @param idProducto ID del producto a cambiar.
     * @param categoria Nueva categoría.
     */
    public void cambiarCategorias(int idProducto, Categoria categoria) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            sesion.beginTransaction();
            Producto producto = sesion.get(Producto.class, idProducto);
            if (producto != null) {
                producto.setCategoria(categoria);
                sesion.merge(producto);
                sesion.getTransaction().commit();
            } else {
                System.out.println("Producto no encontrado para ID: " + idProducto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que busca productos en la base de datos.
     * @param descripcion Descripción del producto.
     * @param ean EAN del producto.
     * @param keyRFID Key RFID del producto.
     * @param nombreCategoria Nombre de la categoría del producto.
     * @return Lista de productos encontrados.
     */
    public List<Producto> buscarProducto(String descripcion, int ean, String keyRFID, String nombreCategoria) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("from Producto p where 1=1");

            if (descripcion != null) {
                hql.append(" and descripcion LIKE :descripcion");
            }
            if (ean != 0) {
                hql.append(" and ean = :ean");
            }
            if (keyRFID != null) {
                hql.append(" and keyRFID Like :keyRFID");
            }

                if (nombreCategoria != null && nombreCategoria.equals("Sin categoria")) {
                hql.append(" and categoria is null");
            } else if (nombreCategoria != null) {
                hql.append(" and :categoria = p.categoria");
            }

            Query<Producto> query = sesion.createQuery(hql.toString(), Producto.class);
            if (descripcion != null) {
                query.setParameter("descripcion", "%" + descripcion + "%");
            }
            if (ean != 0) {
                query.setParameter("ean", ean);
            }
            if (keyRFID != null) {
                query.setParameter("keyRFID", "%" + keyRFID + "%");
            }
            if (nombreCategoria != null && !nombreCategoria.equals("Sin categoria")) {
                Query<Categoria> categoriaQuery = sesion.createQuery("from Categoria c where c.nombre = :nombreCategoria", Categoria.class);
                categoriaQuery.setParameter("nombreCategoria", nombreCategoria);
                Categoria categoria = categoriaQuery.uniqueResult();

                if (categoria != null) {
                    query.setParameter("categoria", categoria);
                } else {
                    System.out.println("Categoría no encontrada: " + nombreCategoria);
                    return null;
                }
            }

            return query.list();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
