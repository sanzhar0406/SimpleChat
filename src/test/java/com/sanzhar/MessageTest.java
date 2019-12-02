package com.sanzhar;

import com.sanzhar.model.Message;
import com.sanzhar.model.ThreadPlayer;
import org.junit.jupiter.api.Test;
import com.sanzhar.utils.Constants;
import com.sanzhar.utils.PlayerType;
import java.util.concurrent.LinkedBlockingDeque;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {


    @Test
    public void testPlayer() throws InterruptedException {

        System.out.println("Message Test");
        
        Message greetingMessage = new Message(Constants.Greeting, PlayerType.INITIATOR.name());
        Message finishMessage = new Message(Constants.FINISH, PlayerType.RESPONDENT.name());

        assertEquals(greetingMessage.getContent(), Constants.Greeting);
        assertEquals(greetingMessage.getSenderName(), PlayerType.INITIATOR.name());

        assertEquals(finishMessage.getContent(), Constants.FINISH);
        assertEquals(finishMessage.getSenderName(), PlayerType.RESPONDENT.name());
    }
}
