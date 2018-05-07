package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Frame extends JFrame {

    static Frame instance = null;

    MenuPanel menuPanel = new MenuPanel(); // 0
    GamePanel gamePanel = new GamePanel(); // 1
    HostGamePanel hostGamePanel = new HostGamePanel(); // 2
    JoinGamePanel joinGamePanel = new JoinGamePanel(); // 3
    LobbyPanel lobbyPanel = new LobbyPanel(); // 4
    Panel currentPanel;

    public static Frame getInstance() {
        if (instance == null)
            instance = new Frame();
        return instance;
    }

    public Frame() {
        super("Blokus");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 800));
        setResizable(false);
        setLocationRelativeTo(null);
        addListeners();

        setPanel(0);
        setVisible(true);
    }

    public void setPanel(int panelNumber) {
        switch (panelNumber) {
            case 0: // MenuPanel
                setPanel(menuPanel);
                break;
            case 1: // GamePanel
                setPanel(gamePanel);
                gamePanel.initGame();
                break;
            case 2: // hostGamePanel
                setPanel(hostGamePanel);
                break;
            case 3: // JoinGamePanel
                setPanel(joinGamePanel);
                break;
            case 4: // LobbyPanel
                if (currentPanel instanceof HostGamePanel) // If it came from host, then host a game
                    lobbyPanel.initHost();
                else
                    lobbyPanel.initClient();
                setPanel(lobbyPanel);
                break;
            default: // Psych! That's the wrong number!
                System.out.println("Panel number doesn't exist!");
                break;
        }
    }

    private void setPanel(Panel panel) {
        currentPanel = panel;
        setContentPane((JPanel) panel);
        revalidate();
        repaint();
    }

    private void addListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                currentPanel.handleKeys(e);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                currentPanel.handleMouse(e);
            }
        });
    }
}
