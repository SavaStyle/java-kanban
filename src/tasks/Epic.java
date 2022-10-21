package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
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

    public Epic() {
        super(null);
    }

    public Epic(int id, String name, String description, Status status, LocalDateTime startTime, Duration duration, LocalDateTime endTime) {
        super(id, name, description, status);
        this.startTime = null;
        this.duration = null;
        this.endTime = null;
    }


    public List<Integer> getSubTaskId() {
        return subTaskIds;
    }

    @Override
    public String toString() {
        return id + "," +
                TasksType.EPIC + "," +
                name + "," +
                status + "," +
                description + "," +
                startTime + "," +
                duration;
    }

}