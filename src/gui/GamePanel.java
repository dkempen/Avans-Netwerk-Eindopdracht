package gui;

import game.Blokus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel implements Panel {

    Blokus blokus;

    public void initGame() {
        blokus = new Blokus();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawString("GamePanel", 10, 20);

        blokus.draw(g2d);
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
            Frame.getInstance().setPanel(0);
    }

    @Override
    public void handleMouse(MouseEvent mouseEvent) {

    }
}
