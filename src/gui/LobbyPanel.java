package gui;

import network.Client;
import network.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LobbyPanel extends JPanel implements Panel {

    private Server server;
    private Client client;

    private JTextArea jTextArea;

    public LobbyPanel() {
        setLayout(new BorderLayout());
        jTextArea = new JTextArea();
        jTextArea.append("test\n");
        add(new JScrollPane(jTextArea), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {

    }

    @Override
    public void handleMouse(MouseEvent mouseEvent) {

    }

    public void initHost() {
        server = new Server(this, 8000);
    }

    public void initClient() {
        client = new Client(this, 8000);
    }

    public void setjTextArea(JTextArea jTextArea) {
        System.out.println("set");
        this.jTextArea.setText(jTextArea.getText());
        invalidate();
        revalidate();
        repaint();
    }
}
