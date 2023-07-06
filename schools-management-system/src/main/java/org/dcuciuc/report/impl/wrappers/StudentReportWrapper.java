package org.dcuciuc.report.impl.wrappers;

import org.dcuciuc.mark.Mark;
import org.dcuciuc.user.User;

import java.util.List;

public class StudentReportWrapper {
    private User user;
    private List<Mark> marks;

    public StudentReportWrapper(User user, List<Mark> marks) {
        this.user = user;
        this.marks = marks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }
}
