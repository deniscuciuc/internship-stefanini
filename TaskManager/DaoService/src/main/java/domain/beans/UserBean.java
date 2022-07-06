package domain.beans;



import java.util.List;
import java.util.Set;

/**
 * Simple user bean class, that is used for hibernate mappings configuration. Allows not to use annotations.
 * Used one-to-many relationship with tasks.
 * @author dcuciuc
 */
public class UserBean {
	private int id;
	private String firstName;
    private String lastName;
    private String userName;
    private Set<TaskBean> tasks;

    public UserBean() {

    }

    public UserBean(int id, String firstName, String lastName, String userName, Set<TaskBean> tasks) {
    	this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.tasks = tasks;
    }

    public UserBean(String firstName, String lastName, String userName, Set<TaskBean> tasks) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.tasks = tasks;
    }
    

    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
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

    public Set<TaskBean> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskBean> tasks) {
        this.tasks = tasks;
    }

    public String getFullName() {
        return getFirstName()+ " " + getLastName();
    }

    public int getNumberOfTasks() {
        return tasks.size();
    }
}
