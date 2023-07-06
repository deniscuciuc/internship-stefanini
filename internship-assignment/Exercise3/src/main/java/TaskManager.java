import menu.Menu;
import user.User;


import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {
    public void launch() {
        ArrayList<User> users = new ArrayList<>();
        Menu menu = new Menu();
        menu.showMenu();
        menu.readDataMySQL(users);
        Scanner scanner = new Scanner(System.in);
        int userChoice;
        do {
            System.out.print("\nSelect option: ");
            userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    menu.createUser(users);
                    break;
                case 2:
                    menu.showUsers(users);
                    break;
                case 3:
                    menu.addTask(users);
                    break;
                case 4:
                    menu.showUserTasks(users);
                    break;
                case 5:
                    System.out.println("Exiting from the program...");
                    menu.saveDataMySQL(users);
                    break;
            }
        } while (userChoice != 5);
    }
}
