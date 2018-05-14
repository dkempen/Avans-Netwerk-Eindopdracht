package game;

import gui.Frame;
import gui.panels.GamePanel;
import network.Client;

import javax.swing.*;
import java.awt.*;

public class Blokus {

    private Client client;

    private BlokusBoard board;
    private BlokusPlayer player;
    private GamePanel gamePanel;

    public Blokus(Client client) {
        this.client = client;
        initGame();
    }

    public void draw(Graphics2D g2d) {
        board.draw(g2d);
    }

    private void initGame() {
        board = new BlokusBoard();
        player = new BlokusPlayer(client.getId());
        initGUI();
        if (client.isMyTurn()) {

        }
    }

    private void initGUI() {
        gamePanel = Frame.getInstance().getGamePanel();
        gamePanel.init(this);
    }

    public void drawBoard() {

    }

    public void startNewTurn() {
        // Edit piecesPanel
        JPanel piecesPanel = gamePanel.getPiecesPanel();
    }

    public boolean isGameOver() {
        return false;
    }

    public void gameOver() {

    }

    public void rotateClockwise() {

    }

    public void rotateCounterClockwise() {

    }

    public void flip() {

    }

    public BlokusBoard getBoard() {
        return board;
    }

    public BlokusPlayer getPlayer() {
        return player;
    }
}
