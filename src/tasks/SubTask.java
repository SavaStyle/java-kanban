package tasks;
public class SubTask  extends Task {
    private int epicId;

    public SubTask(int id, String name, String description, Status status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int colId) {
        this.epicId = colId;
    }

}
