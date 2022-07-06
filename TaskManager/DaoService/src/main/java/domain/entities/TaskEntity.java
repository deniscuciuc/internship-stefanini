package domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    
    public TaskEntity() {
    	
    }
    
    public TaskEntity(int id, String title, String description, UserEntity user) {
    	this.id = id;
    	this.title = title;
    	this.description = description;
        this.user = user;
    }
    
    public TaskEntity(int id, String title, String description) {
    	this.id = id;
    	this.title = title;
    	this.description = description;
    }
    
    public TaskEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
