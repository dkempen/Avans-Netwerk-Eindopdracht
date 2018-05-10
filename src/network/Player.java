package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player {

    private int id;
    private boolean surrendered;
    private int score;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    Player(Socket socket, int id) {
        this.id = id;
        this.surrendered = false;
        try {
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataInputStream input() {
        return dataInputStream;
    }

    public DataOutputStream output() {
        return dataOutputStream;
    }

    public int getId() {
        return id;
    }

    public boolean hasSurrendered() {
        return surrendered;
    }

    public void surrender(int score) {
        surrendered = true;
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
