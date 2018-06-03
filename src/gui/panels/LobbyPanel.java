package gui.panels;

import gui.Frame;
import gui.Panel;
import network.Client;
import network.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

public class LobbyPanel extends JPanel implements Panel {

    public LobbyPanel() {
        setBackground(Frame.BACKGROUND_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        Frame.getInstance().addText(g2d, "Lobby", 50, Frame.WIDTH / 2 - 30, 100, true);
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

    public void initHost() {
        Server.getInstance().start(8000);
    }

    public void initClient() {
        Client.getInstance().start(8000);
    }

}
