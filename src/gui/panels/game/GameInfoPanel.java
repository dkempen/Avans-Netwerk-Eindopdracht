package gui.panels.game;

import javax.swing.*;
import java.awt.*;

public class GameInfoPanel extends JPanel {

    private String turn;
    private int score;
    private int piecesLeft;

    GameInfoPanel() {
        setPreferredSize(new Dimension(400, 55));
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

        g2d.drawString(turn, 10, 10);
        g2d.drawString("Score: " + score, 10, 30);
        g2d.drawString("Pieces left:  " + piecesLeft, 10, 50);
    }

    private String getTurnString(int id, boolean myTurn) {
        String string = "";
        if (myTurn)
            string = "your";
        else
            switch (id) {
                case 1: string = "blue's"; break;
                case 2: string = "red's"; break;
                case 3: string = "yellow's"; break;
                case 4: string = "green's"; break;
            }
        return "It's " + string + " turn";
    }
}
