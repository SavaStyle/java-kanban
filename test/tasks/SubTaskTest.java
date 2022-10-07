package tasks;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    TaskManager manager = Managers.getDefault();

    @Test
    void getEpicId() {
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), 2);
        assertEquals(2,  subTask1.getEpicId());
    }

    @Test
    void testToString() {
        SubTask subTask1 = new SubTask(manager.getNextId(), "NAMEsubtask1", "subtask1", Status.DONE,
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofMinutes(90), 2);
        String string = "1,SUBTASK,NAMEsubtask1,DONE,subtask1,2000-01-01T00:00,PT1H30M,2";
        assertEquals(string,  subTask1.toString());
    }
}