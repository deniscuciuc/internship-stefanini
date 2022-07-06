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
	 * Allows to create new UserHibernateDAO and perform Hibernate operations on User
	 * @return UserHibernateDAO
	 */
    @Override
    public UserDAO getUserDAO() {
        return new UserHibernateDAO();
    }

    /**
     * Allows to create new TaskHibernateDAO and perform Hibernate operations on Tasks
     * @return TaskHibernateDAO
     */
    @Override
    public TaskDAO getTaskDAO() {
        return new TaskHibernateDAO();
    }
}
