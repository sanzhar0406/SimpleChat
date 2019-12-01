package com.sanzhar;

import com.sanzhar.model.Message;
import com.sanzhar.model.SocketPlayer;
import com.sanzhar.model.ThreadPlayer;
import com.sanzhar.utils.ArgumentParser;
import com.sanzhar.utils.Constants;
import com.sanzhar.utils.GameType;
import com.sanzhar.utils.PlayerType;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Sanzhar
 *
 * Main class of the application
 * Parses the command line args using ArgumentParser
 * based on user preferences runs either:
 * @see #runSingle or 
 * @see #runSeparate process game
 */
public class App {

    private static App app = new App();

    private LinkedBlockingDeque<Message> initiatorMessageQueue = new LinkedBlockingDeque<>();
    private LinkedBlockingDeque<Message> receiverMessageQueue = new LinkedBlockingDeque<>();

    public static LinkedList<ServerConnection> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ArgumentParser.isValid(args);
        System.out.println(Constants.startMessage);
        if (ArgumentParser.getType(args) == GameType.SINGLE) {
            app.runSingle();
        } else {
            app.runSeparate();
        }
    }

    /**
    * Runs two threads for initiator and repondent
    * joins on successful completion
    */
    private void runSingle() {
        ThreadPlayer initiator = new ThreadPlayer(PlayerType.INITIATOR, initiatorMessageQueue, receiverMessageQueue);
        ThreadPlayer respondent = new ThreadPlayer(PlayerType.RESPONDENT, receiverMessageQueue, initiatorMessageQueue);
        Thread initiatorThread = new Thread(initiator);
        Thread respondentThread = new Thread(respondent);
        initiatorThread.start();
        respondentThread.start();
        try {
            initiatorThread.join();
            respondentThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(Constants.endMessage);
    }

    /**
    * Runs a serverThread which accepts Client connection requests
    * and creates a ServerConnection for each socket to keep connection
    * then creates initiator and respondent sockets
    */
    private void runSeparate() throws IOException {
        Thread serverThread = new Thread() {
            @Override
            public void run() {
                try {
                    runServer();
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            public void runServer() throws IOException {
                ServerSocket server = new ServerSocket(Constants.PORT);
                try {
                    synchronized (serverList) {
                        // Once both Initiator and Respondent connect
                        // no need to accept any other connection requests
                        while (serverList.size() < Constants.MAX_CONNECTIONS) {
                            Socket socket = server.accept();
                            try {
                                // adding connection to connections list
                                serverList.add(new ServerConnection(socket, serverList)); 
                            } catch (IOException e) {
                                socket.close();
                            }
                        }
                    }
                } finally {
                    server.close();
                }
            }
        };
        serverThread.start();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        SocketPlayer initiator = new SocketPlayer(PlayerType.INITIATOR, Constants.address, Constants.PORT);
        SocketPlayer respondent = new SocketPlayer(PlayerType.RESPONDENT, Constants.address, Constants.PORT);
        Thread initiatorThread = new Thread(initiator);
        Thread respondentThread = new Thread(respondent);

        initiatorThread.start();
        respondentThread.start();
        try {
            serverThread.join();
            initiatorThread.join();
            respondentThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(Constants.endMessage);
    }
}
