package domain.beans;

/**
 * Simple user bean class, that is used for hibernate mappings configuration. Allows not to use annotations.
 * Used many-to-one relationship with user.
 * @author dcuciuc
 */
public class TaskBean {
	private int id;
    private String title;
    private String description;
    private UserBean user;


    public TaskBean() {

    }

    public TaskBean(int id, String title, String description, UserBean user) {
    	this.id = id;
    	this.title = title;
    	this.description = description;
        this.user = user;
    }

    public TaskBean(int id, String title, String description) {
    	this.id = id;
    	this.title = title;
    	this.description = description;
    }

    public TaskBean(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
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
