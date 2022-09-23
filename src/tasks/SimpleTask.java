package tasks;

public class SimpleTask extends Task {

    public SimpleTask(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    @Override
    public String toString() {
        return id + "," +
                TasksType.SIMPLETASK + "," +
                name + "," +
                status + "," +
                description;
    }
}
