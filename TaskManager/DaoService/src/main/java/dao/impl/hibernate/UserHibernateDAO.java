package dao.impl.hibernate;

import dao.UserDAO;
import dao.impl.hibernate.util.HibernateFactory;
import domain.UserEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * This Hibernate DAO class is responsible for Hibernate operations with UserEntity object and MySQL
 * @author dcuciuc
 */
public class UserHibernateDAO implements UserDAO {

    /**
     * Method saves UserEntity object in database through one hibernate transaction.
     * If the user is created with tasks, they will also be saved.
     * Then commit, after commit we can use user id from database.
     * After all the method will check whether the session is open, and if it is, it will close it
     * @param user
     */
    @Override
    public void createUser(UserEntity user) {
       try {
           SessionFactory sessionFactory = HibernateFactory.getSessionAnnotationFactory();
           Session session = sessionFactory.getCurrentSession();

           session.beginTransaction();

           session.save(user);

           session.getTransaction().commit();
           System.out.println("User is saved! User ID = " + user.getId());

           if (session.isOpen()) {
               session.close();
           }
       } catch(Exception e) {
           e.printStackTrace();
       }
    }

    /**
     * Method gets all users from database in one transaction.
     * All users will be placed in the list, then save it with rollback.
     * If the method returns null, it means that there are no users in the database
     * @return List<UserEntity>
     */
    @Override
    public List<UserEntity> getAllUsers() {
        try {
            Session session = HibernateFactory.getSessionAnnotationFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();

            List users = session.createQuery("from UserEntity").list();
            transaction.rollback();

            if (session.isOpen()) {
                session.close();
            }

            return users;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The method is very useful because it returns a UserEntity object by username,
     * which means that we can access User tasks through it.
     * UserEntity object is getting from query.list() method, but we get only the first item from list
     * If the method returns null, it means that there are no users with that username in the database
     * @param userName
     * @return UserEntity
     */
    @Override
    public UserEntity getUserByUserName(String userName) {
        try {
            Session session = HibernateFactory.getSessionAnnotationFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("from UserEntity where userName = :userName");
            query.setString("userName", userName);

            UserEntity user = (UserEntity) query.list().get(0);
            transaction.rollback();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
