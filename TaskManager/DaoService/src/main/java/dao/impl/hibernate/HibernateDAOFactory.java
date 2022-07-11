package dao.impl.hibernate;

import dao.DAOFactory;
import dao.TaskDAO;
import dao.UserDAO;

/**
 * Simple Hibernate DAO Factory through which we can access low-level operations with database
 * @author dcuciuc
 */
public class HibernateDAOFactory extends DAOFactory {

	/**
	 * Allows creating new UserHibernateDAO and perform Hibernate operations with UserEntities objects
	 * @return UserHibernateDAO
	 */
    @Override
    public UserDAO getUserDAO() {
        return new UserHibernateDAO();
    }

    /**
     * Allows creating new TaskHibernateDAO and perform Hibernate operations with TaskEntities object
     * @return TaskHibernateDAO
     */
    @Override
    public TaskDAO getTaskDAO() {
        return new TaskHibernateDAO();
    }
}
