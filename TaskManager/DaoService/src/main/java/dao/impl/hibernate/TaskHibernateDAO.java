package dao.impl.hibernate;

import dao.TaskDAO;
import dao.impl.hibernate.util.HibernateFactory;
import domain.TaskEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.List;

/**
 * This Hibernate DAO class is responsible for Hibernate operations with TaskEntity object and MySQL
 * @author dcuciuc
 */
public class TaskHibernateDAO implements TaskDAO {

    /**
     * Method saves TaskEntity object in database through one hibernate transaction.
     * Then commit, after commit we can use task id from database.
     * After all the method will check whether the session is open, and if it is, it will close it
     * @param task
     */
    @Override
    public void createTask(TaskEntity task) {
        try {
            SessionFactory sessionFactory = HibernateFactory.getSessionAnnotationFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            session.save(task);

            session.getTransaction().commit();
            System.out.println("Task is saved! Task ID = " + task.getId() + "| Performer = " + task.getUser().getUserName());

            if (session.isOpen()) {
                session.close();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method gets all user's tasks from database using his id and one transaction.
     * All tasks will be placed in the list, then save it with rollback.
     * If the method returns null, it means that user has no tasks
     * @param userId
     * @return List<TaskEntity>
     */
    @Override
    public List<TaskEntity> getAllUsersTasks(int userId) {
        try {
            Session session = HibernateFactory.getSessionAnnotationFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("from TaskEntity where user_id = :user_id");
            query.setInteger("user_id", userId);
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
