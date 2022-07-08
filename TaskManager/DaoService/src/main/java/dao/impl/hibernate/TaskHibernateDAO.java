package dao.impl.hibernate;

import dao.TaskDAO;
import dao.impl.hibernate.util.HibernateFactory;
import domain.beans.TaskBean;
import domain.beans.UserBean;
import org.hibernate.Session;


import java.util.HashSet;
import java.util.Set;


public class TaskHibernateDAO implements TaskDAO {

	
    @Override
    public void createTask(TaskBean task) {

    }

    @Override
    public Set<TaskBean> getAllUsersTasks(int userId) {
        Session session = HibernateFactory.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        TaskBean task = (TaskBean) session.load(TaskBean.class, userId);
        session.getTransaction().commit();
        return null;
    }
}
