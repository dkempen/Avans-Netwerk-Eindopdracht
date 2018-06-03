package gui.panels.game;

import gui.Frame;

import javax.swing.*;
import java.awt.*;

public class GameRenderPanel extends JPanel {

    private GamePanel gamePanel;

    GameRenderPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setBackground(Frame.BACKGROUND_COLOR);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        gamePanel.getBlokus().draw(g2d);
    }
}
