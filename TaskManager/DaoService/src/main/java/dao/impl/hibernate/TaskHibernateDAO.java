package dao.impl.hibernate;

import dao.TaskDAO;
import dao.impl.hibernate.util.HibernateFactory;
import domain.TaskEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;


import java.util.List;


public class TaskHibernateDAO implements TaskDAO {

    private static final Logger logger = LogManager.getLogger(TaskHibernateDAO.class);
    private static final String TASK_CLASS_NAME = TaskEntity.class.getName();
    private static TaskHibernateDAO taskHibernateDAO;

    public TaskHibernateDAO() {

    }

    private TaskHibernateDAO(TaskHibernateDAO taskHibernateDAO) {
        TaskHibernateDAO.taskHibernateDAO = taskHibernateDAO;
    }

    public static TaskHibernateDAO getInstance() {
        if (TaskHibernateDAO.taskHibernateDAO == null) {
            TaskHibernateDAO.taskHibernateDAO = new TaskHibernateDAO();
        }
        return TaskHibernateDAO.taskHibernateDAO;
    }


    @Override
    public List<TaskEntity> getAllUsersTasks(int userId) {
        final Session session = HibernateFactory.getSessionFactory().getCurrentSession();
        try {
            final Transaction transaction = session.beginTransaction();

            Criteria criteria = session.createCriteria(TASK_CLASS_NAME);
            criteria.add(Restrictions.eq("user_id", userId));

            List<TaskEntity> tasks = criteria.list();

            transaction.rollback();

            if (session.isOpen()) {
                session.close();
            }

            return tasks;
        } catch(Exception e) {
            logger.error("Exception while getting all user's tasks from database occurred. " + e.getMessage());
        } finally {
            HibernateFactory.closeSession();
        }
        return null;
    }
}
