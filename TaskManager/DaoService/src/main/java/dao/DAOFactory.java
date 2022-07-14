package dao;

import dao.enums.AvailableDAOFactories;
import dao.impl.hibernate.HibernateDAOFactory;
import dao.impl.jdbc.JdbcDAOFactory;

/**
 * Abstract DAOFactory class allow to get needed DAOFactory and DAO beans interfaces
 * @author dcuciuc
 *
 */
public abstract class DAOFactory {
	
	public abstract UserDAO getUserDAO();
	public abstract TaskDAO getTaskDAO();
	public abstract GenericDAO getGenericDAO();

	/**
	 * Method allows to access the selected DAOFactories
	 * @return DAOFactory
	 */
	public static DAOFactory getDAOFactory(AvailableDAOFactories whichFactory) {
		switch(whichFactory) {
			case JDBC:
				return new JdbcDAOFactory();
			case HIBERNATE:
				return new HibernateDAOFactory();
			default:
				return null;
		}
	}
}
