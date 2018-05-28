package game;

import gui.Frame;
import gui.panels.GamePanel;
import gui.panels.GameRenderPanel;
import network.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Blokus {

    private Client client;

    private BlokusBoard board;
    private BlokusPlayer player;
    private GamePanel gamePanel;
    private Point selected;
    private int selectedPieceIndex;

    public Blokus(Client client) {
        this.client = client;
        initGame();

        // Test
        selectedPieceIndex = 0;
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

    public void draw(Graphics2D g2d) {
        g2d.drawImage(board.draw(), 0, 0, null);
    }

    private void repaint() {
        Frame.getInstance().getGamePanel().getGameRenderPanel().repaint();
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
        player.getPieces().get(selectedPieceIndex).rotateClockwise();
        board.overlay(player.getPieces().get(selectedPieceIndex), selected.x, selected.y);
        repaint();
    }

    public void rotateCounterClockwise() {

    }

    public void flip() {

    }

    private Point getPointOnBoard(Point point) {
        return new Point((point.x - 123) / (BlokusBoard.DEFAULT_RESOLUTION / BlokusBoard.BOARD_SIZE),
                (point.y - 32) / (BlokusBoard.DEFAULT_RESOLUTION / BlokusBoard.BOARD_SIZE));
    }

    public void handleMouseMove(MouseEvent e) {
        Point p = getPointOnBoard(e.getPoint());
        if (!p.equals(selected)) {
            selected = p;
            board.overlay(player.getPieces().get(selectedPieceIndex), selected.x, selected.y);
            repaint();
        }
    }

    public void handleMouseClick() {
        System.out.println("click");
        try {
            board.placePiece(player.getPieces().get(selectedPieceIndex),
                    selected.x - BlokusPiece.SHAPE_SIZE / 2,
                    selected.y - BlokusPiece.SHAPE_SIZE / 2,
                    player.isFirstMove());
            player.getPieces().remove(selectedPieceIndex);
            player.setFirstMove(false);
            player.setCanPlay(player.getPieces().size() != 0);
            startNewTurn();
            repaint();
            Client.getInstance().setReadyToUpdate(false);
        } catch (BlokusBoard.IllegalMoveException ex) {
            System.out.println((ex.getMessage()));
        }
    }

    public BlokusBoard getBoard() {
        return board;
    }

    public BlokusPlayer getPlayer() {
        return player;
    }
}
