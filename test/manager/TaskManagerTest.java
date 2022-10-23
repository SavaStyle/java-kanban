package manager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest {

    protected TaskManager manager;
    protected InMemoryHistoryManager managerHistory = new InMemoryHistoryManager();

    @AfterEach
    void clean() {
        manager.cleanSubTask();
        manager.cleanEpicTask();
        manager.cleanSubTask();
        managerHistory.historyClear();
    }

    @Test
    void getNextId() {
        int id = manager.getNextId();
        assertEquals(id + 1, manager.getNextId());
    }

    // Получение списка всех задач
    @Test
    void getSimpleTask() {
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        SimpleTask simpleTask2 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 8, 0), Duration.ofMinutes(90));
        manager.addSimpleTask(simpleTask1);
        manager.addSimpleTask(simpleTask2);
        Map<Integer, SimpleTask> simpleTasks = new HashMap<>();
        simpleTasks.put(simpleTask1.getId(), simpleTask1);
        simpleTasks.put(simpleTask2.getId(), simpleTask2);

        assertEquals(simpleTasks, manager.getSimpleTask());
    }

    @Test
    void getEpicTask() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        ArrayList<Integer> epic2 = new ArrayList<>();
        Epic epic22 = new Epic(manager.getNextId(), "NAMEepic2", "epic2", Status.NEW, epic2);
        manager.addEpicTask(epic11);
        manager.addEpicTask(epic22);
        Map<Integer, Epic> Epic = new HashMap<>();
        Epic.put(epic11.getId(), epic11);
        Epic.put(epic22.getId(), epic22);

        assertEquals(Epic, manager.getEpicTask());
    }

    @Test
    void getSubTasks() {
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), 1);
        SubTask subTask2 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.DONE,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), 1);
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        Map<Integer, SubTask> subTask = new HashMap<>();
        subTask.put(subTask1.getId(), subTask1);
        subTask.put(subTask2.getId(), subTask2);

        assertEquals(subTask, manager.getSubTasks());
    }

    // Удаление всех задач
    @Test
    void cleanSimpleTask() {
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        SimpleTask simpleTask2 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 8, 0), Duration.ofMinutes(90));
        manager.addSimpleTask(simpleTask1);
        manager.addSimpleTask(simpleTask2);
        manager.cleanSimpleTask();
        assertEquals(new HashMap<Integer, SimpleTask>(), manager.getSimpleTask());

    }

    @Test
    void cleanEpicTask() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        ArrayList<Integer> epic2 = new ArrayList<>();
        Epic epic22 = new Epic(manager.getNextId(), "NAMEepic2", "epic2", Status.NEW, epic2);
        manager.addEpicTask(epic11);
        manager.addEpicTask(epic22);
        manager.cleanEpicTask();
        assertEquals(new HashMap<Integer, Epic>(), manager.getEpicTask());
    }

    @Test
    void cleanSubTask() {
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), 1);
        SubTask subTask2 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.DONE,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), 1);
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        manager.cleanSubTask();
        assertEquals(new HashMap<Integer, SubTask>(), manager.getSubTasks());
    }

    // Получение задач по ID
    @Test
    void getSimpleTaskById() {
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        SimpleTask simpleTask2 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 8, 0), Duration.ofMinutes(90));
        manager.addSimpleTask(simpleTask1);
        manager.addSimpleTask(simpleTask2);
        int id = simpleTask1.getId();

        assertEquals(simpleTask1, manager.getSimpleTaskById(id));
    }

    @Test
    void getEpicById() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        ArrayList<Integer> epic2 = new ArrayList<>();
        Epic epic22 = new Epic(manager.getNextId(), "NAMEepic2", "epic2", Status.NEW, epic2);
        manager.addEpicTask(epic11);
        manager.addEpicTask(epic22);
        int id = epic11.getId();
        assertEquals(epic11, manager.getEpicById(id));
    }

    @Test
    void getSubTaskById() {
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), 1);
        SubTask subTask2 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.DONE,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), 1);
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        int id = subTask1.getId();
        assertEquals(subTask1, manager.getSubTaskById(id));
    }

    // создание задач
    @Test
    void addSimpleTask() {
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        SimpleTask simpleTask2 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 8, 0), Duration.ofMinutes(90));
        manager.addSimpleTask(simpleTask1);
        manager.addSimpleTask(simpleTask2);
        Map<Integer, SimpleTask> map = manager.getSimpleTask();
        assertEquals(2, map.size());
    }

    @Test
    void addEpicTask() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        ArrayList<Integer> epic2 = new ArrayList<>();
        Epic epic22 = new Epic(manager.getNextId(), "NAMEepic2", "epic2", Status.NEW, epic2);
        manager.addEpicTask(epic11);
        manager.addEpicTask(epic22);
        Map<Integer, Epic> map = manager.getEpicTask();
        assertEquals(2, map.size());
    }

    @Test
    void addSubEpicTask() {
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), 1);
        SubTask subTask2 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.DONE,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), 1);
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        Map<Integer, SubTask> map = manager.getSubTasks();
        assertEquals(2, map.size());
    }

    // обновление задач
    @Test
    void updateSimpleTask() {
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        SimpleTask simpleTask2 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 8, 0), Duration.ofMinutes(90));
        manager.addSimpleTask(simpleTask1);
        manager.addSimpleTask(simpleTask2);
        int id = simpleTask2.getId();
        SimpleTask simpleTaskUpdated = new SimpleTask(id, "NAMEsimpleTask2", "Updated", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 8, 0), Duration.ofMinutes(90));
        manager.updateSimpleTask(simpleTaskUpdated);

        assertEquals(simpleTaskUpdated, manager.getSimpleTaskById(id));
    }

    @Test
    void updateEpicTask() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        ArrayList<Integer> epic2 = new ArrayList<>();
        Epic epic22 = new Epic(manager.getNextId(), "NAMEepic2", "epic2", Status.NEW, epic2);
        manager.addEpicTask(epic11);
        manager.addEpicTask(epic22);
        int id = epic11.getId();
        Epic epicUpdated = new Epic(id, "NAMEepic2", "Updated", Status.NEW, epic2);
        manager.updateEpicTask(epicUpdated);

        assertEquals(epicUpdated, manager.getEpicById(id));
    }

    @Test
    void updateSubTask() {
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), 1);
        SubTask subTask2 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.DONE,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), 1);
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        int id = subTask2.getId();
        SubTask subTaskUpdated = new SubTask(subTask2.getId(), "NAMEsubtask2", "subtask2", Status.DONE,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), 1);
        manager.updateSubTask(subTaskUpdated);

        assertEquals(subTaskUpdated, manager.getSubTaskById(id));
    }

    // Удаление задач по ID
    @Test
    void deleteSimpleTaskById() {
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        SimpleTask simpleTask2 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 8, 0), Duration.ofMinutes(90));
        manager.addSimpleTask(simpleTask1);
        manager.addSimpleTask(simpleTask2);
        int id = simpleTask1.getId();
        int size = manager.getSimpleTask().size();
        manager.deleteSimpleTaskById(id);

        assertEquals(size - 1, manager.getSimpleTask().size());
    }

    @Test
    void deleteSubTaskById() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), epic11.getId());
        SubTask subTask2 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.DONE,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), epic11.getId());
        epic1.add(subTask1.getId());
        epic1.add(subTask2.getId());
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        manager.addEpicTask(epic11);

        int size = manager.getSubTasks().size();
        manager.deleteSubTaskById(subTask1.getId());  //2

        assertEquals(size - 1, manager.getSubTasks().size());
    }

    @Test
    void deleteEpicById() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        ArrayList<Integer> epic2 = new ArrayList<>();
        Epic epic22 = new Epic(manager.getNextId(), "NAMEepic2", "epic2", Status.NEW, epic2);
        manager.addEpicTask(epic11);
        manager.addEpicTask(epic22);
        int id = epic11.getId();
        int size = manager.getEpicTask().size();
        manager.deleteEpicById(id);

        assertEquals(size - 1, manager.getEpicTask().size());
    }

    // обновление статуса эпика
    @Test
    void updateEpicStatus() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), 1);
        epic1.add(subTask1.getId());
        manager.addSubEpicTask(subTask1);
        manager.addEpicTask(epic11);
        manager.updateEpicStatus(epic11);
        assertEquals(Status.DONE, epic11.getStatus());
    }

    @Test
    void history() {
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
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        manager.addSubEpicTask(subTask3);
        manager.addEpicTask(epic11);
        manager.addEpicTask(epic22);
        manager.addSimpleTask(simpleTask1);
        manager.addSimpleTask(simpleTask2);
        List<Task> history = managerHistory.getHistory();
        assertEquals(0, history.size());
        //заполняем историю просмотра
        manager.getSubTaskById(subTask1.getId());
        manager.getSubTaskById(subTask2.getId());
        manager.getSubTaskById(subTask3.getId());
        manager.getEpicById(epic11.getId());
        manager.getEpicById(epic22.getId());
        manager.getSimpleTaskById(simpleTask1.getId());
        manager.getSimpleTaskById(simpleTask2.getId());
        assertNotNull(history, "История заполнена.");

    }

    @Test
    void getSimpleTasks() {
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        SimpleTask simpleTask2 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 8, 0), Duration.ofMinutes(90));
        manager.addSimpleTask(simpleTask1);
        manager.addSimpleTask(simpleTask2);
        Collection<SimpleTask> getSimpleTasks = manager.getSimpleTasks();
        assertNotNull(getSimpleTasks, "Успех");
    }

    @Test
    void getEpicTasks() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        ArrayList<Integer> epic2 = new ArrayList<>();
        Epic epic22 = new Epic(manager.getNextId(), "NAMEepic2", "epic2", Status.NEW, epic2);
        manager.addEpicTask(epic11);
        manager.addEpicTask(epic22);
        Collection<Epic> getEpicTasks = manager.getEpicTasks();
        assertNotNull(getEpicTasks, "Успех");
    }

    @Test
    void getSubTask() {
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), 1);
        SubTask subTask2 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.DONE,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), 1);
        SubTask subTask3 = new SubTask(manager.getNextId(), "NAMEsubtask3", "subtask3", Status.DONE,
                LocalDateTime.of(2005, 1, 1, 4, 0), Duration.ofMinutes(90), 1);
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        manager.addSubEpicTask(subTask3);
        Collection<SubTask> getSubTask = manager.getSubTask();
        assertNotNull(getSubTask, "Успех");
    }

    @Test
    void updateEpicTime() {
        //пустой эпик без времени
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        manager.addEpicTask(epic11);
        LocalDateTime nullTime = epic11.getStartTime();
        assertNull(nullTime);
        // добовление эпикаи проверка времени старта
        LocalDateTime firstSubTask = LocalDateTime.of(2000, 1, 1, 0, 0);
        SubTask subTask1 = new SubTask(manager.getNextId(), "subtask1", "subtask1", Status.DONE,
                firstSubTask, Duration.ofMinutes(60), epic11.getId());
        epic1.add(subTask1.getId());
        manager.addSubEpicTask(subTask1);
        manager.updateEpicTime(epic11);
        assertEquals(firstSubTask, epic11.getStartTime());
        // добовление эпикаи проверка длительности эпика
        LocalDateTime secondSubTask = LocalDateTime.of(2000, 1, 1, 2, 0);
        SubTask subTask2 = new SubTask(manager.getNextId(), "subtask2", "subtask2", Status.DONE,
                secondSubTask, Duration.ofMinutes(60), epic11.getId());
        epic1.add(subTask2.getId());
        manager.addSubEpicTask(subTask2);
        manager.updateEpicTime(epic11);
        Duration duration = Duration.ofMinutes(180);
        assertEquals(duration, epic11.getDuration());
        // добовление эпикаи проверка длительности эпика
        SubTask subTask3 = new SubTask(manager.getNextId(), "subtask3", "subtask3", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 4, 0), Duration.ofMinutes(60), epic11.getId());
        epic1.add(subTask3.getId());
        manager.addSubEpicTask(subTask3);
        manager.updateEpicTime(epic11);
        Duration duration3 = Duration.ofMinutes(300);
        assertEquals(duration3, epic11.getDuration());
    }

    @Test
    void updateEpicTimeBySubTask() {
        //пустой эпик без времени
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        manager.addEpicTask(epic11);
        LocalDateTime nullTime = epic11.getStartTime();
        assertNull(nullTime);
        // добовление эпикаи проверка времени старта
        LocalDateTime firstSubTask = LocalDateTime.of(2000, 1, 1, 0, 0);
        SubTask subTask1 = new SubTask(manager.getNextId(), "subtask1", "subtask1", Status.DONE,
                firstSubTask, Duration.ofMinutes(60), epic11.getId());
        epic1.add(subTask1.getId());
        manager.addSubEpicTask(subTask1);
        manager.updateEpicTimeBySubTask(subTask1);
        assertEquals(firstSubTask, epic11.getStartTime());
        // добовление эпикаи проверка длительности эпика
        LocalDateTime secondSubTask = LocalDateTime.of(2000, 1, 1, 2, 0);
        SubTask subTask2 = new SubTask(manager.getNextId(), "subtask2", "subtask2", Status.DONE,
                secondSubTask, Duration.ofMinutes(60), epic11.getId());
        epic1.add(subTask2.getId());
        manager.addSubEpicTask(subTask2);
        manager.updateEpicTimeBySubTask(subTask2);
        Duration duration = Duration.ofMinutes(180);
        assertEquals(duration, epic11.getDuration());
        // добовление эпикаи проверка длительности эпика
        SubTask subTask3 = new SubTask(manager.getNextId(), "subtask3", "subtask3", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 4, 0), Duration.ofMinutes(60), epic11.getId());
        epic1.add(subTask3.getId());
        manager.addSubEpicTask(subTask3);
        manager.updateEpicTimeBySubTask(subTask3);
        Duration duration3 = Duration.ofMinutes(300);
        assertEquals(duration3, epic11.getDuration());
    }

    @Test
    void getPrioritizedTasks() {
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
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        manager.addSubEpicTask(subTask3);
        manager.addEpicTask(epic11);
        manager.addEpicTask(epic22);
        manager.addSimpleTask(simpleTask1);
        manager.addSimpleTask(simpleTask2);
        Set<Task> prioritizedTasks = manager.getPrioritizedTasks();
        assertNotNull(prioritizedTasks);
    }

    @Test
    void crossCheckAddFirstAddSecondTaskAddCross() {
        Set<Task> prioritizedTasks = manager.getPrioritizedTasks();
        assertTrue(prioritizedTasks.isEmpty());
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        manager.crossCheckAdd(simpleTask1);
        assertFalse(prioritizedTasks.isEmpty());
        SimpleTask simpleTask2 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 8, 0), Duration.ofMinutes(90));
        manager.crossCheckAdd(simpleTask2);
        assertEquals(2, (manager.getPrioritizedTasks()).size());
        SimpleTask simpleTask3 = new SimpleTask(manager.getNextId(), "Таск с пересечением времени", "Таск с пересечением времени", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 9, 0), Duration.ofMinutes(90));
        manager.crossCheckAdd(simpleTask3);
        assertEquals(2, (manager.getPrioritizedTasks()).size());
    }

}