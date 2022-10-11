import manager.FileBackedTasksManager;
import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.SimpleTask;
import tasks.Status;
import tasks.SubTask;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager manager = Managers.getDefault();

        testCreation(manager);
    }

    public static void testCreation(TaskManager manager) {

        System.out.println("создаем эпик и добавляем в него подзадачи!");


        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        ArrayList<Integer> epic2 = new ArrayList<>();
        Epic epic22 = new Epic(manager.getNextId(), "NAMEepic2", "epic2", Status.NEW, epic2);
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), 1);
        SubTask subTask2 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.DONE,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), 1);
        SubTask subTask3 = new SubTask(manager.getNextId(), "NAMEsubtask3", "subtask3", Status.DONE,
                LocalDateTime.of(2005, 1, 1, 4, 0), Duration.ofMinutes(90), 1);
        epic1.add(subTask1.getId());
        epic1.add(subTask2.getId());
        epic1.add(subTask3.getId());
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        SimpleTask simpleTask2 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 8, 0), Duration.ofMinutes(90));

        //   Epic testEpic = new Epic(manager.getNextId(), "testEpic", "описание теста ID8", Status.NEW);

        System.out.println("добавляем задачи в менеджер!");
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        manager.addSubEpicTask(subTask3);
        manager.addEpicTask(epic11);
        manager.addEpicTask(epic22);
        manager.addSimpleTask(simpleTask1);
        manager.addSimpleTask(simpleTask2);
        //  manager.addEpicTask(testEpic);

        System.out.println("test 8 'эпика");
        //   System.out.println(testEpic.getSubTaskId());
        //    System.out.println(manager.getEpicById(8));

        System.out.println("печать списка эпика и подзадач!");
        manager.getSubTasks();
        manager.getEpicTask();
        manager.getSimpleTask();

        System.out.println("crossCheck");
        manager.crossCheckAdd(new SimpleTask(manager.getNextId(), "TestTask", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 7, 45), Duration.ofMinutes(10)));

        System.out.println("заполняем историю просмотра");
        manager.getSubTaskById(5);
        manager.getSubTaskById(4);
        manager.getSubTaskById(3);
        manager.getEpicById(1);
        manager.getEpicById(2);
        manager.getSubTaskById(3);
        manager.getSimpleTaskById(7);
        manager.getSimpleTaskById(6);

        System.out.println("Печать приоретезированного списка");
        System.out.println(manager.getPrioritizedTasks());
        manager.getSimpleTask();

        System.out.println("история");
        System.out.println(manager.history());
        System.out.println("getNextId");
        System.out.println(manager.getNextId());

        System.out.println("обновление эпика");
        manager.updateEpicTime(epic11);
        manager.getEpicTask();

        System.out.println("Печать приоретезированного списка");
        manager.getSimpleTask();
        System.out.println(manager.getPrioritizedTasks());

        System.out.println("загрузка");

        manager = FileBackedTasksManager.loadFromFile(Path.of("save\\save.csv"));

        System.out.println("печать истории");
        System.out.println(manager.history());
        System.out.println("печать taskov");
        manager.getSubTasks();
        manager.getEpicTask();
        manager.getSimpleTask();

        System.out.println("история");
        System.out.println(manager.history());

        System.out.println("Печать приоретезированного списка");
        System.out.println(manager.getPrioritizedTasks());

        ArrayList<Integer> testList = new ArrayList<>();

        SubTask testSubTask = new SubTask(manager.getNextId(), "testSubTask", "testSubTask", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(30), 8);
        testList.add(testSubTask.getId());
    }
}


