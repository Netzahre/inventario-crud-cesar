package CRUD;

import modelo.Aula;
import modelo.Marcaje;
import modelo.Producto;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase que se encarga de realizar las operaciones CRUD de la tabla Marcaje
 */
public class CrudMarcajes {
    /**
     * Método que se encarga de crear un nuevo marcaje en la base de datos
     *
     * @param marcaje Objeto de la clase Marcaje que se desea crear
     */
    public void crearMarcaje(Marcaje marcaje) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            sesion.beginTransaction();
            sesion.persist(marcaje);
            sesion.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que se encarga de actualizar un marcaje en la base de datos
     *
     * @param idMarcaje Objeto de la clase Marcaje que se desea actualizar
     */
    public Marcaje verMarcaje(int idMarcaje) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            return sesion.get(Marcaje.class, idMarcaje);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método que se encarga de actualizar un marcaje en la base de datos
     * @param idMarcaje Objeto de la clase Marcaje que se desea actualizar
     */
    public void borrarMarcaje(int idMarcaje) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            sesion.beginTransaction();
            Marcaje marcajeDB = sesion.get(Marcaje.class, idMarcaje);
            if (marcajeDB != null) {
                sesion.remove(marcajeDB);
                sesion.getTransaction().commit();
            } else {
                System.out.println("Marcaje no encontrado para ID: " + idMarcaje);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que se encarga de actualizar un marcaje en la base de datos
     * @return Lista de marcajes
     */
    public List<Marcaje> verMarcajes() {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Marcaje";
            return sesion.createQuery(hql, Marcaje.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método que se encarga de actualizar un marcaje en la base de datos
     *
     * @param fechaInicio Fecha de inicio del filtro
     * @param fechaFin    Fecha de fin del filtro
     * @param producto    Producto a filtrar
     * @param aula        Aula a filtrar
     * @param pabellon    Pabellon a filtrar
     * @return Lista de marcajes filtrados
     */
    public List<Marcaje> filtrarMarcajes(LocalDateTime fechaInicio, LocalDateTime fechaFin, Producto producto, Aula aula, int pabellon) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("from Marcaje m where 1=1");

            if (fechaInicio != null && fechaFin != null) {
                hql.append(" and timeStamp between :fechaInicio and :fechaFin");
            }
            if (producto != null) {
                hql.append(" and m.idProducto = :producto");
            }

            if (aula != null) {
                hql.append(" and m.idAula = :aula");
            }

            if (pabellon != 0) {
                hql.append(" and m.idAula.numeracion like :pabellon");
            }

            hql.append(" order by timeStamp desc");

            Query<Marcaje> query = sesion.createQuery(hql.toString(), Marcaje.class);

            if (fechaInicio != null && fechaFin != null) {
                query.setParameter("fechaInicio", fechaInicio);
                query.setParameter("fechaFin", fechaFin);
            }

            if (producto != null) {
                query.setParameter("producto", producto);
            }

            if (aula != null) {
                query.setParameter("aula", aula);
            }

            if (pabellon != 0) {
                query.setParameter("pabellon", pabellon + "%");
            }

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
