package service.impl;


import java.util.Set;


import domain.UserEntity;
import domain.TaskEntity;
import dao.DAOFactory;
import dao.enums.AvailableDAOFactories;
import email.SendMail;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.TaskService;
import service.exceptions.InvalidUserException;


public class TaskServiceImpl implements TaskService {

	private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);


	@Override
	public void addTask(TaskEntity task) {
		saveTaskHibernateMySQLByUserId(task);
		sendMail(task.getId(), task.getTitle(), task.getDescription(), task.getUser().getUserName());
	}


	@Override
	public void showAllUsersTasks(String userName) {
		displayTasksConsole(userName, getUsersTasksByUserNameHibernateMySQL(userName));
 	}

	@SendMail
	private void sendMail(int id, String title, String description, String userName) {
		logger.info("Sending email...");
	}

	private void displayTasksConsole(String userName, Set<TaskEntity> tasks) {
		System.out.println(userName + "'s number of tasks: " + tasks.size());
		tasks.stream().forEach(System.out::println);
	}

	private Set<TaskEntity> getUsersTasksByUserNameHibernateMySQL(String userName) {
		return DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().getUserByUserName(userName).getTasks();
	}

	private void saveTaskHibernateMySQLByUserId(TaskEntity task) {
		DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getGenericDAO().create(task);
	}
}
