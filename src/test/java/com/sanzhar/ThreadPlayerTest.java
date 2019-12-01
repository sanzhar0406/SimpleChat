import org.junit.Test;
import static org.junit.Assert.*;

public class ArgumentParserTest {

    @Test
    public void testParser() {
        String[] argsSingle = {"SINGLE"};
        String[] argsSeparate = {"SEPARATE"};
        String[] argsEmpty = {};
        String[] argsInvalid = {"ABCD"};

        assertEquals(ArgumentParser.isValid(argsSingle), true);
        assertEquals(ArgumentParser.isValid(argsSeparate), true);
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentParser.isValid(argsEmpty);
        });assertThrows(IllegalArgumentException.class, () -> {
            ArgumentParser.isValid(argsInvalid);
        });
    }
}