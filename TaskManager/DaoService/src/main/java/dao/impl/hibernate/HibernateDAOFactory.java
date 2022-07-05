package dao.impl.hibernate;

import dao.DAOFactory;
import dao.TaskDAO;
import dao.UserDAO;

public class HibernateDAOFactory extends DAOFactory {

    @Override
    public UserDAO getUserDAO() {
        return new UserHibernateDAO();
    }

    @Override
    public TaskDAO getTaskDAO() {
        return new TaskHibernateDAO();
    }
}
