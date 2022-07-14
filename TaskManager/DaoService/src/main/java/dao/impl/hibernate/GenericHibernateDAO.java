package dao.impl.hibernate;

import dao.GenericDAO;
import dao.impl.hibernate.util.HibernateFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class GenericHibernateDAO<T> implements GenericDAO<T> {

    private static final Logger logger = LogManager.getLogger(GenericHibernateDAO.class);
    private static GenericHibernateDAO genericHibernateDAO;

    public GenericHibernateDAO() {

    }

    private GenericHibernateDAO(GenericHibernateDAO genericHibernateDAO) {
        GenericHibernateDAO.genericHibernateDAO = genericHibernateDAO;
    }

    public static GenericHibernateDAO getInstance() {
        if (GenericHibernateDAO.genericHibernateDAO == null) {
            GenericHibernateDAO.genericHibernateDAO = new GenericHibernateDAO();
        }
        return GenericHibernateDAO.genericHibernateDAO;
    }

    @Override
    public void create(T item) {
        try {
            SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            if (session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction();
            } else {
                session.beginTransaction();
            }

            session.save(item);

            session.getTransaction().commit();
            logger.info("Item is saved!");

            if (session.isOpen()) {
                session.close();
            }

        } catch(Exception e) {
            logger.error("Exception while creating item occurred. " + e.getMessage());
        }
    }
}
