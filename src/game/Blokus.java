package game;

import gui.Frame;
import gui.panels.game.GamePanel;
import network.Client;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Blokus {

    private Client client;

    private BlokusBoard board;
    private BlokusPlayer player;
    private GamePanel gamePanel;
    private Point selected;
    private int selectedPieceIndex;
    private boolean isValidPlacement;

    private State currentState;

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
    }

    private void initGUI() {
        gamePanel = Frame.getInstance().getGamePanel();
        gamePanel.init(this);
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(board.draw(isValidPlacement), 0, 0, null);
    }

    private void repaint() {
        Frame.getInstance().getGamePanel().getGameRenderPanel().repaint();
    }

    public void setState(State state) {
        currentState = state;
        Frame.getInstance().getGamePanel().getInfoPanel().updateValues(
                client.getCurrentTurn(), player.getScore(), player.getPieces().size()
        );
    }

    private void rotateClockwise() {
        player.getPieces().get(selectedPieceIndex).rotateClockwise();
        board.overlay(player.getPieces().get(selectedPieceIndex), selected.x, selected.y);
        repaint();
    }

    private void rotateCounterClockwise() {
        player.getPieces().get(selectedPieceIndex).rotateCounterClockwise();
        board.overlay(player.getPieces().get(selectedPieceIndex), selected.x, selected.y);
        repaint();
    }

    private void flip() {
        player.getPieces().get(selectedPieceIndex).flipOver();
        board.overlay(player.getPieces().get(selectedPieceIndex), selected.x, selected.y);
        repaint();
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
            checkIfPieceIsValid();
            repaint();
        }
    }

    public void handleMouseWheel(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0)
            rotateClockwise();
        else
            rotateCounterClockwise();
        checkIfPieceIsValid();
    }

    public void handleMouseClick(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1: // Left mouse button: place piece
                // If it's not the players turn
                if (currentState != State.TURN)
                    return;
                try {
                    board.placePiece(player.getPieces().get(selectedPieceIndex),
                            selected.x - BlokusPiece.SHAPE_SIZE / 2,
                            selected.y - BlokusPiece.SHAPE_SIZE / 2,
                            player.isFirstMove());
                    player.getPieces().remove(selectedPieceIndex);
                    gamePanel.getPiecesPanel().remove(getSelectedPieceIndex());
                    player.setFirstMove(false);
                    isValidPlacement = true;
                    repaint();
                    Client.getInstance().setReadyToUpdate(false);
                } catch (BlokusBoard.IllegalMoveException ex) {
                    isValidPlacement = false;
                    System.out.println((ex.getMessage()));
                }

                checkIfFinished();
                break;
            case MouseEvent.BUTTON3: // Right mouse button: flip piece
                flip();
                break;
        }
    }

    private void checkIfPieceIsValid() {
        try {
            board.isValidMove(player.getPieces().get(selectedPieceIndex),
                    selected.x - BlokusPiece.SHAPE_SIZE / 2,
                    selected.y - BlokusPiece.SHAPE_SIZE / 2,
                    player.isFirstMove());
            isValidPlacement = true;
        } catch (BlokusBoard.IllegalMoveException e) {
            isValidPlacement = false;
        }
    }

    private void checkIfFinished() {
        // if the player still has pieces left, then he hasn't won
        if (player.getPieces().size() == 0) {
            setState(State.DONE);
            Client.getInstance().setReadyToUpdate(true);
        }
    }

    public BlokusBoard getBoard() {
        return board;
    }

    public BlokusPlayer getPlayer() {
        return player;
    }

    public void setSelectedPieceIndex(int selectedPieceIndex) {
        this.selectedPieceIndex = selectedPieceIndex;
    }

    public int getSelectedPieceIndex(){
        return selectedPieceIndex;
    }
}
