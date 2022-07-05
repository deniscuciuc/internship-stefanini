package dao;

import dao.enums.AvailableDAOFactories;
import dao.impl.hibernate.HibernateDAOFactory;
import dao.impl.jdbc.JdbcDAOFactory;

public abstract class DAOFactory {
	public abstract UserDAO getUserDAO();
	public abstract TaskDAO getTaskDAO();
	
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
