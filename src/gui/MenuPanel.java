package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel implements Panel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawString("MenuPanel", 10, 20);
        g2d.drawString("1 = Host Game", 10, 40);
        g2d.drawString("2 = Join Game", 10, 60);
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                Frame.getInstance().setPanel(1);
                break;
            case KeyEvent.VK_1:
                Frame.getInstance().setPanel(2);
                break;
            case KeyEvent.VK_2:
                Frame.getInstance().setPanel(3);
                break;
        }
    }

    @Override
    public void handleMouse(MouseEvent mouseEvent) {

    }
}
