import menu.Menu;
import org.junit.jupiter.api.Test;
import user.Task;
import user.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuTest {

    @Test
    public void isUserHaveTasks() {
        Menu menuTest = new Menu();
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Spring Boot", "Learn more about Spring Boot and Hibernate"));
        tasks.add(new Task("React JS", "Learn more about React"));
        users.add(new User("Nick", "Diaz", "nick123", tasks));
        assertEquals(true, menuTest.isUserHaveTasks(users.get(0)));
    }

    @Test
    public void isUserNameExistsTest() {
        Menu menuTest = new Menu();
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Nick", "Diaz", "nick123", new ArrayList<Task>()));
        assertEquals(true, menuTest.isUserNameExists(users,"nick123"));
    }

    @Test
    public void getUserByUserNameTest() {
        Menu menuTest = new Menu();
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Nick", "Diaz", "nick123", new ArrayList<Task>()));
        users.add(new User("Nick", "Diaz", "nick321", new ArrayList<Task>()));
        assertEquals(users.get(1), menuTest.getUserByUserName(users,"nick321"));
    }
}
