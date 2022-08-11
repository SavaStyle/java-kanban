package manager;

public class Managers {

    static TaskManager getDefault() {

        TaskManager manager = new InMemoryTaskManager();

        return manager;
    }

    static InMemoryHistoryManager getDefaultHistory() {

        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        return inMemoryHistoryManager;
    }
}
