package com.stefanini.taskmanager.commands.impl;

import com.stefanini.taskmanager.commands.CommandsExecutor;
import com.stefanini.taskmanager.commands.ThreadTask;
import com.stefanini.taskmanager.commands.actions.AddTaskCommand;
import com.stefanini.taskmanager.commands.actions.CreateUserCommand;
import com.stefanini.taskmanager.commands.actions.ShowAllUsersCommand;
import com.stefanini.taskmanager.commands.actions.ShowAllUsersTasksCommand;
import dao.DAOFactory;
import dao.enums.AvailableDAOFactories;
import domain.TaskEntity;
import domain.UserEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.TaskService;
import service.UserService;
import service.exceptions.InvalidUserException;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.*;

public class CommandsExecutorImpl implements CommandsExecutor {

    private static final Logger logger = LogManager.getLogger(CommandsExecutorImpl.class);
    private ExecutorService executorService;

    private UserEntity user;
    private TaskEntity task;

    @Override
    public void executeAllCommands() {
        getUserAndTaskData();
        executeThreadPools();
    }

    private void getUserAndTaskData() {
        user = null;

        do {
            user = getUserData();
        } while (user == null);

        task = getTaskData();

        user.getTasks().add(task);
        task.setUser(user);
    }

    private UserEntity getUserData() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Username: ");
            String userName = scanner.nextLine();

            checkIfUserNameAlreadyExists(userName);

            System.out.print("First name: ");
            String firstName = scanner.nextLine();

            System.out.print("Last name: ");
            String lastName = scanner.nextLine();


            Set<TaskEntity> tasks = new HashSet<>();

            return new UserEntity(firstName, lastName, userName, tasks);
        } catch (InvalidUserException e) {
            logger.error("Exception while getting user details from console. " + e.getMessage());
        }
        return null;
    }

    private TaskEntity getTaskData() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Task title: ");
        String title = scanner.nextLine();

        System.out.print("Task description: ");
        String description = scanner.nextLine();

        return new TaskEntity(title, description);
    }

    private void checkIfUserNameAlreadyExists(String userName) throws InvalidUserException {
        UserEntity user = DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().getUserByUserName(userName);
        if (user != null) {
            throw new InvalidUserException("Username already exists!");
        }
    }

    private void executeThreadPools() {
        executorService = Executors.newFixedThreadPool(4);
        ThreadTask createUser = new ThreadTask(new CreateUserCommand(user));
        ThreadTask addTask = new ThreadTask(new AddTaskCommand(task));
        ThreadTask showAllUsers = new ThreadTask(new ShowAllUsersCommand());
        ThreadTask showAllUsersTasks = new ThreadTask(new ShowAllUsersTasksCommand(user.getUserName()));

        Future<?> createUserStatus = executorService.submit(createUser);
        Future<?> addTaskStatus = executorService.submit(addTask);

        try {
            createUserStatus.get();
            addTaskStatus.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Future<?> showAllUsersStatus = executorService.submit(showAllUsers);
        Future<?> showAllUsersTasksStatus = executorService.submit(showAllUsersTasks);

        try {
            showAllUsersStatus.get();
            showAllUsersTasksStatus.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
