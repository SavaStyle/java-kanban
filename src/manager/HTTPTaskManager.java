package manager;

import exception.ManagerSaveException;
import KVServer.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tasks.Epic;
import tasks.SimpleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static KVServer.HttpTaskServer.getGson;

public class HTTPTaskManager extends FileBackedTasksManager {

    private final Gson gson;
    private final KVTaskClient kvTaskClient;

    public HTTPTaskManager(URI uri) {
        this.gson = getGson();
        try {
            this.kvTaskClient = new KVTaskClient(uri);
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Ошибка подключения KVServer");
        }
    }



    public void load() {
        try {
            Map<Integer, SimpleTask> simpleTasks = gson.fromJson(kvTaskClient.load("simpleTasks"), new TypeToken<HashMap<Integer, SimpleTask>>() {
            }.getType());
            Map<Integer, SubTask> subTasks = gson.fromJson(kvTaskClient.load("subtasks"), new TypeToken<HashMap<Integer, SubTask>>() {
            }.getType());
            Map<Integer, Epic> epicks = gson.fromJson(kvTaskClient.load("epicks"), new TypeToken<HashMap<Integer, Epic>>() {
            }.getType());
            int nextId = Integer.parseInt(kvTaskClient.load("nextId"));

            List<Task> historyList = gson.fromJson(kvTaskClient.load("history"), new TypeToken<List<Task>>() {
            }.getType());
            for (Task task : historyList) {
                managerHistory.add(task);
            }
            this.simpleTasks = simpleTasks;
            this.epicks = epicks;
            this.subTasks = subTasks;
            this.prioritizedTasks.addAll(simpleTasks.values());
            this.prioritizedTasks.addAll(subTasks.values());
            InMemoryTaskManager.nextId = nextId;
        } catch (IOException | InterruptedException exception) {
            System.out.println("Ошибка загрузки");
        }
    }

    @Override
    public void save() {
        try {
            kvTaskClient.put("simpleTasks", gson.toJson(simpleTasks));
            kvTaskClient.put("subtasks", gson.toJson(subTasks));
            kvTaskClient.put("epicks", gson.toJson(epicks));
            kvTaskClient.put("history", gson.toJson(history()));
            kvTaskClient.put("nextId", gson.toJson(getNextId()));
        } catch (IOException | InterruptedException err) {
            throw new ManagerSaveException("Ошибка при сохранении данных");
        }
    }
}
