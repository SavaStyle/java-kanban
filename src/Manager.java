import java.util.HashMap;

public class Manager {
    private int nextId = 1;
    private HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epicks = new HashMap<>();

    // Получение списка всех задач
    public Object printSimpleTask() {
        System.out.println(simpleTasks);
        return simpleTasks;
    }

    public Object printEpicTask() {
        System.out.println(epicks);
        return epicks;
    }

    public Object printSubTasks() {
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
    public Object getSimpleTaskById(int id) {
        Object object = simpleTasks.get(id);
        return object;
    }

    public Object getEpicById(int id) {
        Object object = epicks.get(id);
        return object;
    }

    public Object getSubTaskById(int id) {
        SubTask subTask = subTasks.get(id);
        return subTask;
    }

        // создание задач
    public void addST(SimpleTask task) {
        task.setId(nextId++);
        simpleTasks.put(task.getId(), task);
    }

    public void addET(Epic task) {
        task.setId(nextId++);
        updateEpicStatus(task);
        epicks.put(task.getId(), task);
    }

    public void addSET(SubTask task) {
        task.setId(nextId++);
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
        subTasks.remove(id);
    }

    public void deleteEpicById(int id) {
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

        for (Integer subTask: task.getSubTaskId()) {
            SubTask sTask =  subTasks.get(subTask);
            if ((sTask.getStatus()).equals("NEW")) {
                count++;
            } else if ((sTask.getStatus()).equals("DONE")) {
                count =  count + 2;
            }
        }

        if (task.subTaskIds.isEmpty()) {
            task.setStatus("NEW");
        } else {
            if (count / task.subTaskIds.size() == 1) {
                task.setStatus("NEW");
            } else if (count / task.subTaskIds.size() == 2) {
                task.setStatus("Done");
            } else {
                task.setStatus("IN_PROGRESS");
            }
        }


    }


}



