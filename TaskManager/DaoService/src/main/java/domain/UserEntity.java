package domain;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id"})})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, unique = true)
    private int id;

    @Column(name = "firstName", length = 20, nullable = false)
	private String firstName;

    @Column(name = "lastName", length = 20, nullable = false)
    private String lastName;

    @Column(name = "userName", length = 20, nullable = false, unique = true)
    private String userName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TaskEntity> tasks;
    
    public UserEntity() {
    	
    }

    public UserEntity(int id, String firstName, String lastName, String userName, Set<TaskEntity> tasks) {
    	this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.tasks = tasks;
    }
    
    public UserEntity(String firstName, String lastName, String userName, Set<TaskEntity> tasks) {
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

    public Set<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public String getFullName() {
        return getFirstName()+ " " + getLastName();
    }

    public int getNumberOfTasks() {
        return tasks.size();
    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", numberOfTasks='" + tasks.size() + '\'' +
                '}';
    }
}
