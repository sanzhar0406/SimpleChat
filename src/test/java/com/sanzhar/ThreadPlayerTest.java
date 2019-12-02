package com.sanzhar;

import com.sanzhar.model.Message;
import com.sanzhar.model.ThreadPlayer;
import org.junit.jupiter.api.Test;
import com.sanzhar.utils.Constants;
import com.sanzhar.utils.PlayerType;
import java.util.concurrent.LinkedBlockingDeque;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadPlayerTest {

    public LinkedBlockingDeque<Message> initiatorMessageQueue = new LinkedBlockingDeque<>();
    public LinkedBlockingDeque<Message> receiverMessageQueue = new LinkedBlockingDeque<>();

    @Test
    public void testPlayer() throws InterruptedException {

        ThreadPlayer initiator = new ThreadPlayer(PlayerType.INITIATOR, initiatorMessageQueue, receiverMessageQueue);
        ThreadPlayer respondent = new ThreadPlayer(PlayerType.RESPONDENT, receiverMessageQueue, initiatorMessageQueue);
        
        initiator.send(Constants.Greeting);
        Message expectedReceivedMessage = new Message(Constants.Greeting, PlayerType.INITIATOR.name());
        Message receivedMessage = respondent.receive();
        assertEquals(receivedMessage.getContent(), expectedReceivedMessage.getContent());
        assertEquals(receivedMessage.getSenderName(), expectedReceivedMessage.getSenderName());
        assertEquals(initiator.getSentCounter(), 1);
        assertEquals(initiator.getReceivedCounter(), 0);
        assertEquals(respondent.getSentCounter(), 0);
        assertEquals(respondent.getReceivedCounter(), 1);

    }
}
