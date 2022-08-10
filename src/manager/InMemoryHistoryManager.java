package manager;

import tasks.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    public List<Task> history = new ArrayList<>();

    public List<Task> getHistory(){
        return history;
    }

    public void add(Task task) {
        if (history.size() < 10) {
            history.add(task);
        } else {
            history.remove(0);
            history.add(task);
        }
    }

}

