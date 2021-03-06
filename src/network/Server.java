package network;

import gui.Frame;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static Server instance = null;
    private boolean isActive = false;

    private GameLogic gameLogic;
    private ServerSocket serverSocket;

    private int numberOfClients = 4;

    private Server() {}

    public static Server getInstance() {
        if (instance == null)
            instance = new Server();
        return instance;
    }

    public void start(int port) {
        isActive = true;
        gameLogic = new GameLogic(this);

        new Thread(() -> {
            try {
                // Create a server socket
                serverSocket = new ServerSocket(port);

                log("Server started at " + new Date());

                // Number a client
                int clientNo = 1;

                log("listening for clients...");

                // Start client on host's window
                new Thread(() -> Client.getInstance().start(port)).start();

                while (true) {
                    // Listen for a new connection request
                    Socket socket = serverSocket.accept();
                    gameLogic.getPlayers().add(new Player(socket, clientNo));

                    // Display the client number
                    log("Client " + clientNo + " connected at " + new Date());

                    // Find the client's host name, and IP address
                    InetAddress inetAddress = socket.getInetAddress();
                    log("Client " + clientNo + "'s host name is " + inetAddress.getHostName());
                    log("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress());

                    if (clientNo >= numberOfClients) {
                        startGame();
                        gameLogic.closeServer();
                        return;
                    }

                    clientNo++;
                }
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startGame() {
        // Server sends start message to all clients
        gameLogic.sendStartUpdate();

        // Server waits for a response when a turn is made and loops for the entire game
        while (true) {
            try {
                String message = gameLogic.getCurrentPlayer().input().readUTF();
                String[] splitMessage = splitMessage(message);
                switch (splitMessage[0]) {
                    case "turn":
                        gameLogic.setNewGrid(splitMessage[1]);
                        gameLogic.setScore(splitMessage[2]);
                        gameLogic.nextTurn();
                        gameLogic.sendUpdate();
                        break;
                    case "surrender":
                        gameLogic.setScore(splitMessage[1]);
                        gameLogic.getCurrentPlayer().surrender();
                        // check if the game has ended when all of the player have surrendered
                        if (gameLogic.handleSurrender(false))
                            return;
                        break;
                    case "shutdown":
                        // When the user closes the program, commence shutdown sequence
                        if (gameLogic.handleSurrender(true))
                            return;
                        break;
                    default:
                        System.out.println("Message isn't recognized");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String[] splitMessage(String message) {
        return message.split("/");
    }

    void log(String message) {
        Frame.getInstance().handleServerLog(message);
    }

    public boolean isActive() {
        return isActive;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }
}
