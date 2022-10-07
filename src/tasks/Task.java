package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static tasks.TasksType.*;

public abstract class Task implements Comparable<Task> {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;

    protected LocalDateTime startTime;

    protected Duration duration;

    protected LocalDateTime endTime;

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(int id,String name,String description, Status status, LocalDateTime startTime, Duration duration){
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public static TasksType getType(Task task, String type) {

        switch (TasksType.valueOf(type)) {
            case EPIC: {
                return EPIC;
            }
            case SIMPLETASK: {
                return SIMPLETASK;
            }
            case SUBTASK: {
                return SUBTASK;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", type='" + name + '\'' +
                ", name='" + description + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", EndTime=" + endTime +
                '}';
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        if (startTime != null && duration != null) {
            endTime = startTime.plus(duration);
        } else {
            endTime = null;
        }
         return endTime;
    }

    @Override
    public int compareTo(Task o) {
        return (this.startTime.isBefore(o.startTime)) ? -1 : ((this.startTime.equals(o.startTime)) ? 0 : 1);
    }
}
