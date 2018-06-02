package gui;

import gui.panels.*;
import gui.panels.game.GamePanel;
import network.Client;
import network.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Frame extends JFrame {

    private static Frame instance = null;

    private MenuPanel menuPanel = new MenuPanel();
    private GamePanel gamePanel = new GamePanel();
    private HostGamePanel hostGamePanel = new HostGamePanel();
    private JoinGamePanel joinGamePanel = new JoinGamePanel();
    private LobbyPanel lobbyPanel = new LobbyPanel();
    private EndPanel endPanel = new EndPanel();
    private Panel currentPanel;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    private Font font;

    public static Frame getInstance() {
        if (instance == null)
            instance = new Frame();
        return instance;
    }

    private Frame() {
        super("Blokus");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        addListeners();

        setFont();
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
            case END_PANEL:
                setPanel(endPanel);
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

    private void setFont() {
        try (InputStream in = getClass().getResourceAsStream("/font.ttf")) {
            InputStream bufferedIn = new BufferedInputStream(in);
            this.font = Font.createFont(Font.PLAIN, bufferedIn);
            this.font = this.font.deriveFont(Font.PLAIN, 80);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    private void addListeners() {
        // Shutdown procedure
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (Client.getInstance().isActive())
                    Client.getInstance().shutdown();
                dispose();
                System.exit(0);
            }
        });

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

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                currentPanel.handleMouseWheel(e);
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

    public void addText(Graphics2D g2d, String text, int size, int x, int y, boolean centered) {
        // Add text
        this.font = this.font.deriveFont((float) size);
        GlyphVector itemFontVector = font.createGlyphVector(g2d.getFontRenderContext(), text);
        AffineTransform item = new AffineTransform();
        Shape itemShape = itemFontVector.getOutline();
        if (centered)
            x -= itemShape.getBounds2D().getCenterX();
        item.translate(x, y);
        itemShape = item.createTransformedShape(itemShape);
        g2d.setColor(Color.WHITE);

        // Add shadow
        Shape shadowShape = itemShape;
        AffineTransform shadow = new AffineTransform();
        shadow.translate(2, -2);
        shadowShape = shadow.createTransformedShape(shadowShape);

        // Draw
        g2d.setColor(Color.BLACK);
        g2d.fill(shadowShape);
        g2d.setColor(Color.GRAY);
        g2d.fill(itemShape);
    }

    public EndPanel getEndPanel() {
        return endPanel;
    }
}
