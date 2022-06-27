package dao;

import dao.enums.AvaibleDAOFactories;

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
