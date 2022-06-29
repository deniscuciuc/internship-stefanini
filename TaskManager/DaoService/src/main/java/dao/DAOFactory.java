package dao;

import dao.enums.AvaibleDAOFactories;
import dao.impl.JdbcDAOFactory;

public abstract class DAOFactory {
	public abstract UserDAO getUserDAO();
	public abstract TaskDAO getTaskDAO();
	
	public static DAOFactory getDAOFactory(AvaibleDAOFactories wichFactory) {
		switch(wichFactory) {
		case JDBC:
			return new JdbcDAOFactory();
		default:
			return null;
		}
	}
}
