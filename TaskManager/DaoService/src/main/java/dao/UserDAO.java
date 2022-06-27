package dao;



import domain.UserEntity;

public interface UserDAO {
	void createUser(UserEntity user);
	void getAllUsers();
}
