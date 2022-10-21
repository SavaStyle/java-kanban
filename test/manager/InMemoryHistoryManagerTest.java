package manager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import tasks.SimpleTask;
import tasks.Status;
import tasks.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    TaskManager manager = Managers.getDefault();
    InMemoryHistoryManager managerHistory = Managers.getDefaultHistory();

    InMemoryHistoryManagerTest() throws IOException, InterruptedException {
    }

    @AfterEach
    void clean() {
        manager.cleanSubTask();
        manager.cleanEpicTask();
        manager.cleanSubTask();
        managerHistory.historyClear();
    }

    @AfterAll
    static void toNull() {
        FileBackedTasksManager fb = new FileBackedTasksManager();
        fb.save();
    }

    @Test
    void add() {
        Map<Integer, Node> getNodeMap = managerHistory.getNodeMap();
        assertEquals(0, getNodeMap.size());
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        manager.addSimpleTask(simpleTask1);
        managerHistory.add(simpleTask1);
        assertEquals(1, getNodeMap.size());
    }

    @Test
    void linkLastTestAndremoveAndremoveNodeTest() {
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        manager.addSimpleTask(simpleTask1);
        managerHistory.add(simpleTask1);
        Node node = managerHistory.getLast();
        Task taskFromNode = node.getTask();
        assertEquals(simpleTask1, taskFromNode);   // linkLastTest
        SimpleTask simpleTask2 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 2, 8, 0), Duration.ofMinutes(90));
        manager.addSimpleTask(simpleTask2);
        managerHistory.add(simpleTask2);
        Node node2 = managerHistory.getLast();
        Task taskFromNode2 = node2.getTask();
        assertEquals(simpleTask2, taskFromNode2);  // linkLastTest
        SimpleTask simpleTask3 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 3, 8, 0), Duration.ofMinutes(90));
        manager.addSimpleTask(simpleTask3);
        managerHistory.add(simpleTask3);
        managerHistory.remove(simpleTask3.getId());
        assertEquals(2, managerHistory.getNodeMap().size());   //removeTest
        Node nodeTest = managerHistory.getLast();
        managerHistory.removeNode(nodeTest);
        Node nodePostTest = managerHistory.getLast();
        assertEquals(simpleTask1, nodePostTest.getTask());   //removeNodeTest
    }

/*
    @Test
    void getHistory() {
       Тест истории выполнен в классе TaskManagerTest
    }
    */

}