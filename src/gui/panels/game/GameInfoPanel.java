package gui.panels.game;

import javax.swing.*;
import java.awt.*;

public class GameInfoPanel extends JPanel {

    private int turn;
    private int score;
    private int piecesLeft;

    GameInfoPanel() {
        setPreferredSize(new Dimension(400, 80));
    }

    public void updateValues(int turn, int score, int piecesLeft) {
        this.turn = turn;
        this.score = score;
        this.piecesLeft = piecesLeft;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawString("It's " + turn + "'s turn", 10, 20);
        g2d.drawString("Score: " + score, 10, 40);
        g2d.drawString("Pieces left:  " + piecesLeft, 10, 60);
    }
}
