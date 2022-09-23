import manager.FileBackedTasksManager;
import manager.TaskManager;
import tasks.Epic;
import tasks.SimpleTask;
import tasks.Status;
import tasks.SubTask;

import java.nio.file.Path;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager manager = new FileBackedTasksManager();

        testCreation(manager);
    }

    public static void testCreation(TaskManager manager)  {

        System.out.println("создаем эпик и добавляем в него подзадачи!");
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "epic1", "epic1", Status.NEW, epic1);

        ArrayList<Integer> epic2 = new ArrayList<>();
        Epic epic22 = new Epic(manager.getNextId(), "epic2", "epic2", Status.NEW, epic2);

        System.out.println("создаем подзадачи!");
        SubTask subTask1 = new SubTask(manager.getNextId(), "subtask1", "subtask1", Status.NEW, 1);
        SubTask subTask2 = new SubTask(manager.getNextId(), "subtask2", "subtask2", Status.IN_PROGRESS, 1);
        SubTask subTask3 = new SubTask(manager.getNextId(), "subtask3", "subtask3", Status.DONE, 1);

        epic1.add(subTask1.getId());
        epic1.add(subTask2.getId());
        epic1.add(subTask3.getId());

        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "simpleTask1", "simpleTask1", Status.IN_PROGRESS);
        SimpleTask simpleTask2 = new SimpleTask(manager.getNextId(), "simpleTask2", "simpleTask2", Status.NEW);

        System.out.println("добавляем задачи в менеджер!");
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        manager.addSubEpicTask(subTask3);
        manager.addEpicTask(epic11);
        manager.addEpicTask(epic22);
        manager.addSimpleTask(simpleTask1);
        manager.addSimpleTask(simpleTask2);

        System.out.println("печать списка эпика и подзадач!");
        manager.printSubTasks();
        manager.printEpicTask();
        manager.printSimpleTask();

        System.out.println("заполняем лист истории просмотра");
        manager.getSubTaskById(5);
        manager.getSubTaskById(4);
        manager.getSubTaskById(3);
        manager.getEpicById(1);
        manager.getEpicById(2);
        manager.getSubTaskById(3);
        manager.getSimpleTaskById(7);
        manager.getSimpleTaskById(6);

        System.out.println("история");
        System.out.println(manager.history());
        System.out.println("getNextId");
        System.out.println(manager.getNextId());

        System.out.println("загрузка");

        manager = FileBackedTasksManager.loadFromFile(Path.of("save.csv"));

        System.out.println("печать");
        System.out.println(manager.history());
        manager.printSubTasks();
        manager.printEpicTask();
        manager.printSimpleTask();

        System.out.println("getNextId");
        System.out.println(manager.getNextId());
    }
}


