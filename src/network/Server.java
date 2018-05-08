package network;

import gui.panels.LobbyPanel;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class Server {
    // Text area for displaying contents
    private JTextArea jTextArea = new JTextArea();
    private LobbyPanel lobbyPanel;

    public Server(LobbyPanel lobbyPanel, int port) {
        this.lobbyPanel = lobbyPanel;
        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(port);

                jTextArea.append("Server started at " + new Date() + '\n');
                System.out.println("Server started at " + new Date() + '\n');
                lobbyPanel.setjTextArea(jTextArea);

                // Number a client
                int clientNo = 1;

                System.out.println("listening...");

                while (true) {
                    // Listen for a new connection request
                    Socket socket = serverSocket.accept();

                    // Display the client number
                    jTextArea.append("Starting thread for client " + clientNo +
                            " at " + new Date() + '\n');
                    lobbyPanel.setjTextArea(jTextArea);

                    // Find the client's host name, and IP address
                    InetAddress inetAddress = socket.getInetAddress();
                    jTextArea.append("TestClient " + clientNo + "'s host name is "
                            + inetAddress.getHostName() + "\n");
                    jTextArea.append("TestClient " + clientNo + "'s IP Address is "
                            + inetAddress.getHostAddress() + "\n");
                    lobbyPanel.setjTextArea(jTextArea);

                    // Create a new thread for the connection
                    HandleAClient task = new HandleAClient(socket);

                    // Start the new thread
                    new Thread(task).start();

                    // Increment clientNo
                    clientNo++;
                }
            }
            catch(IOException ex) {
                System.err.println(ex);
            }
        }).start();
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

                    jTextArea.append("radius received from client: " +
                            radius + '\n');
                    jTextArea.append("Area found: " + area + '\n');
                    lobbyPanel.setjTextArea(jTextArea);
                }
            }
            catch(IOException e) {
                System.err.println(e);
            }
        }
    }
}
