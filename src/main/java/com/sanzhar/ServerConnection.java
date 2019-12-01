/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanzhar;

import com.sanzhar.utils.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author Sanzhar, ServerConnection class, for communication with clients
 */
public class ServerConnection extends Thread {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private LinkedList<ServerConnection> connectionList;

    /**
     * ServerConnection constructor
     *
     * @param socket, for communication
     * @param connectionList list of connected sockets
     * @throws IOException
     */
    public ServerConnection(Socket socket, LinkedList<ServerConnection> connectionList) throws IOException {
        this.socket = socket;
        this.connectionList = connectionList;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    /**
     * run method, reads message from clients and sends to other connected clients
     *
     */
    @Override
    public void run() {
        String message;
        try {
            while (true) {
                message = in.readLine();
                if (message.equals(Constants.FINISH)) {
                    close();
                    break;
                }
                for (ServerConnection connection : connectionList) {
                    if (connection.equals(this)) {
                        continue;
                    }
                    connection.send(message);
                }
            }
        } catch (IOException e) {
            this.close();
        }
    }

    /**
     * send method, writes message as a string into
     *
     * @param msg
     */
    private void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {
        }

    }

    /**
     * gracefully closing connection
     */
    private void close() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ServerConnection connection : connectionList) {
                    if (connection.equals(this)) {
                        continue;
                    }
                    connectionList.remove(connection);
                }
            }
        } catch (IOException ignored) {
        }
    }
}
