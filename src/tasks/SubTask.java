package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    protected int epicId;

    public SubTask(int id, String name, String description, Status status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public SubTask() {
        super(null);
    }

    public SubTask(int id, String name, String description, Status status, LocalDateTime startTime, Duration duration, int epicId) {
        super(id, name, description, status, startTime, duration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return id + "," +
                TasksType.SUBTASK + "," +
                name + "," +
                status + "," +
                description + "," +
                startTime + "," +
                duration + "," +
                epicId;
    }


}
