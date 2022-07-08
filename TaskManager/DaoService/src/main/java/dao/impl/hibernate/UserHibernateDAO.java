package dao.impl.hibernate;

import dao.UserDAO;
import dao.impl.hibernate.util.HibernateFactory;
import domain.beans.UserBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.Set;

public class UserHibernateDAO implements UserDAO {

    @Override
    public void createUser(UserBean user) {
       try {

           SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
           Session session = sessionFactory.getCurrentSession();

           session.beginTransaction();

           session.save(user);

           session.getTransaction().commit();
           System.out.println("Employee ID = " + user.getId());

           if (session.isOpen()) {
               session.close();
           }
       } catch(Exception e) {
           System.out.println("Exception occured. " + e.getMessage());
           e.printStackTrace();
       }
    }

    @Override
    public Set<UserBean> getAllUsers() {
        Session session = HibernateFactory.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        HashSet users = (HashSet)session.createQuery("from UserBean").list();
        session.getTransaction().commit();
        return users;
    }
}
