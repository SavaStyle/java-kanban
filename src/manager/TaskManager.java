package manager;

import tasks.Epic;
import tasks.SimpleTask;
import tasks.SubTask;
import tasks.Task;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskManager {

    int getNextId();

    // Получение списка всех задач
    Map<Integer, SimpleTask> getSimpleTask();

    Map<Integer, Epic> getEpicTask();

    Map<Integer, SubTask> getSubTasks();

    // Удаление всех задач
    void cleanSimpleTask();

    void cleanEpicTask();

    void cleanSubTask();

    // Получение задач по ID
    SimpleTask getSimpleTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    // создание задач
    void addSimpleTask(SimpleTask task);

    void addEpicTask(Epic task);

    void addSubEpicTask(SubTask task);

    // обновление задач
    void updateSimpleTask(SimpleTask task);

    void updateEpicTask(Epic task);

    void updateSubTask(SubTask task);

    // Удаление задач по ID
    void deleteSimpleTaskById(int id);

    void deleteSubTaskById(int id);

    void deleteEpicById(int id);

    // обновление статуса эпика
    void updateEpicStatus(Epic task);

    List<Task> history();

    Collection<SimpleTask> getSimpleTasks();

    Collection<Epic> getEpicTasks();

    Collection<SubTask> getSubTask();

    void updateEpicTime(Epic epic);

    void updateEpicTimeBySubTask(SubTask task);

    Set<Task> getPrioritizedTasks();

    void crossCheckAdd(Task task);
}