package domain;

public class TaskEntity {
	private int id;
	private int userId;
    private String title;
    private String describtion;

    
    public TaskEntity() {
    	
    }
    
    public TaskEntity(int userId, int id, String title, String describtion) {
    	this.userId = userId;
    	this.id = id;
    	this.title = title;
    	this.describtion = describtion;
    }
    
    public TaskEntity(int id, String title, String describtion) {
    	this.id = id;
    	this.title = title;
    	this.describtion = describtion;
    }
    
    public TaskEntity(String title, String describtion) {
        this.title = title;
        this.describtion = describtion;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

}
