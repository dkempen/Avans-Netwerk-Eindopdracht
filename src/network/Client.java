package network;

import gui.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private static Client instance = null;

    // IO streams
    private DataOutputStream toServer;
    private DataInputStream fromServer;

    public static Client getInstance() {
        if (instance == null)
            instance = new Client();
        return instance;
    }

    private Client() {
    }

    public void start(int port) {
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", port);
            printInfo(socket);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());

            String string = fromServer.readUTF();
            log("Recieved from, server: " + string);
            toServer.writeUTF("recieved: " + string);
        } catch (IOException ex) {
            log(ex.toString() + '\n');
        }
    }

    private void log(String message) {
        Frame.getInstance().handleClientLog(message);
    }

    private void printInfo(Socket s) {
        try {
            log("local socket address: " + s.getInetAddress());
            log("buffer: " + s.getSendBufferSize());
            log("remote socket address: " + s.getRemoteSocketAddress());
        } catch (Exception ignored) {
        }
    }

    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            try {
//                // Get the radius from the text field
//                double radius = Double.parseDouble(jtf.getText().trim());
//
//                // Send the radius to the server
//                toServer.writeDouble(radius);
//                toServer.flush();
//
//                // Get area from the server
//                double area = fromServer.readDouble();
//
//                // Display to the text area
//                jta.append("Radius is " + radius + "\n");
//                jta.append("Area received from the server is "
//                        + area + '\n');
//            }
//            catch (IOException ex) {
//                System.err.println(ex);
//            }
        }
    }
}
