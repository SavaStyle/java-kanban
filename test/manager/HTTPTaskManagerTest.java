package manager;

import KVServer.KVServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import tasks.Epic;
import tasks.SimpleTask;
import tasks.Status;
import tasks.SubTask;
import KVServer.HttpTaskServer;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HTTPTaskManagerTest  extends FileBackedTasksManagerTest {

     KVServer kvs;
     TaskManager manager;
     HttpTaskServer server;
      SimpleTask simpleTask555;
      Epic epic555;
      SimpleTask simpleTask666;
      SubTask subTask222;
      Epic epic666;
      SubTask subTask1111;


    @BeforeEach
     void  start() throws IOException, InterruptedException {
        kvs = new KVServer();
        kvs.start();
        manager = new HTTPTaskManager(URI.create("http://localhost:8078"));
        server = new HttpTaskServer(manager);
        server.start();

        ArrayList<Integer> epic1 = new ArrayList<>();
        epic555 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        ArrayList<Integer> epic2 = new ArrayList<>();
        epic666 = new Epic(manager.getNextId(), "NAMEepic2", "epic2", Status.NEW, epic2);
        subTask1111 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), epic555.getId());
        subTask222 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.DONE,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), epic555.getId());
        SubTask subTask321 = new SubTask(manager.getNextId(), "NAMEsubtask3", "subtask3", Status.DONE,
                LocalDateTime.of(2005, 1, 1, 4, 0), Duration.ofMinutes(90), epic555.getId());
        epic1.add(subTask1111.getId());
        epic1.add(subTask222.getId());
        epic1.add(subTask321.getId());
        simpleTask555 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        simpleTask666 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 8, 0), Duration.ofMinutes(90));
        manager.addSubEpicTask(subTask1111);
        manager.addSubEpicTask(subTask222);
        manager.addSubEpicTask(subTask321);
        manager.addEpicTask(epic555);
        manager.addEpicTask(epic666);
        manager.addSimpleTask(simpleTask555);
        manager.addSimpleTask(simpleTask666);
        manager.getSubTaskById(subTask1111.getId());
        manager.getSubTaskById(subTask222.getId());
        manager.getSubTaskById(subTask321.getId());
        manager.getEpicById(epic555.getId());
        manager.getEpicById(epic666.getId());
        manager.getSubTaskById(subTask1111.getId());
        manager.getSimpleTaskById(simpleTask555.getId());
        manager.getSimpleTaskById(simpleTask666.getId());
    }

    @AfterEach
    public void afterEach() {
        manager.cleanSubTask();
        manager.cleanEpicTask();
        manager.cleanSubTask();
        managerHistory.historyClear();

        kvs.stop();
        server.stop();
    }

    @Test
    void getTasks() throws IOException, InterruptedException {
        assertNotNull(manager.getSimpleTask());  // проверка  SimpleTask
        assertNotNull(manager.getEpicTask());    // проверка  EpicTask
        assertNotNull(manager.getSubTasks());    // проверка  SubTasks
        assertNotNull(manager.history());        // проверка  history
    }

    @Test
    void getTasksById() throws IOException, InterruptedException {
        assertEquals(simpleTask555, manager.getSimpleTaskById(simpleTask555.getId())); // проверка  SimpleTaskById
        assertEquals(epic555, manager.getEpicById(epic555.getId())); // проверка  EpicById
        assertEquals(subTask1111, manager.getSubTaskById(subTask1111.getId())); // проверка  SubTaskById
    }

    @Test
    void deleteTasks() throws IOException, InterruptedException {
        var taskSize = manager.getSimpleTask().size();
        var epicSize = manager.getEpicTask().size();
        var subSize = manager.getSubTasks().size();
        manager.deleteSimpleTaskById(simpleTask555.getId());
        manager.deleteSubTaskById(subTask222.getId());
        manager.deleteEpicById(epic666.getId());

        assertEquals(taskSize - 1, manager.getSimpleTask().size()); // проверка  удаления SimpleTaskBy
        assertEquals(epicSize - 1, manager.getEpicTask().size()); // проверка удаления EpicBy
        assertEquals(subSize - 1, manager.getSubTasks().size()); // проверка удаления SubTaskBy
    }

    @Test
    void addTasks() throws IOException, InterruptedException {
        var taskSize = manager.getSimpleTask().size();
        var epicSize = manager.getEpicTask().size();
        var subSize = manager.getSubTasks().size();
        SubTask subTask33 = new SubTask(manager.getNextId(), "NAME33", "subtask33", Status.DONE,
                LocalDateTime.of(3000, 1, 1, 0, 0), Duration.ofMinutes(90), 1);
        SimpleTask simpleTask101 = new SimpleTask(manager.getNextId(), "NAME55", "simpleTask55", Status.IN_PROGRESS,
                LocalDateTime.of(1000, 1, 1, 6, 0), Duration.ofMinutes(90));
        ArrayList<Integer> epic909 = new ArrayList<>();
        Epic epic99 = new Epic(manager.getNextId(), "NAM99", "epic99", Status.NEW, epic909);

        manager.addSubEpicTask(subTask33);
        manager.addSimpleTask(simpleTask101);
        manager.addEpicTask(epic99);

        assertEquals(taskSize + 1, manager.getSimpleTask().size()); // проверка  добавления SimpleTaskBy
        assertEquals(epicSize + 1, manager.getEpicTask().size()); // проверка добавления EpicBy
        assertEquals(subSize + 1 , manager.getSubTasks().size()); // проверка добавления SubTaskBy
    }
}