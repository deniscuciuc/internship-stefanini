package dao.impl.hibernate;

import dao.DAOFactory;
import dao.GenericDAO;
import dao.TaskDAO;
import dao.UserDAO;


public class HibernateDAOFactory extends DAOFactory {


    @Override
    public UserDAO getUserDAO() {
        return UserHibernateDAO.getInstance();
    }


    @Override
    public TaskDAO getTaskDAO() {
        return TaskHibernateDAO.getInstance();
    }


    @Override
    public GenericDAO getGenericDAO() {
        return GenericHibernateDAO.getInstance();
    }
}
