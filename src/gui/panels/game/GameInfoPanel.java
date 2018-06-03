package gui.panels.game;

import game.BlokusBoard;
import gui.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class GameInfoPanel extends JPanel {

    private GamePanel gamePanel;
    private String turn;
    private int score;
    private int piecesLeft;

    private Rectangle2D surrenderButton;
    private boolean surrenderButtonSelected;
    private String surrenderButtonText;

    GameInfoPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setPreferredSize(new Dimension(Frame.WIDTH, Frame.HEIGHT - BlokusBoard.DEFAULT_RESOLUTION - 30));
        setBackground(Frame.BACKGROUND_COLOR);
        surrenderButtonText = "Surrender";
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
        frame.addText(g2d, turn, 40, 20, 50, false);
        surrenderButton = (frame.addButton(g2d, surrenderButtonText, 40, 20, 100, false, surrenderButtonSelected));
        frame.addText(g2d, "Score: " + score, 40, 600, 50, false);
        frame.addText(g2d, "Pieces left: " + piecesLeft, 40, 600, 100, false);
    }

    private String getTurnString(int id, boolean myTurn) {
        String string;
        if (myTurn)
            string = "your";
        else
            string = BlokusBoard.getColorById(id) + "'s";
        return "It's " + string + " turn";
    }

    public void handleMouseMove(MouseEvent mouseEvent) {
        surrenderButtonSelected = false;

        Point relative = Frame.getPoint(mouseEvent.getPoint(), this);
        if (surrenderButton != null && surrenderButton.contains(relative))
            surrenderButtonSelected = true;
        repaint();
    }

    public void handleMouseClick(MouseEvent mouseEvent) {
        Point relative = Frame.getPoint(mouseEvent.getPoint(), this);
        if (surrenderButton.contains(relative)) {
            gamePanel.getBlokus().surrender();
            surrenderButtonText = "You have surrendered";
        }
    }
}
