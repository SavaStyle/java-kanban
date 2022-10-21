package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class SimpleTask extends Task {

    public SimpleTask(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public SimpleTask() {
        super(null);
    }

    public SimpleTask(int id, String name, String description, Status status, LocalDateTime startTime, Duration duration) {
        super(id, name, description, status, startTime, duration);
    }

    @Override
    public String toString() {
        return id + "," +
                TasksType.SIMPLETASK + "," +
                name + "," +
                status + "," +
                description + "," +
                startTime + "," +
                duration;
    }
}
