package manager;

import exception.ManagerSaveException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SimpleTask;
import tasks.Status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTasksManagerTest extends InMemoryTaskManagerTest {

    @BeforeEach
    public void beforeEach() throws IOException {
        manager = new FileBackedTasksManager();
    }

    @Test
    void saveTestloadFromFileTestupdateNextIDTest() {
        manager.cleanSimpleTask();
        SimpleTask simpleTask11 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        SimpleTask simpleTask22 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 8, 0), Duration.ofMinutes(90));
        ArrayList<Integer> listEpic33 = new ArrayList<>();
        Epic epic33 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, listEpic33);
        try {
            String firstTest = Files.readString(Path.of("save\\save.csv"));
            String[] testEmptyManager = firstTest.split(System.lineSeparator());
            assertEquals(3, testEmptyManager.length); // проверка сохранения пустого списка задач (заголовок, отступ, строка история)
            manager.addEpicTask(epic33);
            manager.addSimpleTask(simpleTask11);
            manager.addSimpleTask(simpleTask22);
            String secondTest = Files.readString(Path.of("save\\save.csv"));
            String[] testEmptyEpicWithoutHistory = secondTest.split(System.lineSeparator());
            assertEquals(6, testEmptyEpicWithoutHistory.length); // проверка сохранения пустого списка задач (заголовок, отступ, строка история, 3строки задач)
            String testHistroyEmpty = testEmptyEpicWithoutHistory[5];
            assertEquals("История просмотров пуста", testHistroyEmpty); // проверка пустой истории
            manager.getEpicById(epic33.getId());
            manager.getSimpleTaskById(simpleTask11.getId());
            manager.getSimpleTaskById(simpleTask22.getId());
            String thirdTest = Files.readString(Path.of("save\\save.csv"));
            String[] testEmptyEpicWithHistory = thirdTest.split(System.lineSeparator());
            String testHistroy = testEmptyEpicWithHistory[5];
            int id1 = simpleTask11.getId();
            int id2 = simpleTask22.getId();
            int id3 = epic33.getId();
            String answer = id3 + "," + id1 + "," + id2 + ",";
            assertEquals(answer, testHistroy); // проверка заполненной истории
        } catch (IOException err) {
            throw new ManagerSaveException("Ошибка при восстановлении данных");
        }
    }

}