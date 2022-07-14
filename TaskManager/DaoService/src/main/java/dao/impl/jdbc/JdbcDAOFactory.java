package dao.impl.jdbc;

import dao.DAOFactory;
import dao.GenericDAO;
import dao.TaskDAO;
import dao.UserDAO;

/**
 * This class represents constructor for impl of TaskDao and UserDAO.
 * Also, it makes connections with Database
 * @author dcuciuc
 */
public class JdbcDAOFactory extends DAOFactory {

	/**
	 * Create new UserJdbcDAO to execute operations with users through jdbc
	 * @return UserDAO
	 */
	@Override
	public UserDAO getUserDAO() {
		return UserJdbcDAO.getInstance();
	}


	/**
	 * Create new TaskJdbcDAO to execute operations with tasks through jdbc
	 * @return TaskDAO
	 */
	@Override
	public TaskDAO getTaskDAO() {
		return TaskJdbcDAO.getInstance();
	}

	@Override
	public GenericDAO getGenericDAO() {
		return null;
	}


}
