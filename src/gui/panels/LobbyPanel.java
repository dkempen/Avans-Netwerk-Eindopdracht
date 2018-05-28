package gui.panels;

import gui.Panel;
import network.Client;
import network.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LobbyPanel extends JPanel implements Panel {

    private JTextArea jTextArea;

    public LobbyPanel() {
        setLayout(new BorderLayout());
        jTextArea = new JTextArea();
        add(new JScrollPane(jTextArea), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {

    }

    @Override
    public void handleMouseClick(MouseEvent mouseEvent) {

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

    public void setjTextArea(String message) {
        jTextArea.append(message + "\n");
        invalidate();
        revalidate();
        repaint();
    }
}
