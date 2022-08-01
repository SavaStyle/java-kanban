import java.util.ArrayList;
import manager.Manager;
import tasks.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        Manager manager = new Manager();

        testCreation(manager);
    }

    public static void testCreation(Manager manager) {
        ArrayList<Integer> epic1 = new ArrayList<>();
        ArrayList<Integer> epic2 = new ArrayList<>();

        // создаем подзадачи
        SubTask subTask1 = new SubTask(manager.getNextId(), "subtask1", "subtask1", "NEW", 4);
        SubTask subTask2 = new SubTask(manager.getNextId(), "subtask2", "subtask2", "IN_PROGRESS", 4);
        SubTask subTask3 = new SubTask(manager.getNextId(), "subtask3", "subtask3", "DONE", 5);

        epic1.add(subTask1.getId());
        epic1.add(subTask2.getId());
        epic2.add(subTask3.getId());

        // создаем эпик и добавляем в него подзадачи
        Epic epic11 = new Epic(manager.getNextId(), "epic1", "epic1", "NEW", epic1);
        Epic epic22 = new Epic(manager.getNextId(), "epic1", "epic1", "NEW", epic2);

        // добавляем задачи в менеджер
        manager.addSET(subTask1);
        manager.addSET(subTask2);
        manager.addSET(subTask3);
        manager.addET(epic11);
        manager.addET(epic22);

        // печать списка эпика и подзадач
        manager.printSubTasks();
        manager.printEpicTask();

        // обновляем 2ю подзадачу
        SubTask subTask22 = new SubTask(2, "subtask22", "subtask22", "DONE", 4);
        manager.updateSubTask(subTask22);
        manager.printSubTasks();
        manager.printEpicTask();

        // удаляем 1ю подзадачу

        manager.deleteSubTaskById(1);
        manager.printSubTasks();
        manager.printEpicTask();

        //получение списка подзадач эпика
        System.out.println(manager.getListSubTasks(5));

        //удаление эпика
        manager.deleteEpicById(5);
        manager.printSubTasks();
        manager.printEpicTask();

    }
}


