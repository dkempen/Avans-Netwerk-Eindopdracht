package gui.panels;

import gui.Frame;
import gui.PanelType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MenuPanel extends JPanel implements gui.Panel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawString("MenuPanel", 10, 20);
        g2d.drawString("1 = Host Game", 10, 40);
        g2d.drawString("2 = Join Game", 10, 60);
        Frame.getInstance().addText(g2d, "MenuPanel", 100, Frame.WIDTH / 2, 500, true);
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                gui.Frame.getInstance().setPanel(PanelType.GAME_PANEL);
                break;
            case KeyEvent.VK_1:
                gui.Frame.getInstance().setPanel(PanelType.HOST_GAME_PANEL);
                break;
            case KeyEvent.VK_2:
                Frame.getInstance().setPanel(PanelType.JOIN_GAME_PANEL);
                break;
        }
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
