package user;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String userName;
    private ArrayList<Task> tasks;

    public User(String firstName, String lastName, String userName, ArrayList<Task> tasks) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.tasks = tasks;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public String getFullName() {
        return getFirstName()+ " " + getLastName();
    }

    public int getNumberOfTasks() {
        return getTasks().size();
    }
}
