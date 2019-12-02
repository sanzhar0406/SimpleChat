package com.sanzhar;

import org.junit.jupiter.api.Test;
import com.sanzhar.utils.ArgumentParser;
import com.sanzhar.utils.GameType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArgumentParserTest {

    @Test
    public void testValid() {
        String[] argsSingle = {GameType.SINGLE.name()};
        String[] argsSeparate = {GameType.SEPARATE.name()};
        String[] argsEmpty = {};
        String[] argsInvalid = {"ABCD"};
        String[] argsInvalid2 = {"blah"};

        assertEquals(ArgumentParser.isValid(argsSingle), true);
        assertEquals(ArgumentParser.isValid(argsSeparate), true);

        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentParser.isValid(argsEmpty);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentParser.isValid(argsInvalid);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentParser.isValid(argsInvalid2);
        });
    }

    @Test
    public void testParser() {
        String[] argsSingle = {"SINGLE"};
        String[] argsSeparate = {"SEPARATE"};

        assertEquals(ArgumentParser.getType(argsSingle), GameType.SINGLE);
        assertEquals(ArgumentParser.getType(argsSeparate), GameType.SEPARATE);
    }
}
