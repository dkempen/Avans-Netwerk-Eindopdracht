package network;

import game.Blokus;
import game.BlokusBoard;
import game.State;
import gui.Frame;
import gui.PanelType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private static Client instance = null;
    private boolean isActive = false;

    // IO streams
    private DataOutputStream toServer;
    private DataInputStream fromServer;

    // Game
    private Blokus blokus;

    private int id;
    private boolean isMyTurn;
    private boolean readyToUpdate;
    private boolean hasSurrendered;
    private boolean isBeginTurn;

    private Client() {
    }

    public static Client getInstance() {
        if (instance == null)
            instance = new Client();
        return instance;
    }

    public void start(int port) {
        isActive = true;
        new Thread(() -> {
            try {
                // Create a socket to connect to the server
                Socket socket = new Socket("localhost", port);
                printInfo(socket);

                // Create an input stream to receive data from the server
                fromServer = new DataInputStream(socket.getInputStream());

                // Create an output stream to send data to the server
                toServer = new DataOutputStream(socket.getOutputStream());

                // Waiting for start command
                String startupString = fromServer.readUTF();
                handleStartup(startupString);

                //noinspection InfiniteLoopStatement
                while (true) {
                    if (!isBeginTurn) {
                        String updateString = fromServer.readUTF();
                        //TODO: Check for end
                        String[] update = splitMessage(updateString);
                        updateGrid(update[1]);
                        checkForTurn(update[0]);
                    }
                    isBeginTurn = false;

                    // If it's not my turn, continue
                    if (!isMyTurn)
                        continue;

                    // Wait for turn to finish
                    synchronized (this) {
                        while (!readyToUpdate) {
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    readyToUpdate = false;

                    // Check if the player placed a piece or has surrendered
                    if (!hasSurrendered) {
                        // When piece has been placed send update to server
                        toServer.writeUTF("turn/" + Arrays.deepToString(blokus.getBoard().getGrid()));
                    } else {
                        // When the player has surrendered update the server
                        toServer.writeUTF("surrender/" + blokus.getPlayer().getScore());
                    }
                }
            } catch (IOException ex) {
                log(ex.toString() + '\n');
            }
        }).start();
    }

    private void handleStartup(String startupString) {
        String[] message = splitMessage(startupString);
        if (!message[0].equals("start"))
            System.err.println("Not a start command");

        setId(message[1]);
        blokus = new Blokus(this);
        checkForTurn(message[2]);
        if (isMyTurn)
            isBeginTurn = true;
        Frame.getInstance().setPanel(PanelType.GAME_PANEL);
    }

    private void updateGrid(String gridString) {
        int[][] newGrid = new int[BlokusBoard.BOARD_SIZE][BlokusBoard.BOARD_SIZE];
        Scanner scanner = new Scanner(gridString).useDelimiter("[^\\d]+");
        int x = 0;
        int y = 0;
        //noinspection Duplicates
        while (scanner.hasNext()) {
            newGrid[x][y] = scanner.nextInt();
            x++;
            if (x >= BlokusBoard.BOARD_SIZE) {
                x = 0;
                y++;
            }
        }
        blokus.getBoard().setGrid(newGrid);
        Frame.getInstance().getGamePanel().getGameRenderPanel().repaint();
    }

    private String[] splitMessage(String message) {
        return message.split("/");
    }

    private void setId(String message) {
        id = Integer.parseInt(message.replaceAll(".*=", ""));
    }

    private void checkForTurn(String message) {
        int turnId = Integer.parseInt(message.replaceAll(".*=", ""));
        isMyTurn = turnId == id;

        if (isMyTurn)
            blokus.setState(State.TURN);
        else
            blokus.setState(State.WAIT);
    }

    // Call when player ended his turn
    public void setReadyToUpdate(boolean hasSurrendered) {
        synchronized (this) {
            readyToUpdate = true;
            this.hasSurrendered = hasSurrendered;
            notifyAll();
        }
    }

    public void shutdown() {
        // When the user closes the program, commence shutdown sequence
        try {
            toServer.writeUTF("shutdown/" + blokus.getPlayer().getScore());
        } catch (IOException e) {
            e.printStackTrace();
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

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }
}
