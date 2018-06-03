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
import java.awt.geom.Rectangle2D;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Frame extends JFrame {

    private static Frame instance = null;

    private MenuPanel menuPanel = new MenuPanel();
    private RulesPanel rulesPanel = new RulesPanel();
    private HostGamePanel hostGamePanel = new HostGamePanel();
    private JoinGamePanel joinGamePanel = new JoinGamePanel();
    private LobbyPanel lobbyPanel = new LobbyPanel();
    private GamePanel gamePanel = new GamePanel();
    private EndPanel endPanel = new EndPanel();
    private Panel currentPanel;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    public static final Color BACKGROUND_COLOR = Color.DARK_GRAY;
    public static final Color TEXT_COLOR = Color.DARK_GRAY;
    public static final Color SHADOW_COLOR = Color.DARK_GRAY;
    public static final Color BUTTON_COLOR = Color.DARK_GRAY;
    public static final Color BUTTON_HIGHLIGHT_COLOR = Color.DARK_GRAY;

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
            case RULES_PANEL:
                setPanel(rulesPanel);
                break;
            case HOST_GAME_PANEL:
                setPanel(hostGamePanel);
                break;
            case JOIN_GAME_PANEL:
                setPanel(joinGamePanel);
                break;
            case LOBBY_PANEL:
                if (menuPanel.isHostPressed()) // If it came from host, then host a game
                    lobbyPanel.initHost();
                else
                    lobbyPanel.initClient();
                setPanel(lobbyPanel);
                break;
            case GAME_PANEL:
                setPanel(gamePanel);
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
        g2d.setColor(TEXT_COLOR);

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

    public Rectangle2D addButton(Graphics2D g2d, String text, int size, int x, int y, boolean centered, boolean selected) {
        // Add text
        int padding = size / 6;
        this.font = this.font.deriveFont((float) size);
        GlyphVector itemFontVector = font.createGlyphVector(g2d.getFontRenderContext(), text);
        AffineTransform item = new AffineTransform();
        Shape itemShape = itemFontVector.getOutline();
        if (centered)
            x -= itemShape.getBounds2D().getCenterX();
        item.translate(x + padding, y + padding);
        itemShape = item.createTransformedShape(itemShape);

        // Add shadow
        Shape shadowShape = itemShape;
        AffineTransform shadow = new AffineTransform();
        shadow.translate(2, -2);
        shadowShape = shadow.createTransformedShape(shadowShape);

        // Add box
        Rectangle2D bounds = itemShape.getBounds2D();
        Rectangle2D rectangle = new Rectangle2D.Double(bounds.getMinX() - padding, bounds.getMinY() - padding,
                bounds.getWidth() + padding * 2, bounds.getHeight() + padding * 2);
        if (selected)
            g2d.setColor(BUTTON_HIGHLIGHT_COLOR);
        else
            g2d.setColor(BUTTON_COLOR);

        // Draw
        g2d.fill(rectangle);
        g2d.setColor(SHADOW_COLOR);
        g2d.fill(shadowShape);
        g2d.setColor(TEXT_COLOR);
        g2d.fill(itemShape);

        return rectangle;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public static Point getPoint(Point point, Component component) {
        return SwingUtilities.convertPoint(Frame.getInstance(), point, component);
    }

    public EndPanel getEndPanel() {
        return endPanel;
    }
}
