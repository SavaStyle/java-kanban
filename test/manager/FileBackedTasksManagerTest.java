package manager;

import Exception.ManagerSaveException;
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

class FileBackedTasksManagerTest extends TaskManagerTest {

    TaskManager manager = Managers.getDefault();
    InMemoryHistoryManager managerHistory = Managers.getDefaultHistory();

    FileBackedTasksManagerTest() throws IOException, InterruptedException {
    }


    @BeforeEach
    void clean() {
        manager.cleanSubTask();
        manager.cleanEpicTask();
        manager.cleanSubTask();
        managerHistory.historyClear();
    }

    @BeforeAll
    static void toNull() {
        FileBackedTasksManager fb = new FileBackedTasksManager();
        fb.save();
    }

    @Test
    void saveTestloadFromFileTestupdateNextIDTest() {
        SimpleTask simpleTask1 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask1", "simpleTask1", Status.IN_PROGRESS,
                LocalDateTime.of(5, 1, 1, 6, 0), Duration.ofMinutes(90));
        SimpleTask simpleTask2 = new SimpleTask(manager.getNextId(), "NAMEsimpleTask2", "simpleTask2", Status.NEW,
                LocalDateTime.of(2000, 1, 1, 8, 0), Duration.ofMinutes(90));
        ArrayList<Integer> epic1 = new ArrayList<>();
        Epic epic11 = new Epic(manager.getNextId(), "NAMEepic1", "epic1", Status.NEW, epic1);
        try {
            String firstTest = Files.readString(Path.of("save\\save.csv"));
            String[] testEmptyManager = firstTest.split(System.lineSeparator());
            assertEquals(3, testEmptyManager.length); // проверка сохранения пустого списка задач (заголовок, отступ, строка история)
            manager.addEpicTask(epic11);
            manager.addSimpleTask(simpleTask1);
            manager.addSimpleTask(simpleTask2);
            String secondTest = Files.readString(Path.of("save\\save.csv"));
            String[] testEmptyEpicWithoutHistory = secondTest.split(System.lineSeparator());
            assertEquals(6, testEmptyEpicWithoutHistory.length); // проверка сохранения пустого списка задач (заголовок, отступ, строка история, 3строки задач)
            String testHistroyEmpty = testEmptyEpicWithoutHistory[5];
            assertEquals("История просмотров пуста", testHistroyEmpty); // проверка пустой истории
            manager.getEpicById(epic11.getId());
            manager.getSimpleTaskById(simpleTask1.getId());
            manager.getSimpleTaskById(simpleTask2.getId());
            String thirdTest = Files.readString(Path.of("save\\save.csv"));
            String[] testEmptyEpicWithHistory = thirdTest.split(System.lineSeparator());
            String testHistroy = testEmptyEpicWithHistory[5];
            assertEquals("3,1,2,", testHistroy); // проверка заполненной истории
        } catch (IOException err) {
            throw new ManagerSaveException("Ошибка при восстановлении данных");
        }
    }

}