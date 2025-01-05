package CRUD;

import modelo.Aula;
import org.hibernate.Session;

import java.util.List;
/**
 * Clase que se encarga de realizar las operaciones CRUD de la entidad Aula
 */
public class CrudAula {

    /**
     * Método que permite crear un aula
     * @param aula
     */
    public void crearAula(Aula aula) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            sesion.beginTransaction();
            sesion.persist(aula);
            sesion.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que permite ver una aula en específico
     * @param idAula
     * @return
     */
    public Aula verAula(int idAula) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
        return sesion.get(Aula.class, idAula);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método que permite actualizar un aula
     * @param idAula
     * @param aula
     */
    public void actualizarAula(int idAula, Aula aula) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            sesion.beginTransaction();
            Aula aulaDB = sesion.get(Aula.class, idAula);
            if (aulaDB != null) {
                aulaDB.setNumeracion(aula.getNumeracion());
                aulaDB.setDescripcion(aula.getDescripcion());
                aulaDB.setIp(aula.getIp());
                sesion.merge(aulaDB);
                sesion.getTransaction().commit();
            } else {
                System.out.println("Aula no encontrada para ID: " + idAula);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que permite borrar un aula
     * @param idAula
     */
    public void borrarAula(int idAula) {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            sesion.beginTransaction();
            Aula aula = sesion.get(Aula.class, idAula);
            if (aula != null) {
                sesion.remove(aula);
                sesion.getTransaction().commit();
            } else {
                System.out.println("Aula no encontrada para ID: " + idAula);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que permite ver todas las aulas
     * @return
     */
    public List<Aula> verTodasAulas() {
        try (Session sesion = hibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Aula";
            return sesion.createQuery(hql, Aula.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
