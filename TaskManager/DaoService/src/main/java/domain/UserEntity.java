package domain;

import java.util.List;

public class UserEntity {
	private int id;
	private String firstName;
    private String lastName;
    private String userName;
    private List<TaskEntity> tasks;
    
    public UserEntity() {
    	
    }

    public UserEntity(int id, String firstName, String lastName, String userName, List<TaskEntity> tasks) {
    	this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.tasks = tasks;
    }
    
    public UserEntity(String firstName, String lastName, String userName, List<TaskEntity> tasks) {
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

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public String getFullName() {
        return getFirstName()+ " " + getLastName();
    }

    public int getNumberOfTasks() {
        return tasks.size();
    }
}
