/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanzhar.model;

import com.sanzhar.utils.Constants;
import com.sanzhar.utils.PlayerType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sanzhar AbstractPlayer class is an abstract class for Player
 * instance, implements Runnable and Player interface, it is basic functionality
 * of Player instance, children of AbstractPlayer class realize communication
 * methods
 *
 */
public abstract class AbstractPlayer implements Runnable, Player {

    protected String name;
    protected int receivedCounter = 0;
    protected int sentCounter = 0;
    protected PlayerType type;
    protected static final int TOTAL_MESSAGE_COUNT = 10;

    /**
     * AbstractPlayer constructor
     *
     * @param type is an enum value, either INITIATOR or RESPONDENT
     */
    public AbstractPlayer(PlayerType type) {
        this.name = type.name();
        this.type = type;
    }

    /**
     * Implementation of Runnable interface run method If current player is
     * initiator, sends greeting Later on exchanging messages until received and
     * sent messages count is 10
     *
     */
    @Override
    public void run() {
        if (type == PlayerType.INITIATOR) {
            this.send(Constants.Greeting);
        }
        while (receivedCounter < TOTAL_MESSAGE_COUNT) {
            try {
                Message receivedMessage = receive();
                System.out.println(name + " received message : \"" + receivedMessage.getContent() + "\" from : " + receivedMessage.getSenderName());
                String newMessageContents = receivedMessage.getContent() + " " + sentCounter;
                Thread.sleep(1000);
                if (sentCounter < TOTAL_MESSAGE_COUNT) {
                    this.send(newMessageContents);
                } else {
                    break;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadPlayer.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
        close();
    }

    /**
     * send method is stating which content and who is going to send increments
     * sentCounter value
     *
     */
    @Override
    public void send(String content) {
        System.out.println(name + " sending message : \"" + content);
        sentCounter++;
    }

    /**
     * Receive method to be implemented by children of the AbstractPlayer class
     *
     */
    @Override
    public Message receive() throws InterruptedException {
        return new Message();
    }

    /**
     * Method for graceful closing
     *
     */
    protected void close() {
    }
    
    public int getReceivedCounter() {
        return receivedCounter;
    }

    public int getSentCounter() {
        return sentCounter;
    }
}
