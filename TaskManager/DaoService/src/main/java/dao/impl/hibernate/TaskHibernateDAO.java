package dao.impl.hibernate;

import dao.TaskDAO;
import domain.TaskEntity;

import java.util.List;

public class TaskHibernateDAO implements TaskDAO {

    @Override
    public void createTask(TaskEntity task) {

    }

    @Override
    public List<TaskEntity> getAllUsersTasks(int userId) {
        return null;
    }
}
