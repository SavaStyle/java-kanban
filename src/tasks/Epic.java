package tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    protected List<Integer> subTaskIds;

    public Epic(int id, String name, String description, Status status, ArrayList<Integer> subTaskIds) {
        super(id, name, description, status);
        this.subTaskIds = subTaskIds;
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public void addSubTask(int subTaskId) {
        subTaskIds.add(subTaskId);
    }

    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }

    public List<Integer> getSubTaskId() {
        return subTaskIds;
    }

    public void setSubTaskId(ArrayList<Integer> subTaskId) {
        this.subTaskIds = subTaskId;
    }

    public List<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    @Override
    public String toString() {
        return id + "," +
                TasksType.EPIC + "," +
                name + "," +
                status + "," +
                description;
    }
}