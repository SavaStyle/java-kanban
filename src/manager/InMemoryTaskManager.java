package manager;

import tasks.*;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.Duration.between;

public class InMemoryTaskManager implements TaskManager {

    protected Map<Integer, SimpleTask> simpleTasks = new HashMap<>();

    protected Map<Integer, SubTask> subTasks = new HashMap<>();

    protected Map<Integer, Epic> epicks = new HashMap<>();

    protected HistoryManager managerHistory = Managers.getDefaultHistory();

    protected Set<Task> prioritizedTasks = new TreeSet<>((left, right) -> {
        if (left.getStartTime() == null) return 1;
        if (right.getStartTime() == null) return -1;
        return left.getStartTime().compareTo(right.getStartTime());
    });

    protected static int nextId = 1;

    @Override
    public int getNextId() {
        return nextId++;
    }

    // Получение списка всех задач
    @Override
    public Map<Integer, SimpleTask> getSimpleTask() {
        System.out.println(simpleTasks);
        return simpleTasks;
    }

    @Override
    public Map<Integer, Epic> getEpicTask() {
        System.out.println(epicks);
        return epicks;
    }

    @Override
    public Map<Integer, SubTask> getSubTasks() {
        System.out.println(subTasks);
        return subTasks;
    }

    // Удаление всех задач
    @Override
    public void cleanSimpleTask() {
        for (Map.Entry<Integer, SimpleTask> entry : simpleTasks.entrySet()) {
            SimpleTask simpleTask = entry.getValue();
            managerHistory.remove(simpleTask.getId());
            prioritizedTasks.remove(simpleTask);
        }
        simpleTasks.clear();
    }

    @Override
    public void cleanEpicTask() {
        for (Map.Entry<Integer, Epic> entry : epicks.entrySet()) {
            Epic epic = entry.getValue();
            managerHistory.remove(epic.getId());
        }
        epicks.clear();
    }

    @Override
    public void cleanSubTask() {
        for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
            SubTask subTask = entry.getValue();
            managerHistory.remove(subTask.getId());
            prioritizedTasks.remove(subTask);
        }
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
    public void addSimpleTask(SimpleTask task) {
        crossCheckAdd(task);
        simpleTasks.put(task.getId(), task);
    }

    @Override
    public void addEpicTask(Epic task) {
        updateEpicStatus(task);
        updateEpicTime(task);
        epicks.put(task.getId(), task);
    }

    @Override
    public void addSubEpicTask(SubTask task) {
        Epic epic = epicks.get(task.getEpicId());
        if (epic != null) {
            updateEpicStatus(epic);
        }
        crossCheckAdd(task);
        subTasks.put(task.getId(), task);
    }

    // обновление задач
    @Override
    public void updateSimpleTask(SimpleTask task) {
        crossCheckAdd(task);
        simpleTasks.put(task.getId(), task);
    }

