import java.util.ArrayList;
import manager.InMemoryTaskManager;
import manager.InMemoryHistoryManager;
import tasks.*;

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
        SubTask subTask3 = new SubTask(manager.getNextId(), "subtask3", "subtask3", Status.DONE, 5);

        epic1.add(subTask1.getId());
        epic1.add(subTask2.getId());
        epic2.add(subTask3.getId());

        // создаем эпик и добавляем в него подзадачи
        System.out.println("создаем эпик и добавляем в него подзадачи!");
        Epic epic11 = new Epic(manager.getNextId(), "epic1", "epic1", Status.NEW, epic1);
        Epic epic22 = new Epic(manager.getNextId(), "epic1", "epic1", Status.NEW, epic2);

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

        // обновляем 2ю подзадачу
        System.out.println("обновляем 2ю подзадачу!");
        SubTask subTask22 = new SubTask(2, "subtask22", "subtask22", Status.DONE, 4);
        manager.updateSubTask(subTask22);

        // заполняем лист истории просмотра
        manager.getSubTaskById(1);
        manager.getSubTaskById(1);
        manager.getSubTaskById(1);
        manager.getSubTaskById(1);
        manager.getSubTaskById(1);
        manager.getSubTaskById(1);
        manager.getSubTaskById(1);
        manager.getSubTaskById(1);
        manager.getSubTaskById(1);
        manager.getSubTaskById(1);
        manager.getEpicById(4);
        manager.getSubTaskById(2);
        manager.getSubTaskById(1);
        manager.getEpicById(5);

        manager.printSubTasks();
        manager.printEpicTask();

        // удаляем 1ю подзадачу
        System.out.println("удаляем 1ю подзадачу!");
        manager.deleteSubTaskById(1);
        manager.printSubTasks();
        manager.printEpicTask();

        //получение списка подзадач эпика
        System.out.println("получение списка подзадач эпика!");
        System.out.println(manager.getListSubTasks(5));

        //удаление эпика
        System.out.println("удаление эпика!");
        manager.deleteEpicById(5);
        manager.printSubTasks();
        manager.printEpicTask();

        //История
        System.out.println("история");
        managerHistory.getHistory();


    }
}


