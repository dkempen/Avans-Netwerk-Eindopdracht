package gui.panels;

import javax.swing.*;
import java.awt.*;

public class GameRenderPanel extends JPanel {

    private GamePanel gamePanel;

    public GameRenderPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawString("GamePanel", 10, 20);

        gamePanel.blokus.draw(g2d);
    }
}
