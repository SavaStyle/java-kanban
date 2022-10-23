
package manager;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class InMemoryTaskManagerTest extends TaskManagerTest {
    @BeforeEach
    public void beforeEach() throws IOException {
        manager = new InMemoryTaskManager();
    }
}
