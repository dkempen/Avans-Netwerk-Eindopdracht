package gui;

import gui.panels.*;
import network.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame {

    private static Frame instance = null;

    private MenuPanel menuPanel = new MenuPanel();
    private GamePanel gamePanel = new GamePanel();
    private HostGamePanel hostGamePanel = new HostGamePanel();
    private JoinGamePanel joinGamePanel = new JoinGamePanel();
    private LobbyPanel lobbyPanel = new LobbyPanel();
    private Panel currentPanel;

    public static Frame getInstance() {
        if (instance == null)
            instance = new Frame();
        return instance;
    }

    private Frame() {
        super("Blokus");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 800));
        setResizable(false);
        setLocationRelativeTo(null);
        addListeners();

        setPanel(PanelType.MENU_PANEL);
        setVisible(true);
    }

    public void setPanel(PanelType panelType) {
        switch (panelType) {
            case MENU_PANEL:
                setPanel(menuPanel);
                break;
            case GAME_PANEL:
                setPanel(gamePanel);
                break;
            case HOST_GAME_PANEL:
                setPanel(hostGamePanel);
                break;
            case JOIN_GAME_PANEL:
                setPanel(joinGamePanel);
                break;
            case LOBBY_PANEL:
                if (currentPanel instanceof HostGamePanel) // If it came from host, then host a game
                    lobbyPanel.initHost();
                else
                    lobbyPanel.initClient();
                setPanel(lobbyPanel);
                break;
            default: // Psych! That's the wrong panel!
                System.out.println("Panel doesn't exist!");
                break;
        }
    }

    private void setPanel(Panel panel) {
        currentPanel = panel;
        setContentPane((JPanel) panel);
        revalidate();
        repaint();
    }

    public GamePanel getGamePanel() {
        return gamePanel;
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
                currentPanel.handleMouseClick(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                currentPanel.handleMouseMove(e);
            }
        });
    }

    public void handleServerLog(String log) {
        lobbyPanel.setjTextArea(log);
    }

    public void handleClientLog(String log) {
        if (!Server.getInstance().isActive())
            lobbyPanel.setjTextArea(log);
    }
}
