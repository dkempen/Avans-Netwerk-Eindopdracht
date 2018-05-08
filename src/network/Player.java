package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player {
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    Player(Socket socket) {
        this.socket = socket;
        try {
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream input() {
        return dataInputStream;
    }

    public DataOutputStream output() {
        return dataOutputStream;
    }
}
