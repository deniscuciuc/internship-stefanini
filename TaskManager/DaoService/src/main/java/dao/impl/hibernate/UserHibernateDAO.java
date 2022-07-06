package dao.impl.hibernate;

import dao.UserDAO;
import dao.impl.hibernate.util.HibernateFactory;
import domain.beans.UserBean;
import domain.entities.UserEntity;
import org.hibernate.Session;

import java.util.List;

public class UserHibernateDAO implements UserDAO {

    @Override
    public void createUser(UserBean user) {
        Session session = HibernateFactory.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        System.out.println("UserId created: " + user.getId());
        HibernateFactory.getSessionFactory().close();
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return null;
    }
}
