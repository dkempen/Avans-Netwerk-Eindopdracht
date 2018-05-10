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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    void notifyAllStart() {
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

    public Player getCurrent() {
        return current;
    }

    public void setCurrent(Player current) {
        this.current = current;
    }

    public void setNewGrid(String gridString) {
        int[][] newGrid = new int[BlokusBoard.BOARD_SIZE][BlokusBoard.BOARD_SIZE];
        Scanner scanner = new Scanner(gridString).useDelimiter("[^\\d]+");
        int x = 0;
        int y = 0;

        while (scanner.hasNext()) {
            newGrid[x][y] = scanner.nextInt();
            x++;
            if (x >= BlokusBoard.BOARD_SIZE)
                x = 0;
        }
        grid = newGrid;
    }
}
