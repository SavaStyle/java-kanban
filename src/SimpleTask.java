public class SimpleTask extends Task {


    public SimpleTask(int id, String name, String description, String status) {
        super(id, name, description, status);
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
