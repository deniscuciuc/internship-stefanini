package menu;

import user.Task;
import user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    static final String url = "jdbc:mysql://localhost/task_management";
    static final String userName = "root";
    static final String password = "password";


    public void showMenu() {
        System.out.println("1. Create new user");
        System.out.println("2. Show all users");
        System.out.println("3. Add task to the user");
        System.out.println("4. Show user's tasks");
        System.out.println("5. Save and exit");
    }

    public void createUser(ArrayList<User> users) {
        String userName, firstName, lastName;
        Scanner scanner = new Scanner(System.in);

        System.out.print("User name: ");
        boolean nameAlreadyExists = true;
        do {
            userName = scanner.nextLine();
            if (isUserNameExists(users, userName)) {
                System.out.println("User name already exists!");
                System.out.print("Try again: ");
            } else {
                nameAlreadyExists = false;
            }
        } while (nameAlreadyExists);

        System.out.print("First name:");
        firstName = scanner.nextLine();

        System.out.print("Last name: ");
        lastName = scanner.nextLine();

        users.add(new User(firstName, lastName, userName, new ArrayList<Task>()));
    }

    public void showUsers(ArrayList<User> users) {
        if (users.size() == 0) {
            System.out.println("No users have been created yet!");
        } else {
            System.out.println("\n\n\033[01mFull Name\t\t\t Tasks\033[0m");
            for (User user : users) {
                System.out.println(user.getFullName() + " \t\t " + user.getNumberOfTasks());
            }
        }
    }

    public void addTask(ArrayList<User> users) {
        Scanner scanner = new Scanner(System.in);
        String userName, title, describtion;
        User user;

        System.out.print("Enter user name: ");
        do {
            userName = scanner.nextLine();
            user = getUserByUserName(users, userName);
            if (user == null) {
                System.out.println("Username doesn't exists!");
                System.out.print("Try again: ");
            }
        } while (user == null);

        System.out.print("Enter task title: ");
        title = scanner.nextLine();

        System.out.print("Enter task describtion: ");
        describtion = scanner.nextLine();

        user.getTasks().add(new Task(title, describtion));
    }

    public void showUserTasks(ArrayList<User> users) {
        Scanner scanner = new Scanner(System.in);
        String userName;
        User user;

        System.out.print("Enter user name: ");
        do {
            userName = scanner.nextLine();
            user = getUserByUserName(users, userName);
            if (user == null) {
                System.out.println("Username doesn't exists!");
                System.out.print("Try again: ");
            }
        } while (user == null);
        if (isUserHaveTasks(user)) {
            printTasks(user);
        } else {
            System.out.println("This user haven't tasks!");
        }
    }

    public boolean isUserNameExists(ArrayList<User> users, String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public User getUserByUserName(ArrayList<User> users, String userName) {
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
        System.out.println("\n" + user.getFullName() + " tasks: ");
        for (Task tasks : userTask) {
            System.out.println("Task №" + taskCounter + ":");
            System.out.println("Title: " + tasks.getTitle());
            System.out.println("Describtion: " + tasks.getDescribtion());
            System.out.println();
            taskCounter++;
        }
    }

    public boolean isUserHaveTasks(User user) {
        return user.getTasks().size() >= 1;
    }

    /* Exercise3/sqlCommandsTask3.sql - here is commands for creating database and tables */
    public void readDataMySQL(ArrayList<User> users) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, userName, password)) {
                Statement usersStatement = connection.createStatement();
                Statement tasksStatement = connection.createStatement();
                ResultSet usersSet = usersStatement.executeQuery("SELECT * FROM Users");
                
                    while (usersSet.next()) {
                        users.add(new User(usersSet.getString("FirstName"), usersSet.getString("LastName"),
                                usersSet.getString("UserName"), new ArrayList<Task>()));
                    }
                    
                ResultSet tasksSet = tasksStatement.executeQuery("SELECT * FROM Tasks");
                int usersIndex = 0;    
                while (tasksSet.next()) {
                    if (tasksSet.getString("performerUserName").equals(users.get(usersIndex).getUserName())) {
                        users.get(usersIndex).getTasks().add(new Task(tasksSet.getString("Title"),
                                tasksSet.getString("Describtion")));
                    }
                    usersIndex++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Connection failed...");
            System.out.println(e);
        }
    }

    public void saveDataMySQL(ArrayList<User> users) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, userName, password)) {

                /* Need this TRUNCATE operation because each time when we save data that was taken from database
                *  we duplicate data in arraylist, then we put in my sql duplicated fields */
                Statement clearUsersTable = connection.createStatement();
                Statement clearTasksTable = connection.createStatement();
                clearUsersTable.execute("TRUNCATE TABLE Users");
                clearTasksTable.execute("TRUNCATE TABLE Tasks");

                Statement usersStatement = connection.createStatement();
                Statement tasksStatement = connection.createStatement();
                for (User user : users) {
                    usersStatement.executeUpdate("INSERT Users(UserName, FirstName, LastName)" +
                            " VALUES ('" + user.getUserName() + "', '" + user.getFirstName() + "', '" + user.getLastName() + "')");
                    if (isUserHaveTasks(user)) {
                        ArrayList<Task> tasks = user.getTasks();
                        for (Task task : tasks) {
                            tasksStatement.executeUpdate("INSERT Tasks(Title, Describtion, performerUserName)" +
                                    " VALUES ('" + task.getTitle() + "', '" + task.getDescribtion() + "', '" + user.getUserName() + "')");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Connection failed...");
            System.out.println(e);
        }
    }
}