    @Override
    public void updateEpicTask(Epic task) {
        updateEpicStatus(task);
        updateEpicTime(task);
        epicks.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(SubTask task) {
        subTasks.put(task.getId(), task);
        crossCheckAdd(task);
        Epic epic = epicks.get(task.getEpicId());
        if (epic != null) {
            updateEpicStatus(epic);
            updateEpicTime(epic);
        }

    }

    // Удаление задач по ID
    @Override
    public void deleteSimpleTaskById(int id) {
        SimpleTask simpleTask = getSimpleTaskById(id);
        prioritizedTasks.remove(simpleTask);
        managerHistory.remove(id);
        simpleTasks.remove(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        SubTask task = subTasks.get(id);
        prioritizedTasks.remove(task);
        Epic epic = epicks.get(task.getEpicId());
        subTasks.remove(id);
        managerHistory.remove(id);
        List<Integer> subTaskIds = epic.getSubTaskId();
        for (int i = 0; i < subTaskIds.size(); i++) {
            if (subTaskIds.get(i) == id) {
                subTaskIds.remove(i);
            }
        }
        updateEpicStatus(epic);
        updateEpicTime(epic);
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

    // обновление статуса эпика
    @Override
    public void updateEpicStatus(Epic task) {
        int count = 0;
        if (task.getSubTaskId() == null) {
            return;
        }
        int countMax = task.getSubTaskId().size();
        for (Integer subTask : task.getSubTaskId()) {
            SubTask sTask = subTasks.get(subTask);
            if (sTask != null) {
                if ((sTask.getStatus()).equals(Status.NEW)) {
                    count++;
                } else if ((sTask.getStatus()).equals(Status.DONE)) {
                    count = count + 100;
                } else {
                    count = count + 3;
                }
            } else continue;
        }

        if (task.getSubTaskId().isEmpty()) {
            task.setStatus(Status.NEW);
        } else {
            if (countMax == count) {
                task.setStatus(Status.NEW);
            } else if ((count / countMax) == 100) {
                task.setStatus(Status.DONE);
            } else {
                task.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    public List<Task> history() {
        return managerHistory.getHistory();
    }

    public Collection<SimpleTask> getSimpleTasks() {
        return simpleTasks.values();
    }

    public Collection<Epic> getEpicTasks() {
        return epicks.values();
    }

    public Collection<SubTask> getSubTask() {
        return subTasks.values();
    }


    public void updateEpicTime(Epic task) {
        LocalDateTime start = task.getStartTime();
        LocalDateTime finish = task.getEndTime();
        if (task.getSubTaskId() != null) {
            List<Integer> subTuskId = task.getSubTaskId();
            for (int idSubTusk : subTuskId) {
                SubTask subTask = subTasks.get(idSubTusk);
                LocalDateTime startSubTask = subTask.getStartTime();
                LocalDateTime finishSubTask = subTask.getEndTime();
                if (start == null) {
                    start = startSubTask;
                } else if (startSubTask != null && startSubTask.isBefore(start)) {
                    start = startSubTask;
                } else {
                    start = start;
                }

                if (finish == null) {
                    finish = finishSubTask;
                } else if (finishSubTask != null && finishSubTask.isAfter(finish)) {
                    finish = finishSubTask;
                } else {
                    finish = finish;
                }
            }
        } else {
            start = null;
            finish = null;
        }
        task.setStartTime(start);
        task.setEndTime(finish);
        if (start != null && finish != null) {
            task.setDuration((between(start, finish)));
        }
    }

    public void updateEpicTimeBySubTask(SubTask task) {
        Epic epic = epicks.get(task.getEpicId());
        LocalDateTime startEpic = epic.getStartTime();
        LocalDateTime finishEpic = epic.getEndTime();

        LocalDateTime startSubTask = task.getStartTime();
        LocalDateTime finishSubTask = task.getEndTime();
        if (startEpic == null) {
            startEpic = startSubTask;
        } else if (startSubTask != null && startSubTask.isBefore(startEpic)) {
            startEpic = startSubTask;
        } else {
            startEpic = startEpic;
        }
        if (finishEpic == null) {
            finishEpic = finishSubTask;
        } else if (finishSubTask != null && finishSubTask.isAfter(finishEpic)) {
            finishEpic = finishSubTask;
        } else {
            finishEpic = finishEpic;
        }
        epic.setStartTime(startEpic);
        epic.setEndTime(finishEpic);
        if (startEpic != null && finishEpic != null) {
            epic.setDuration((between(startEpic, finishEpic)));
        }
    }

    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    public void crossCheckAdd(Task task) {
        if (task.getStartTime() == null || prioritizedTasks.size() == 0) {
            prioritizedTasks.add(task);
        } else {
            Task[] array = prioritizedTasks.toArray(new Task[prioritizedTasks.size()]);
            if (task.getStartTime().isAfter(array[array.length - 1].getEndTime())) {
                prioritizedTasks.add(task);
            } else {
                for (int i = 0; i < array.length; i++) {
                    if (task.getStartTime().isBefore(array[i].getStartTime())) {
                        if (task.getEndTime().isBefore(array[i].getStartTime())) {
                            prioritizedTasks.add(task);
                            break;
                        } else if (task.getEndTime().isAfter(array[i].getStartTime())) {
                            System.out.println("ошибка, задача не может быть добавлена т.к. данное время занято");
                            break;
                        }
                    }
                }
            }
        }
    }
}

