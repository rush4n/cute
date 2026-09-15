package cute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CuteTest {

    @Test
    void firstTest() {
        assertEquals(5, new Cute().five());
    }
}
