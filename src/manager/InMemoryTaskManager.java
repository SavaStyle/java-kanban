package manager;

import tasks.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, SimpleTask> simpleTasks = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final Map<Integer, Epic> epicks = new HashMap<>();
    private final InMemoryHistoryManager managerHistory = new InMemoryHistoryManager();
    private int nextId = 1;

    @Override
    public int getNextId() {
        return nextId++;
    }

    // Получение списка всех задач
    @Override
    public Map<Integer, SimpleTask> printSimpleTask() {
        System.out.println(simpleTasks);
        return simpleTasks;
    }

    @Override
    public Map<Integer, Epic> printEpicTask() {
        System.out.println(epicks);
        return epicks;
    }

    @Override
    public Map<Integer, SubTask> printSubTasks() {
        System.out.println(subTasks);
        return subTasks;
    }

    // Удаление всех задач
    @Override
    public void cleanSimpleTask() {
        simpleTasks.clear();
    }

    @Override
    public void cleanEpicTask() {
        epicks.clear();
    }

    @Override
    public void cleanSubTask() {
        subTasks.clear();
    }

    // Получение задач по ID
    @Override
    public SimpleTask getSimpleTaskById(int id) {
        SimpleTask simpleTask = simpleTasks.get(id);
        managerHistory.add(simpleTask);
        return simpleTask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epicks.get(id);
        managerHistory.add(epic);
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = subTasks.get(id);
        managerHistory.add(subTask);
        return subTask;
    }

    // создание задач
    @Override
    public void addST(SimpleTask task) {
        simpleTasks.put(task.getId(), task);
    }

    @Override
    public void addET(Epic task) {
        epicks.put(task.getId(), task);
    }

    @Override
    public void addSET(SubTask task) {
        subTasks.put(task.getId(), task);
    }

    // обновление задач
    @Override
    public void updateSimpleTask(SimpleTask task) {
        simpleTasks.put(task.getId(), task);
    }

    @Override
    public void updateEpicTask(Epic task) {
        epicks.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(SubTask task) {
        subTasks.put(task.getId(), task);
        Epic epic = epicks.get(task.getEpicId());
        updateEpicStatus(epic);
    }

    // Удаление задач по ID
    @Override
    public void deleteSimpleTaskById(int id) {
        managerHistory.remove(id);
        simpleTasks.remove(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        SubTask task = subTasks.get(id);
        Epic epic = epicks.get(task.getEpicId());
        subTasks.remove(id);
        managerHistory.remove(id);
        epic.getSubTaskId().remove(id - 1);
        updateEpicStatus(epic);
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epicks.get(id);
        for (Integer subTaskId : epic.getSubTaskId()) {
            subTasks.remove(subTaskId);
            managerHistory.remove(subTaskId);
        }
        managerHistory.remove(id);
        epicks.remove(id);
    }

    // Получение списка подзадач эпика
    @Override
    public List<Integer> getListSubTasks(int id) {
        Epic epic = epicks.get(id);
        return epic.getSubTaskId();
    }

    // обновление статуса эпика
    @Override
    public void updateEpicStatus(Epic task) {
        int count = 0;
        int countMax = task.getSubTaskId().size();
        for (Integer subTask : task.getSubTaskId()) {
            SubTask sTask = subTasks.get(subTask);
            if (sTask != null) {
                if ((sTask.getStatus()).equals("NEW")) {
                    count++;
                } else if ((sTask.getStatus()).equals("DONE")) {
                    count = count + 2;
                } else {
                    count = count + 3;
                }
            } else continue;
        }

        if (task.getSubTaskId().isEmpty()) {
            task.setStatus(Status.NEW);
        } else {
            if (countMax / count == 1) {
                task.setStatus(Status.NEW);
            } else if ((count / countMax) == 2) {
                task.setStatus(Status.DONE);
            } else {
                task.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    public List<Task> history() {
        return managerHistory.getHistory();
    }
}