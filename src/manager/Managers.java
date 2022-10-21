package manager;

import java.io.IOException;
import java.net.URI;

public class Managers {

    public static TaskManager getDefault() throws IOException, InterruptedException {
        return new HTTPTaskManager(URI.create("http://localhost:8078"));
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}