package dao.impl.hibernate;

import dao.UserDAO;
import dao.impl.hibernate.util.HibernateFactory;
import domain.UserEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;


public class UserHibernateDAO implements UserDAO {

    private static final Logger logger = LogManager.getLogger(UserHibernateDAO.class);
    private static final String USER_CLASS_NAME = UserEntity.class.getName();
    private static UserHibernateDAO userHibernateDAO;

    public UserHibernateDAO() {

    }

    private UserHibernateDAO(UserHibernateDAO userHibernateDAO) {
        UserHibernateDAO.userHibernateDAO = userHibernateDAO;
    }

    public static UserHibernateDAO getInstance() {
        if (UserHibernateDAO.userHibernateDAO == null) {
            UserHibernateDAO.userHibernateDAO = new UserHibernateDAO();
        }
        return UserHibernateDAO.userHibernateDAO;
    }


    @Override
    public List<UserEntity> getAllUsers() {
        final Session session = HibernateFactory.getSessionFactory().getCurrentSession();
        try {
            final Transaction transaction = session.beginTransaction();

            Criteria criteria = session.createCriteria(USER_CLASS_NAME);
            List users = criteria.list();

            transaction.commit();

            return users;
        } catch(Exception e) {
            logger.error("Exception while getting all users occurred. " + e.getMessage());
        } finally {
            HibernateFactory.closeSession();
        }
        return null;
    }


    @Override
    public UserEntity getUserByUserName(String userName) {
        final Session session = HibernateFactory.getSession();
        try {
            final Transaction transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(USER_CLASS_NAME);
            criteria.add(Restrictions.eq("userName", userName));

            UserEntity user = (UserEntity) criteria.list().get(0);
            transaction.rollback();

            return user;
        } catch (Exception e) {
            logger.error("Exception while getting user by username occurred. " + e.getMessage());
        } finally {
            HibernateFactory.closeSession();
        }
        return null;
    }
}
