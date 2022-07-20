package service.impl;

import java.util.*;
import java.util.stream.Collectors;

import dao.DAOFactory;
import dao.enums.AvailableDAOFactories;
import domain.UserEntity;
import email.SendMail;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.UserService;


public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);


	@Override
	public void createUser(UserEntity user) {
		saveUserHibernateMySQL(user);
		sendMail(user.getFirstName(), user.getLastName(), user.getUserName());
	}


	@Override
	public void showAllUsers() {
		List<UserEntity> users = getAllUsersFromMySQLHibernate();
		displayUsersConsole(users);
	}

	@SendMail
	private void sendMail(String firstName, String lastName, String userName) {
		logger.info("Sending email...");
	}

	private void displayUsersConsole(List<UserEntity> users) {
		users.stream()
				.sorted(Comparator.comparing(UserEntity::getNumberOfTasks))
				.forEach(System.out::println);
	}

	private void saveUserHibernateMySQL(UserEntity user) {
		DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getGenericDAO().create(user);
	}

	private List<UserEntity> getAllUsersFromMySQLHibernate() {
		return DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().getAllUsers();
	}
}
