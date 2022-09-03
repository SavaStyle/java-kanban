import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        InMemoryTaskManager manager = new InMemoryTaskManager();
        InMemoryHistoryManager managerHistory = new InMemoryHistoryManager();
        testCreation(manager, managerHistory);
    }

    public static void testCreation(InMemoryTaskManager manager, InMemoryHistoryManager managerHistory) {
        ArrayList<Integer> epic1 = new ArrayList<>();
        ArrayList<Integer> epic2 = new ArrayList<>();

        // создаем подзадачи
        System.out.println("создаем подзадачи!");
        SubTask subTask1 = new SubTask(manager.getNextId(), "subtask1", "subtask1", Status.NEW, 4);
        SubTask subTask2 = new SubTask(manager.getNextId(), "subtask2", "subtask2", Status.IN_PROGRESS, 4);
        SubTask subTask3 = new SubTask(manager.getNextId(), "subtask3", "subtask3", Status.DONE, 4);

        epic1.add(subTask1.getId());
        epic1.add(subTask2.getId());
        epic1.add(subTask3.getId());

        // создаем эпик и добавляем в него подзадачи
        System.out.println("создаем эпик и добавляем в него подзадачи!");
        Epic epic11 = new Epic(manager.getNextId(), "epic1", "epic1", Status.NEW, epic1);
        Epic epic22 = new Epic(manager.getNextId(), "epic2", "epic2", Status.NEW, epic2);

        // добавляем задачи в менеджер
        System.out.println("добавляем задачи в менеджер!");
        manager.addSET(subTask1);
        manager.addSET(subTask2);
        manager.addSET(subTask3);
        manager.addET(epic11);
        manager.addET(epic22);

        // печать списка эпика и подзадач
        System.out.println("печать списка эпика и подзадач!");
        manager.printSubTasks();
        manager.printEpicTask();

        // заполняем лист истории просмотра
        manager.getSubTaskById(1);
        manager.getSubTaskById(2);
        manager.getSubTaskById(3);
        manager.getEpicById(4);
        manager.getEpicById(5);
        manager.getSubTaskById(1);
        manager.getSubTaskById(1);
        manager.getEpicById(5);

        //удаление эпика
        System.out.println("удаление эпика!");
        manager.deleteEpicById(4);
        manager.printSubTasks();
        manager.printEpicTask();

        //История
        System.out.println("история");
        System.out.println(manager.history());
    }
}


