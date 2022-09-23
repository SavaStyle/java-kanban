package tasks;

import static tasks.TasksType.*;

public abstract class Task {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
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
        return id + "," +
                name + "," +
                status + "," +
                description;
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
}
