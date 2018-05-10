package network;

import game.BlokusBoard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class GameLogic {

    private Server server;
    private ArrayList<Player> players;
    private Player current;

    private int[][] grid;

    GameLogic(Server server) {
        this.server = server;
        players = new ArrayList<>();
        grid = new int[BlokusBoard.BOARD_SIZE][BlokusBoard.BOARD_SIZE];
    }

    public void sendStartUpdate() {
        for (Player player : players) {
            try {
                // notify that the game has started, the client id, and who's turn it is
                player.output().writeUTF("start/id=" + player.getId() + "/turn=1");
                server.log("notified player " + player.getId() + " that the game has started");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Make player 1 current turn
        current = players.get(0);
    }

    public void sendUpdate() {
        String message = "turn=" + current.getId() + Arrays.deepToString(grid);
        for (Player player : players) {
            try {
                player.output().writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendEndUpdate() {
        String message = "end/" + getScores() + "/winner=" + getWinnerId();
        for (Player player : players) {
            try {
                player.output().writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNewGrid(String gridString) {
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
        grid = newGrid;
    }

    public boolean checkForEnd() {
        for (Player player : players)
            if (!player.hasSurrendered())
                return false;
        return true;
    }

    public void nextTurn() {
        while (true) {
            int currentIndex = players.indexOf(current);
            if (currentIndex >= players.size())
                currentIndex = -1;
            current = players.get(currentIndex + 1);
            if (!current.hasSurrendered())
                return;
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrent() {
        return current;
    }

    private String getScores() {
        StringBuilder scores = new StringBuilder("[");
        for (Player player : players)
            scores.append(player.getScore()).append(", ");
        scores.append("]");
        return scores.toString();
    }

    private int getWinnerId() {
        int id = 0;
        int highScore = -1;
        for (Player player : players) {
            if (player.getScore() > highScore) {
                highScore = player.getScore();
                id = player.getId();
            }
        }
        return id;
    }
}
