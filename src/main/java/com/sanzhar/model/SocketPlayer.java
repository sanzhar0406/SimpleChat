/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanzhar.model;

import com.sanzhar.App;
import com.sanzhar.utils.Constants;
import com.sanzhar.utils.PlayerType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sanzhar SocketPlayer class is a child class of AbstractPlayer class
 * implements communication with another player instance using socket through
 * Server
 *
 */
public class SocketPlayer extends AbstractPlayer {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    /**
     * SocketPlayer constructor
     *
     * @param type is an enum value, either INITIATOR or RESPONDENT
     * @param address is an address of server
     * @param port is a port number which server listens to Initializes input
     * and output stream for reading and writing
     * @throws IOException in case of socket creation failure
     */
    public SocketPlayer(PlayerType type, String address, int port) throws IOException {
        super(type);
        this.socket = new Socket(address, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Overriding basic AbstractPlayer send method, sending message to server
     * using BufferedWriter
     *
     */
    @Override
    public void send(String content) {
        super.send(content);
        try {
            // Message contents and sender name is merged in following way
            // in order to avoid using auxilary libraries such as JSONObject
            // & is a delimeter
            String fullMessage = content + "&" + name + "\n";
            out.write(fullMessage);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(SocketPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Receive method grabs the message and splits it into content and sender
     * name, increments receivedCounter
     *
     * @return a <code> Message </code> object carrying content and sender
     */
    @Override
    public Message receive() {
        receivedCounter++;
        String fullMessage = "&";
        try {
            fullMessage = in.readLine();
        } catch (IOException e) {
            this.close();
        }
        String[] messageContent = fullMessage.split("&");
        return new Message(messageContent[0], messageContent[1]);
    }

    /**
     * Close method sends closing signal to server and closes socket with reader
     * and writer
     *
     */
    @Override
    protected void close() {
        try {
            out.write(Constants.FINISH);
            out.flush();
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(SocketPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
