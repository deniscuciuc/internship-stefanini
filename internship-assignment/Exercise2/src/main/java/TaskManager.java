import menu.Menu;


import java.util.Scanner;

public class TaskManager {
    public void launch() {
        Menu menu = new Menu();
        menu.showMenu();
        Scanner choiceObj = new Scanner(System.in);
        int userChoice;
        do {
            System.out.print("\nSelect option: ");
            userChoice = choiceObj.nextInt();
            switch (userChoice) {
                case 1:
                    menu.createUser();
                    break;
                case 2:
                    menu.showUsers();
                    break;
                case 3:
                    menu.addTask();
                    break;
                case 4:
                    menu.showUserTask();
                    break;
                case 5:
                    System.out.println("Exiting from the program...");
                    break;
            }
        } while (userChoice != 5);
    }
}
