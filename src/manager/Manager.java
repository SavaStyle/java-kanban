package manager;

import java.util.HashMap;
import tasks.*;

public class Manager {
    private int nextId = 1;
    private HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epicks = new HashMap<>();

    public int getNextId() {
        return nextId++;
    }


    // Получение списка всех задач
    public HashMap printSimpleTask() {
        System.out.println(simpleTasks);
        return simpleTasks;
    }

    public HashMap printEpicTask() {
        System.out.println(epicks);
        return epicks;
    }

    public HashMap printSubTasks() {
        System.out.println(subTasks);
        return subTasks;
    }

    // Удаление всех задач
    public void cleanSimpleTask() {
        simpleTasks.clear();
    }

    public void cleanEpicTask() {
        epicks.clear();
    }

    public void cleanSubTask() {
        subTasks.clear();
    }

    // Получение задач по ID
    public SimpleTask getSimpleTaskById(int id) {
        SimpleTask simpleTask = simpleTasks.get(id);
        return simpleTask;
    }

    public Epic getEpicById(int id) {
        Epic epic = epicks.get(id);
        return epic;
    }

    public SubTask getSubTaskById(int id) {
        SubTask subTask = subTasks.get(id);
        return subTask;
    }

        // создание задач
    public void addST(SimpleTask task) {
       // task.setId(nextId++);
        simpleTasks.put(task.getId(), task);
    }

    public void addET(Epic task) {
      //  task.setId(nextId++);
       // updateEpicStatus(task);
        epicks.put(task.getId(), task);
    }

    public void addSET(SubTask task) {
       // task.setId(nextId++);
        subTasks.put(task.getId(), task);
    }

        // обновление задач
    public void updateSimpleTask(SimpleTask task) {
        simpleTasks.put(task.getId(), task);
    }

    public void updateEpicTask(Epic task) {
        epicks.put(task.getId(), task);
    }

    public void updateSubTask(SubTask task) {
        subTasks.put(task.getId(), task);
        Epic epic = epicks.get(task.getEpicId());
        updateEpicStatus(epic);
    }

    // Удаление задач по ID
    public void deleteSimpleTaskById(int id) {
        simpleTasks.remove(id);
    }

    public void deleteSubTaskById(int id) {
        SubTask task = getSubTaskById(id);
        Epic epic = epicks.get(task.getEpicId());
        subTasks.remove(id);
        epic.getSubTaskId().remove(id - 1);
        updateEpicStatus(epic);
    }

    public void deleteEpicById(int id) {
        Epic epic = epicks.get(id);
        for (Integer subTaskId: epic.getSubTaskId()) {
            subTasks.remove(subTaskId);
        }
        epicks.remove(id);
    }

    // Получение списка подзадач эпика
    public Object getListSubTasks(int id) {
        Epic epic = epicks.get(id);
        return epic.getSubTaskId();
    }

    // обновление статуса эпика
    private void updateEpicStatus(Epic task) {
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
            task.setStatus("NEW");
        } else {
            if ((( countMax / count ) == 1)) {
                task.setStatus("NEW");
            } else if (( count  /  countMax ) == 2) {
                task.setStatus("DONE");
            } else {
                task.setStatus("IN_PROGRESS");
            }
        }
    }


}



