package dao;


/**
 * Through this interface we get access to low level operations,
 * which have their own classes that implements this class, for example UserJdbcDAO and UserHibernateDAO
 * @author dcuciuc
 *
 */

import domain.beans.UserBean;

import java.util.Set;

public interface UserDAO {
	void createUser(UserBean user);
	Set<UserBean> getAllUsers();
}
