package gui.panels;

import gui.Frame;
import gui.Panel;
import gui.PanelType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

public class RulesPanel extends JPanel implements Panel {

    private Rectangle2D backButton;
    private boolean backButtonSelected;

    public RulesPanel() {
        setBackground(Frame.BACKGROUND_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        backButton = gui.Frame.getInstance().addButton(g2d, "Back", 100,
                20, 100, false, backButtonSelected);
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {

    }

    @Override
    public void handleMouseClick(MouseEvent mouseEvent) {
        Point relative = Frame.getPoint(mouseEvent.getPoint(), this);
        if (backButton.contains(relative)) {
            backButtonSelected = false;
            gui.Frame.getInstance().setPanel(PanelType.MENU_PANEL);
        }
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent mouseWheelEvent) {

    }

    @Override
    public void handleMouseMove(MouseEvent mouseEvent) {
        backButtonSelected = false;

        Point relative = Frame.getPoint(mouseEvent.getPoint(), this);
        if (backButton.contains(relative))
            backButtonSelected = true;
        repaint();
    }
}
