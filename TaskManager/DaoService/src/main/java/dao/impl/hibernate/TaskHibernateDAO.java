package dao.impl.hibernate;

import dao.TaskDAO;
import dao.impl.hibernate.util.HibernateFactory;
import domain.entities.TaskEntity;
import domain.entities.UserEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TaskHibernateDAO implements TaskDAO {

	
    @Override
    public void createTask(TaskEntity task) {
        try {
            SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            session.save(task);

            session.getTransaction().commit();
            System.out.println("Task ID = " + task.getId());
            System.out.println("Username = " + task.getUser().getId());
            if (session.isOpen()) {
                session.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TaskEntity> getAllUsersTasks(String userName) {
        try {
            Session session = HibernateFactory.getSessionAnnotationFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from UserEntity where userName = :userName");
            query.setString("userName", userName);
            UserEntity user = (UserEntity) query.list().get(0);

            query = session.createQuery("from TaskEntity where user_id = :user_id");
            query.setInteger("user_id", user.getId());
            List<TaskEntity> tasks = query.list();

            transaction.rollback();

            if (session.isOpen()) {
                session.close();
            }
            return tasks;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
