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
    private int currentTurn;

    // End game
    private int[] scores;
    private int winnerId;
    private boolean hasWon;

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
                String startString = fromServer.readUTF();
                handleStart(startString);

                //noinspection InfiniteLoopStatement
                while (true) {
                    if (!isBeginTurn) {
                        String updateString = fromServer.readUTF();
                        String[] update = splitMessage(updateString);
                        switch (getTopic(update[0])) {
                            case "turn":
                                updateGrid(update[1]);
                                checkForTurn(update[0]);
                                break;
                            case "end":
                                handleEnd(update);
                                break;
                        }
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
                        toServer.writeUTF("turn/" + Arrays.deepToString(blokus.getBoard().getGrid())
                                + "/" + blokus.getPlayer().getScore());
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

    private void handleStart(String startupString) {
        String[] message = splitMessage(startupString);
        if (!message[0].equals("start"))
            System.err.println("Not a start command");

        id = Integer.parseInt(getValueFromString(message[1]));
        blokus = new Blokus(this);
        checkForTurn(message[2]);
        if (isMyTurn)
            isBeginTurn = true;
        Frame.getInstance().setPanel(PanelType.GAME_PANEL);
    }

    private void handleEnd(String[] endString) {
        setWinner(getValueFromString(endString[1]));
        setScores(getValueFromString(endString[2]));
        blokus.setState(State.DONE);
        closeClient();
        Frame.getInstance().getEndPanel().updateInfo();
        Frame.getInstance().setPanel(PanelType.END_PANEL);
    }

    private void closeClient() {
        try {
            toServer.close();
            fromServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private String getValueFromString(String string) {
        return string.replaceAll(".*=", "");
    }

    private String getTopic(String string) {
        return string.split("=")[0];
    }

    private void checkForTurn(String message) {
        int turnId = Integer.parseInt(getValueFromString(message));
        currentTurn = turnId;
        isMyTurn = turnId == id;

        if (blokus.getState() == State.DONE)
            return;
        if (isMyTurn)
            blokus.setState(State.TURN);
        else
            blokus.setState(State.WAIT);
    }

    private void setWinner(String winnerString) {
        int winnerId = Integer.parseInt(winnerString);
        this.winnerId = winnerId;
        this.hasWon = winnerId == id;
    }
    private void setScores(String scores) {
        this.scores = new int[] {-1, -1, -1, -1};
        Scanner scanner = new Scanner(scores).useDelimiter("[^\\d]+");
        int index = 0;
        while (scanner.hasNext()) {
            this.scores[index] = scanner.nextInt();
            index++;
        }
    }

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

    public int getCurrentTurn() {
        return currentTurn;
    }

    public int[] getScores() {
        return scores;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public boolean hasWon() {
        return hasWon;
    }
}
