package user;


public class Task {
    private String title;
    private String describtion;

    public Task(String title, String describtion) {
        this.title = title;
        this.describtion = describtion;
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
