package gui.panels;

import gui.Frame;
import gui.PanelType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

public class MenuPanel extends JPanel implements gui.Panel {

    private Rectangle2D hostButton;
    private boolean hostButtonSelected;
    private Rectangle2D joinButton;
    private boolean joinButtonSelected;
    private Rectangle2D rulesButton;
    private boolean rulesButtonSelected;

    public MenuPanel() {
        setBackground(Frame.BACKGROUND_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        hostButton = Frame.getInstance().addButton(g2d, "Host Game", 100,
                Frame.WIDTH / 2, Frame.HEIGHT / 2 + -100, true, hostButtonSelected);
        joinButton = Frame.getInstance().addButton(g2d, "Join Game", 100,
                Frame.WIDTH / 2, Frame.HEIGHT / 2 + 100, true, joinButtonSelected);
        rulesButton = Frame.getInstance().addButton(g2d, "Rules", 100,
                Frame.WIDTH / 2, Frame.HEIGHT / 2 + 300, true, rulesButtonSelected);

        Frame.getInstance().addText(g2d,"Blokus!", 200,Frame.WIDTH/2 + 20,150,true);
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {
    }

    @Override
    public void handleMouseClick(MouseEvent mouseEvent) {
        Point relative = Frame.getPoint(mouseEvent.getPoint(), this);
        if (hostButton.contains(relative))
            gui.Frame.getInstance().setPanel(PanelType.LOBBY_PANEL);
        if (joinButton.contains(relative))
            Frame.getInstance().setPanel(PanelType.LOBBY_PANEL);
        if (rulesButton.contains(relative)) {
            rulesButtonSelected = false;
            Frame.getInstance().setPanel(PanelType.RULES_PANEL);
        }
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent mouseWheelEvent) {

    }

    @Override
    public void handleMouseMove(MouseEvent mouseEvent) {
        hostButtonSelected = false;
        joinButtonSelected = false;
        rulesButtonSelected = false;

        Point relative = Frame.getPoint(mouseEvent.getPoint(), this);
        if (hostButton.contains(relative))
            hostButtonSelected = true;
        if (joinButton.contains(relative))
            joinButtonSelected = true;
        if (rulesButton.contains(relative))
            rulesButtonSelected = true;
        repaint();
    }

    public boolean isHostPressed() {
        return hostButtonSelected;
    }
}
