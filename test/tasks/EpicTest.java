package tasks;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EpicTest {
    TaskManager manager = Managers.getDefault();

    @Test
    void getSubTaskId() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), epic11.getId());
        epic1.add(subTask1.getId());

        assertEquals(epic1, epic11.getSubTaskId());
    }

    @Test
    void testToString() {
        ArrayList<Integer> epic2 = new ArrayList<>();
        Epic epic22 = new Epic(manager.getNextId(), "NAMEepic2", "epic2", Status.NEW, epic2);
        String string = epic22.getId() + ",EPIC,NAMEepic2,NEW,epic2,null,null";

        assertEquals(string, epic22.toString());
    }

    @Test
    void withOutSubtasks() {
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW);

        assertNull(epic11.getSubTaskId());
    }

    @Test
    void withNewSubtasks() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), epic11.getId());
        SubTask subTask2 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.NEW,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), epic11.getId());
        SubTask subTask3 = new SubTask(manager.getNextId(), "NAMEsubtask3", "subtask3", Status.NEW,
                LocalDateTime.of(2005, 1, 1, 4, 0), Duration.ofMinutes(90), epic11.getId());
        epic1.add(subTask1.getId());
        epic1.add(subTask2.getId());
        epic1.add(subTask3.getId());
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        manager.addSubEpicTask(subTask3);
        manager.addEpicTask(epic11);
        manager.updateEpicStatus(epic11);

        assertEquals(Status.NEW, epic11.getStatus());
    }

    @Test
    void withDoneSubtasks() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), epic11.getId());
        SubTask subTask2 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.DONE,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), epic11.getId());
        SubTask subTask3 = new SubTask(manager.getNextId(), "NAMEsubtask3", "subtask3", Status.DONE,
                LocalDateTime.of(2005, 1, 1, 4, 0), Duration.ofMinutes(90), epic11.getId());
        epic1.add(subTask1.getId());
        epic1.add(subTask2.getId());
        epic1.add(subTask3.getId());
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        manager.addSubEpicTask(subTask3);
        manager.addEpicTask(epic11);
        manager.updateEpicStatus(epic11);

        assertEquals(Status.DONE, epic11.getStatus());
    }

    @Test
    void withNewANDDoneSubtasks() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), epic11.getId());
        SubTask subTask2 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.NEW,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), epic11.getId());
        SubTask subTask3 = new SubTask(manager.getNextId(), "NAMEsubtask3", "subtask3", Status.DONE,
                LocalDateTime.of(2005, 1, 1, 4, 0), Duration.ofMinutes(90), epic11.getId());
        epic1.add(subTask1.getId());
        epic1.add(subTask2.getId());
        epic1.add(subTask3.getId());
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        manager.addSubEpicTask(subTask3);
        manager.addEpicTask(epic11);
        manager.updateEpicStatus(epic11);

        assertEquals(Status.IN_PROGRESS, epic11.getStatus());
    }

    @Test
    void withInprogressSubtasks() {
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.IN_PROGRESS,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), epic11.getId());
        SubTask subTask2 = new SubTask(manager.getNextId(), "NAMEsubtask2", "subtask2", Status.IN_PROGRESS,
                LocalDateTime.of(2001, 1, 1, 2, 0), Duration.ofMinutes(90), epic11.getId());
        SubTask subTask3 = new SubTask(manager.getNextId(), "NAMEsubtask3", "subtask3", Status.IN_PROGRESS,
                LocalDateTime.of(2005, 1, 1, 4, 0), Duration.ofMinutes(90), epic11.getId());
        epic1.add(subTask1.getId());
        epic1.add(subTask2.getId());
        epic1.add(subTask3.getId());
        manager.addSubEpicTask(subTask1);
        manager.addSubEpicTask(subTask2);
        manager.addSubEpicTask(subTask3);
        manager.addEpicTask(epic11);
        manager.updateEpicStatus(epic11);

        assertEquals(Status.IN_PROGRESS, epic11.getStatus());
    }

}