package tasks;

import java.util.ArrayList;

public class Epic  extends Task {
    private ArrayList<Integer> subTaskIds;

    public Epic(int id, String name, String description, String status, ArrayList<Integer> subTaskIds) {
        super(id, name, description, status);
        this.subTaskIds = subTaskIds;
    }
    public  void addSubTask(int subTaskId) {
        this.subTaskIds.add(subTaskId);
    }


    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }

    public ArrayList<Integer> getSubTaskId() {
        return subTaskIds;
    }

    public void setSubTaskId(ArrayList<Integer> subTaskId) {
        this.subTaskIds = subTaskId;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTaskIds=" + subTaskIds +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
