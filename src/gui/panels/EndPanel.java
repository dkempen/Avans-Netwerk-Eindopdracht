package gui.panels;

import game.BlokusBoard;
import gui.Frame;
import gui.Panel;
import network.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Arrays;

public class EndPanel extends JPanel implements Panel {

    private Frame frame;
    private boolean hasWon;
    private int winnerId;
    private int[] scores;

    public EndPanel() {
    }

    public void updateInfo() {
        frame = Frame.getInstance();
        Client client = Client.getInstance();
        hasWon = client.hasWon();
        winnerId = client.getWinnerId();
        scores = client.getScores();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawString("EndPanel", 10, 20);
        String hasWonString;
        if (hasWon)
            hasWonString = "You have won!";
        else
            hasWonString = "Player " + BlokusBoard.getColorById(winnerId) + " has won!";
        frame.addText(g2d, hasWonString, 50, Frame.WIDTH / 2, 100, true);
        drawScores(g2d);
    }

    private void drawScores(Graphics2D g2d) {
        int y = 300;
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] == -1)
                continue;
            frame.addText(g2d, BlokusBoard.getColorById(i + 1), 50, Frame.WIDTH / 2 - 200, y, false);
            frame.addText(g2d, scores[i] + "", 50, Frame.WIDTH / 2 + 150, y, false);
            y += 60;
        }
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {

    }

    @Override
    public void handleMouseClick(MouseEvent mouseEvent) {

    }

    @Override
    public void handleMouseWheel(MouseWheelEvent mouseWheelEvent) {

    }

    @Override
    public void handleMouseMove(MouseEvent mouseEvent) {

    }
}
