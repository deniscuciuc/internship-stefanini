package dao.impl.hibernate.util;

import dao.impl.helper.PropertiesHelper;
import domain.TaskEntity;
import domain.UserEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


/**
 * This is the helper class that will allow us to build and get a sessionFactories to perform transactions
 * @author dcuciuc
 */
public class HibernateFactory {

    private static PropertiesHelper propertiesHelper = new PropertiesHelper("hibernate.properties");
    private static SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger(HibernateFactory.class);
    private static final ThreadLocal<Session> threadLocal = new ThreadLocal();

    private HibernateFactory(SessionFactory sessionFactory) {
        HibernateFactory.sessionFactory = sessionFactory;
    }

    /**
     * The method will check if sessionFactory is not null (in which case it will build a session annotation factory and configure it).
     * Return us a static sessionFactory to perform the transactions
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) sessionFactory = buildSessionAnnotationFactory();
        return sessionFactory;
    }

    public static Session getSession() {
        Session session = threadLocal.get();

        if (session == null || !session.isOpen()) {
            if (session == null) {
                buildSessionAnnotationFactory();
            }
            session = (sessionFactory != null) ? sessionFactory.openSession() : null;
            threadLocal.set(session);
        }
        return session;
    }

    private static SessionFactory buildSessionAnnotationFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(UserEntity.class);
            configuration.addAnnotatedClass(TaskEntity.class);
            configuration.addProperties(propertiesHelper.getProperties());
            logger.info("Hibernate Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            logger.info("Hibernate Service Registry created");

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        } catch (Throwable e) {
            logger.error("Initial SessionFactory creation failed. " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void closeSession() {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if (session != null) {
            session.close();
        }
    }
}
