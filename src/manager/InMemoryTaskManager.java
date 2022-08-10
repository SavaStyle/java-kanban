package manager;

import java.util.HashMap;

import tasks.*;

public class InMemoryTaskManager implements TaskManager {
    private int nextId = 1;
    private HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epicks = new HashMap<>();

    InMemoryHistoryManager managerHistory = new InMemoryHistoryManager();

    @Override
    public int getNextId() {
        return nextId++;
    }



    // Получение списка всех задач
    @Override
    public HashMap printSimpleTask() {
        System.out.println(simpleTasks);
        return simpleTasks;
    }
    @Override
    public HashMap printEpicTask() {
        System.out.println(epicks);
        return epicks;
    }
    @Override
    public HashMap printSubTasks() {
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
       // task.setId(nextId++);
        simpleTasks.put(task.getId(), task);
    }
    @Override
    public void addET(Epic task) {
      //  task.setId(nextId++);
       // updateEpicStatus(task);
        epicks.put(task.getId(), task);
    }
    @Override
    public void addSET(SubTask task) {
       // task.setId(nextId++);
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
        simpleTasks.remove(id);
    }
    @Override
    public void deleteSubTaskById(int id) {
        SubTask task = subTasks.get(id);
        Epic epic = epicks.get(task.getEpicId());
        subTasks.remove(id);
        epic.getSubTaskId().remove(id - 1);
        updateEpicStatus(epic);
    }
    @Override
    public void deleteEpicById(int id) {
        Epic epic = epicks.get(id);
        for (Integer subTaskId: epic.getSubTaskId()) {
            subTasks.remove(subTaskId);
        }
        epicks.remove(id);
    }

    // Получение списка подзадач эпика
    @Override
    public Object getListSubTasks(int id) {
        Epic epic = epicks.get(id);
        return epic.getSubTaskId();
    }

    // обновление статуса эпика
    @Override
    public void updateEpicStatus(Epic task) {
        int count = 0;
        int countMax = task.getSubTaskId().size();
        for (Integer subTask: task.getSubTaskId()) {
            SubTask sTask =  subTasks.get(subTask);
            if (sTask != null)  {
                if ((sTask.getStatus()).equals("NEW")) {
                    count++;
                 } else if ((sTask.getStatus()).equals("DONE")) {
                    count =  count + 2;
                 } else {
                    count = count +3;
                }
            } else continue;
        }

        if (task.getSubTaskId().isEmpty()) {
            task.setStatus(Status.NEW);
        } else {
            if ((( countMax / count ) == 1)) {
                task.setStatus(Status.NEW);
            } else if (( count  /  countMax ) == 2) {
                task.setStatus(Status.DONE);
            } else {
                task.setStatus(Status.IN_PROGRESS);
            }
        }
    }

}



