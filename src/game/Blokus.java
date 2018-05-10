package game;

import java.awt.*;

public class Blokus {

    private BlokusPlayer[] players;
    private BlokusBoard board;

    public Blokus() {
        board = new BlokusBoard();
        initGUI();
    }

    public void draw(Graphics2D g2d) {
        board.draw(g2d);
    }

    private void initGUI() {

    }

    public void drawBoard() {

    }

    public void startNewTurn() {

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

    public BlokusPlayer getPlayer(int id) {
        return players[id - 1];
    }
}
