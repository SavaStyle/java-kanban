package manager;

import tasks.Epic;
import tasks.SimpleTask;
import tasks.SubTask;

import java.util.List;
import java.util.Map;

public interface TaskManager {

    int getNextId();

    // Получение списка всех задач
    Map<Integer, SimpleTask> printSimpleTask();

    Map<Integer, Epic> printEpicTask();

    Map<Integer, SubTask> printSubTasks();

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

    // Получение списка подзадач эпика
    List<Integer> getListSubTasks(int id);

    // обновление статуса эпика
    void updateEpicStatus(Epic task);
}