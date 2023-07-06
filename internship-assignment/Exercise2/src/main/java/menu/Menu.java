package menu;

import user.Task;
import user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private final String USERS_DATA_FILE_NAME = "users.txt";
    private ArrayList<User> users = new ArrayList<>();

    public void showMenu() {
        System.out.println("1. Create new user");
        System.out.println("2. Show all users");
        System.out.println("3. Add task to the user");
        System.out.println("4. Show user's tasks");
        System.out.println("5. Exit");
    }

    public void createUser() {

        String userName, firstName, lastName;
        Scanner createUserObj = new Scanner(System.in);

        System.out.print("Username:");
        boolean nameAlreadyExists = true;
        do {
            userName = createUserObj.nextLine();
            if (isUserNameExists(userName)) {
                System.out.println("User name already exists!");
                System.out.print("Try again: ");
            } else {
                nameAlreadyExists = false;
            }
        } while (nameAlreadyExists);

        System.out.print("First name: ");
        firstName = createUserObj.nextLine();

        System.out.print("Last name: ");
        lastName = createUserObj.nextLine();

        users.add(new User(firstName, lastName, userName, new ArrayList<Task>()));
        writeUsersDataInFile();
    }

    public void showUsers() {
        readUsersDataFromFile();
        if (users.size() == 0) {
            System.out.println("No users have been created yet!");
            return;
        }
        System.out.println("\n\n\033[01mFull Name\t\t\t Tasks\033[0m");
        for (User user : users) {
            System.out.println(user.getFullName() + " \t\t " + user.getNumberOfTasks());
        }
        users = null;
    }

    public void addTask() {
        readUsersDataFromFile();
        Scanner tasksDataObj = new Scanner(System.in);
        String userName, taskTitle, taskDescribtion;

        System.out.print("Enter user name: ");
        User user;
        do {
            userName = tasksDataObj.nextLine();
            user = getUserByUserName(userName);
            if (user == null) {
                System.out.println("Username doesn't exists!");
                System.out.print("Try again: ");
            }
        } while (user == null);

        System.out.print("Enter task title: ");
        taskTitle = tasksDataObj.nextLine();

        System.out.print("Enter task describtion: ");
        taskDescribtion = tasksDataObj.nextLine();

        user.getTasks().add(new Task(taskTitle, taskDescribtion));
        writeUsersDataInFile();
    }

    public void showUserTask() {
        readUsersDataFromFile();
        if (users.size() == 0) {
            System.out.println("No users have been created yet!");
            return;
        }
        Scanner tasksDataObj = new Scanner(System.in);

        String userName;
        User user;

        System.out.print("Enter user name: ");
        do {
            userName = tasksDataObj.nextLine();
            user = getUserByUserName(userName);
            if (user == null) {
                System.out.println("Username doesn't exists!");
                System.out.print("Try again: ");
            }
        } while (user == null);
        if (user.getTasks().size() == 0) {
            System.out.println("This user haven't tasks!");
        } else {
            printTasks(user);
        }
    }

    public boolean isUserNameExists(String userName) {
        readUsersDataFromFile();
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public User getUserByUserName(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    private void printTasks(User user) {
        int taskCounter = 1;
        ArrayList<Task> userTask = user.getTasks();
        System.out.println(user.getFullName() + " tasks: ");
        for (Task tasks : userTask) {
            System.out.println("Task " + taskCounter);
            System.out.println("Title: " + tasks.getTitle());
            System.out.println("Describtion: " + tasks.getDescribtion());
            System.out.println();
            taskCounter++;
        }
    }

    private void writeUsersDataInFile() {
        try {
            FileOutputStream file = new FileOutputStream(USERS_DATA_FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(users);

            out.close();
            file.close();

            System.out.println("New user was successfully added!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        users = null;
    }

    private void readUsersDataFromFile() {
        try {
            FileInputStream file = new FileInputStream(USERS_DATA_FILE_NAME);
            ObjectInputStream input = new ObjectInputStream(file);

            users = (ArrayList<User>) input.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
