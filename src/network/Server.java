package network;

import gui.Frame;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static Server instance = null;
    private boolean isActive = false;

    private ArrayList<Player> players;

    public static Server getInstance() {
        if (instance == null)
            instance = new Server();
        return instance;
    }

    private Server() {}

    public void start(int port) {
        isActive = true;
        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(port);
                players = new ArrayList<>();

                log("Server started at " + new Date());

                // Number a client
                int clientNo = 1;

                log("listening for clients...");

                // Start client on host's window
                new Thread(() -> Client.getInstance().start(port)).start();

                while (true) {
                    // Listen for a new connection request
                    Socket socket = serverSocket.accept();
                    players.add(new Player(socket));

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

    private void log(String message) {
        Frame.getInstance().handleServerLog(message);
    }

    private void startGame() {
        notifyAllStart();
        try {
            String string = players.get(0).input().readUTF();
            log("Read " + string + " from player 1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyAllStart() {
        for (Player player : players) {
            try {
                player.output().writeUTF("start");
                log("notified player that the game has started");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
