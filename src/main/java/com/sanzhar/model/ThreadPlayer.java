/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanzhar.model;

import com.sanzhar.utils.PlayerType;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author Sanzhar ThreadPlayer class is a child class of AbstractPlayer class
 * implements communication with another player using LinkedBlockingDequeue
 */
public class ThreadPlayer extends AbstractPlayer {

    private LinkedBlockingDeque<Message> myMessageQueue;
    private LinkedBlockingDeque<Message> receiverMessageQueue;

    /**
     * ThreadPlayer constructor
     *
     * @param type is an enum value, either INITIATOR or RESPONDENT
     * @param myMessageQueue is a queue with this player messages
     * @param receiverMessageQueue is a queue with another player messages
     */
    public ThreadPlayer(PlayerType type, LinkedBlockingDeque<Message> myMessageQueue, LinkedBlockingDeque<Message> receiverMessageQueue) {
        super(type);
        this.myMessageQueue = myMessageQueue;
        this.receiverMessageQueue = receiverMessageQueue;
    }

    /**
     * Overriding basic AbstractPlayer send method, sending message to another
     * player by placing the message on player`s messageQueue
     *
     */
    public void send(String content) {
        super.send(content);
        Message message = new Message(content, this.name);
        receiverMessageQueue.add(message);
    }

    /**
     * Receive method grabs the message from messageQueue and increments
     * receivedCounter, takeFirst method waits until some data is placed into
     * queue
     *
     * @return a <code> Message </code> object carrying content and sender
     * @throws InterruptedException in case of failing to get queue`s last elemet
     */
    @Override
    public Message receive() throws InterruptedException {
        receivedCounter++;
        return myMessageQueue.takeFirst();
    }
}
