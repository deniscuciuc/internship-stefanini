package dao.impl.hibernate;

import dao.DAOFactory;
import dao.TaskDAO;
import dao.UserDAO;

/**
 * Simple Hibernate DAO Factory through which we can access  operations
 * @author dcuciuc
 */
public class HibernateDAOFactory extends DAOFactory {

	/**
	 * Allows creating new UserHibernateDAO and perform Hibernate operations on User
	 * @return UserHibernateDAO
	 */
    @Override
    public UserDAO getUserDAO() {
        return new UserHibernateDAO();
    }

    /**
     * Allows creating new TaskHibernateDAO and perform Hibernate operations on Tasks
     * @return TaskHibernateDAO
     */
    @Override
    public TaskDAO getTaskDAO() {
        return new TaskHibernateDAO();
    }
}
