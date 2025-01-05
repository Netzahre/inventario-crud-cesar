package CRUD;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class hibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();
    private hibernateUtil() {}

    /**
     * Crea la sesion de hibernate
     * @return SessionFactory
     */
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Devuelve la sesion de hibernate
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Cierra la sesion de hibernate
     */
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
