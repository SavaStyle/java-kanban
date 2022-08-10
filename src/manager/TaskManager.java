package manager;

import java.util.HashMap;
import java.util.List;

import tasks.*;

public interface TaskManager {

    public int getNextId();

    // Получение списка всех задач
    public HashMap printSimpleTask() ;

    public HashMap printEpicTask();

    public HashMap printSubTasks();

    // Удаление всех задач
    public void cleanSimpleTask();

    public void cleanEpicTask();

    public void cleanSubTask();

    // Получение задач по ID
    public SimpleTask getSimpleTaskById(int id);

    public Epic getEpicById(int id);

    public SubTask getSubTaskById(int id);

    // создание задач
    public void addST(SimpleTask task);

    public void addET(Epic task);

    public void addSET(SubTask task);

    // обновление задач
    public void updateSimpleTask(SimpleTask task);

    public void updateEpicTask(Epic task);

    public void updateSubTask(SubTask task);

    // Удаление задач по ID
    public void deleteSimpleTaskById(int id);

    public void deleteSubTaskById(int id);

    public void deleteEpicById(int id);

    // Получение списка подзадач эпика
    public Object getListSubTasks(int id);

    // обновление статуса эпика
    public void updateEpicStatus(Epic task);

   // public List<Task> getHistory();



}



