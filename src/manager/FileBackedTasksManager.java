package manager;

import tasks.Epic;
import tasks.SimpleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {

    public File file;

    public FileBackedTasksManager() {
    }

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    static Map<Integer, Task> historyMap = new HashMap<>();

    // восстановление таск менеджера после запуска программы
    public static FileBackedTasksManager loadFromFile(Path path) {

        FileBackedTasksManager tasksManager = new FileBackedTasksManager();

        //считывание файла
        try {
            String file = Files.readString(path);
            String[] lines = file.split(System.lineSeparator());

            // разбивка тасков
            int generatorID = 0;

            for (int i = 1; i < lines.length - 2; i++) {
                String line = lines[i];
                Task task = TaskManagerCSVFormat.fromString(line);

                int id = task.getId();
                if (id > generatorID) {
                    generatorID = id;
                }

                String[] types = line.split(",");
                String type = types[1];
                tasksManager.addTask(task, type);
            }

            String historyLine = lines[lines.length - 1];
            String[] historyString = historyLine.split(",");


            for (int j = 0; j < historyString.length; j++) {
                int taskID = Integer.parseInt(historyString[j]);
                Task taskHistory = historyMap.get(taskID);
                managerHistory.add(taskHistory);
            }

            updateNextID(generatorID);

        } catch (IOException err) {
            throw new ManagerSaveException("Ошибка при восстановлении данных");
        }
        return tasksManager;
    }

    private void addTask(Task task, String type) {

        switch (Task.getType(task, type)) {
            case EPIC: {
                epicks.put(task.getId(), (Epic) task);
                historyMap.put(task.getId(), task);
                break;
            }
            case SIMPLETASK: {
                simpleTasks.put(task.getId(), (SimpleTask) task);
                historyMap.put(task.getId(), task);
                break;
            }
            case SUBTASK: {
                subTasks.put(task.getId(), (SubTask) task);
                historyMap.put(task.getId(), task);

            }
        }
    }

    // сохранение таскМенеджера
    public void save() {
        StringBuilder sb = new StringBuilder();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("save.csv", StandardCharsets.UTF_8))) {
            // записать в файл заголовок
            writer.write(TaskManagerCSVFormat.getHeader());
            writer.write(TaskManagerCSVFormat.taskToString(this));
            for (Task task : history()) {
                sb.append(task.getId() + ",");
            }
            writer.write(System.lineSeparator() + sb.toString());
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохраненя");
        }
    }

    @Override
    public Map<Integer, SimpleTask> printSimpleTask() {
        return super.printSimpleTask();
    }

    @Override
    public Map<Integer, Epic> printEpicTask() {
        return super.printEpicTask();

    }

    @Override
    public Map<Integer, SubTask> printSubTasks() {
        return super.printSubTasks();
    }

    @Override
    public void cleanSimpleTask() {
        super.cleanSimpleTask();
        save();
    }

    @Override
    public void cleanEpicTask() {
        super.cleanEpicTask();
        save();
    }

    @Override
    public void cleanSubTask() {
        super.cleanSubTask();
        save();
    }

    @Override
    public SimpleTask getSimpleTaskById(int id) {
        SimpleTask simpleTask = super.getSimpleTaskById(id);
        save();
        return simpleTask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = super.getSubTaskById(id);
        save();
        return subTask;
    }

    @Override
    public void addSimpleTask(SimpleTask task) {
        super.addSimpleTask(task);
        save();
    }

    @Override
    public void addEpicTask(Epic task) {
        super.addEpicTask(task);
        save();
    }

    @Override
    public void addSubEpicTask(SubTask task) {
        super.addSubEpicTask(task);
        save();
    }

    @Override
    public void updateSimpleTask(SimpleTask task) {
        super.updateSimpleTask(task);
        save();
    }

    @Override
    public void updateEpicTask(Epic task) {
        super.updateEpicTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask task) {
        super.updateSubTask(task);
        save();
    }

    @Override
    public void deleteSimpleTaskById(int id) {
        super.deleteSimpleTaskById(id);
        save();
    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public List<Integer> getListSubTasks(int id) {
        return super.getListSubTasks(id);
    }

    @Override
    public void updateEpicStatus(Epic task) {
        super.updateEpicStatus(task);
        save();
    }

    @Override
    public List<Task> history() {
        return super.history();
    }

    public static void updateNextID(Integer id) {
        nextId = id + 1;
    }
}

