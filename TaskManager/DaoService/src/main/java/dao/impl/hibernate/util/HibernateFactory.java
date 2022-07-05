package dao.impl.hibernate.util;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * This is the helper class that will allow us to build and get a sessionFactory to perform transactions
 * @author dcuciuc
 */
public class HibernateFactory {

    private static SessionFactory sessionFactory;

    private HibernateFactory(SessionFactory sessionFactory) {
        HibernateFactory.sessionFactory = sessionFactory;
    }


    /**
     * The method will check if sessionFactory is not null (in which case it will call buildSessionFactoryMethod).
     * Return us a static sessionFactory to perform the transactions
     * @return sessionFactory
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }

    private static SessionFactory buildSessionFactory() {
        return null;
    }
}
