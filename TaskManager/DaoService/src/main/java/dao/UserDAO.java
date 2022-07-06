package dao;


/**
 * Through this interface we get access to low level operations,
 * which have their own classes that implements this class, for example UserJdbcDAO and UserHibernateDAO
 * @author dcuciuc
 *
 */
import java.util.List;

import domain.beans.UserBean;
import domain.entities.UserEntity;

public interface UserDAO {
	void createUser(UserBean user);
	List<UserEntity> getAllUsers();
}
