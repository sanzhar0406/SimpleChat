package com.sanzhar;

import org.junit.jupiter.api.Test;
import com.sanzhar.utils.GameType;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SocketTest {


    @Test
    public void testPlayer() throws InterruptedException, IOException {

        String[] argsSeparate = {GameType.SEPARATE.name()};
        App app = new App();
        assertTrue(app.runSeparate());
    }
}
