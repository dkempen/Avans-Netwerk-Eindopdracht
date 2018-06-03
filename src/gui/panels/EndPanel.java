package gui.panels;

import game.BlokusBoard;
import gui.Frame;
import gui.Panel;
import gui.PanelType;
import network.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

public class EndPanel extends JPanel implements Panel {

    private Frame frame;
    private boolean hasWon;
    private int winnerId;
    private int[] scores;
    private Rectangle2D backButton;
    private boolean backButtonSelected;

    public EndPanel() {
        setBackground(Frame.BACKGROUND_COLOR);
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
        String hasWonString;
        if (hasWon)
            hasWonString = "You have won!";
        else
            hasWonString = "Player " + BlokusBoard.getColorById(winnerId) + " has won!";
        frame.addText(g2d, hasWonString, 50, Frame.WIDTH / 2, 250, true);
        drawScores(g2d);

        backButton = Frame.getInstance().addButton(g2d, "Back to menu", 50, 20, 50, false, backButtonSelected);
    }

    private void drawScores(Graphics2D g2d) {
        int y = 400;
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
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
            Frame.getInstance().setPanel(PanelType.MENU_PANEL);
    }

    @Override
    public void handleMouseClick(MouseEvent mouseEvent) {
        Point relativePoint = Frame.getPoint(mouseEvent.getPoint(), this);
        if (backButton.contains(relativePoint))
            Frame.getInstance().setPanel(PanelType.MENU_PANEL);
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent mouseWheelEvent) {

    }

    @Override
    public void handleMouseMove(MouseEvent mouseEvent) {
        backButtonSelected = false;

        Point relative = Frame.getPoint(mouseEvent.getPoint(), this);
        if (backButton.contains(relative))
            backButtonSelected = true;
        repaint();
    }
}
