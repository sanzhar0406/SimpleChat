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
 * @author alyce
 */
public class ServerConnection extends Thread {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private LinkedList<ServerConnection> connectionList;

    /**
     * для общения с клиентом необходим сокет (адресные данные)
     *
     * @param socket
     * @throws IOException
     */
    public ServerConnection(Socket socket, LinkedList<ServerConnection> connectionList) throws IOException {
        this.socket = socket;
        this.connectionList = connectionList;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        String message;
        try {
            while (true) {
                message = in.readLine();
                if (message.equals(Constants.FINISH)) {
                    close();
                    break; // выходим из цикла если пришло "stop"
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
     * отсылка одного сообщения клиенту по указанному потоку
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
     * закрытие сервера прерывание себя как нити и удаление из списка нитей
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
