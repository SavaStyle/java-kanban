package manager;

import tasks.*;

import java.util.List;

public interface HistoryManager {

    public void add(Task task);

    public List<Task> getHistory();



}