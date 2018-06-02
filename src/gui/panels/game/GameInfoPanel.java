package gui.panels.game;

import game.BlokusBoard;
import gui.Frame;

import javax.swing.*;
import java.awt.*;

public class GameInfoPanel extends JPanel {

    private GamePanel gamePanel;
    private String turn;
    private int score;
    private int piecesLeft;

    GameInfoPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setPreferredSize(new Dimension(Frame.WIDTH, Frame.HEIGHT - BlokusBoard.DEFAULT_RESOLUTION - 30));
    }

    public void updateValues(int turn, boolean myTurn, int score, int piecesLeft) {
        this.turn = getTurnString(turn, myTurn);
        this.score = score;
        this.piecesLeft = piecesLeft;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Frame frame = Frame.getInstance();
        frame.addText(g2d, turn, 30, 20, 50, false);
        frame.addText(g2d, "Score: " + score, 30, 300, 50, false);
        frame.addText(g2d, "Pieces left: " + piecesLeft, 30, 450, 50, false);
        gamePanel.setSurrenderButton(frame.addButton(g2d, "Surrender", 30, 700, 45, false, false));
    }

    private String getTurnString(int id, boolean myTurn) {
        String string;
        if (myTurn)
            string = "your";
        else
            string = BlokusBoard.getColorById(id) + "'s";
        return "It's " + string + " turn";
    }
}
