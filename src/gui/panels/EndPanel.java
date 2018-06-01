package gui.panels;

import gui.Panel;
import network.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class EndPanel extends JPanel implements Panel {

    private boolean hasWon;
    private int winnerId;
    private int[] scores;

    public EndPanel() {
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
        if (hasWon)
            g2d.drawString("You have won!", 10, 40);
        else
            g2d.drawString("Player " + winnerId + "has won!", 10, 40);
        g2d.drawString("Scores: " + scores, 10, 60);
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
