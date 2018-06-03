package gui.panels;

import gui.Frame;
import gui.Panel;
import gui.PanelType;
import network.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

public class HostGamePanel extends JPanel implements Panel {

    private Rectangle2D twoPlayers;
    private boolean twoPlayersSelected;
    private Rectangle2D threePlayers;
    private boolean threePlayersSelected;
    private Rectangle2D fourPlayers;
    private boolean fourPlayersSelected;

    public HostGamePanel() {
        setBackground(Frame.BACKGROUND_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        Frame.getInstance().addText(g2d, "Host Game", 100, Frame.WIDTH / 2, 150, true);
        int y = 300;
        int increment = 150;
        twoPlayers = Frame.getInstance().addButton(g2d, "2 Players", 50,
                Frame.WIDTH / 2, y, true, twoPlayersSelected);
        y += increment;
        threePlayers = Frame.getInstance().addButton(g2d, "3 Players", 50,
                Frame.WIDTH / 2, y, true, threePlayersSelected);
        y += increment;
        fourPlayers = Frame.getInstance().addButton(g2d, "4 Players", 50,
                Frame.WIDTH / 2, y, true, fourPlayersSelected);
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {
    }

    @Override
    public void handleMouseClick(MouseEvent mouseEvent) {
        Point relative = Frame.getPoint(mouseEvent.getPoint(), this);
        if (twoPlayers.contains(relative)) {
            Server.getInstance().setNumberOfClients(2);
            gui.Frame.getInstance().setPanel(PanelType.LOBBY_PANEL);
        } else if (threePlayers.contains(relative)) {
            Server.getInstance().setNumberOfClients(3);
            Frame.getInstance().setPanel(PanelType.LOBBY_PANEL);
        } else if (fourPlayers.contains(relative)) {
            Server.getInstance().setNumberOfClients(4);
            Frame.getInstance().setPanel(PanelType.RULES_PANEL);
        }
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent mouseWheelEvent) {

    }

    @Override
    public void handleMouseMove(MouseEvent mouseEvent) {
        twoPlayersSelected = false;
        threePlayersSelected = false;
        fourPlayersSelected = false;

        Point relative = Frame.getPoint(mouseEvent.getPoint(), this);
        if (twoPlayers != null && twoPlayers.contains(relative))
            twoPlayersSelected = true;
        if (threePlayers != null && threePlayers.contains(relative))
            threePlayersSelected = true;
        if (fourPlayers != null && fourPlayers.contains(relative))
            fourPlayersSelected = true;

        repaint();
    }
}
