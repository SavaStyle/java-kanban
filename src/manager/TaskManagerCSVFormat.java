package manager;

import tasks.*;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerCSVFormat {

    public static String getHeader() {
        return "id,type,name,status,description,epic" + System.lineSeparator();
    }

    public static Task fromString(String line) {
        String[] lines = line.split(",");
        int id = Integer.parseInt(lines[0]);
        String TaskType = lines[1];
        String name = lines[2];
        Status status = Status.valueOf(lines[3]);
        String description = lines[4];
        Integer epicId = null;
        if (lines.length == 6) {
            epicId = Integer.parseInt(lines[5]);
        }

        switch (TasksType.valueOf(TaskType)) {
            case EPIC: {
                return new Epic(id, name, description, status);
            }
            case SIMPLETASK: {
                return new SimpleTask(id, name, description, status);
            }
            case SUBTASK: {
                return new SubTask(id, name, description, status, epicId);
            }
        }
        return null;
    }

    public static String taskToString(TaskManager manager) {
        StringBuilder sb = new StringBuilder();
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(manager.getSimpleTasks());
        tasks.addAll(manager.getEpicTasks());
        tasks.addAll(manager.getSubTask());
        for (Task task : tasks) {
            sb.append(task.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}