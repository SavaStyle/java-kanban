package tasks;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleTaskTest {

    TaskManager manager = Managers.getDefault();

    SimpleTaskTest() throws IOException, InterruptedException {
    }

    @Test
    void testToString() {
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        String string = "1,SIMPLETASK,NAMEsimpleTask1,IN_PROGRESS,simpleTask1,0005-01-01T06:00,PT1H30M";
        assertEquals(string, simpleTask1.toString());
    }
}