package manager;

public class Managers {

    static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
