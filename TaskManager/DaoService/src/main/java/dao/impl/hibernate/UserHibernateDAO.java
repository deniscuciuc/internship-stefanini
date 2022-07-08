package dao.impl.hibernate;

import dao.UserDAO;
import dao.impl.hibernate.util.HibernateFactory;
import domain.entities.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserHibernateDAO implements UserDAO {


    @Override
    public void createUser(UserEntity user) {
       try {
           SessionFactory sessionFactory = HibernateFactory.getSessionAnnotationFactory();
           Session session = sessionFactory.getCurrentSession();

           session.beginTransaction();

           session.save(user);

           session.getTransaction().commit();
           System.out.println("Employee ID = " + user.getId());

           if (session.isOpen()) {
               session.close();
           }
       } catch(Exception e) {
           e.printStackTrace();
       }
    }

    @Override
    public List<UserEntity> getAllUsers() {
        try {
            Session session = HibernateFactory.getSessionAnnotationFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();

            List<UserEntity> users = session.createQuery("from UserEntity").list();
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
}
