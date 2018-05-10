package network;

import gui.Frame;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static Server instance = null;
    private boolean isActive = false;

    private GameLogic gameLogic;

    public static Server getInstance() {
        if (instance == null)
            instance = new Server();
        return instance;
    }

    private Server() {}

    public void start(int port) {
        isActive = true;
        gameLogic = new GameLogic(this);

        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(port);

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

                    if (clientNo >= 2) {
                        startGame();
                        return;
                    }

                    clientNo++;
                }
            }
            catch(IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }

    private void startGame() {
        // Server sends start message to all clients
        gameLogic.notifyAllStart();

        // Server waits for a response when a turn is made and loops for the entire game
        while (true) {
            try {
                String newTurn = gameLogic.getCurrent().input().readUTF();
                switch (getIntent(newTurn)[0]) {
                    case "turn":
                        gameLogic.setNewGrid(getIntent(newTurn)[1]);
                        break;
                    case "surrender":
                        break;
                    default:
                        System.out.println("Turn isn't recognized");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String[] getIntent(String message) {
        return message.split("/");
    }

    void log(String message) {
        Frame.getInstance().handleServerLog(message);
    }

    // Inner class
    // Define the thread class for handling new connection
    class HandleAClient implements Runnable {
        private Socket socket; // A connected socket

        /** Construct a thread */
        HandleAClient(Socket socket) {
            this.socket = socket;
        }

        /** Run a thread */
        public void run() {
            try {
                // Create data input and output streams
                DataInputStream inputFromClient = new DataInputStream(
                        socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(
                        socket.getOutputStream());

                // Continuously serve the client
                while (true) {
                    // Receive radius from the client
                    double radius = inputFromClient.readDouble();

                    // Compute area
                    double area = radius * radius * Math.PI;

                    // Send area back to the client
                    outputToClient.writeDouble(area);

                    log("radius received from client: " +
                            radius + '\n');
                    log("Area found: " + area + '\n');
                }
            }
            catch(IOException e) {
                System.err.println(e);
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }
}
