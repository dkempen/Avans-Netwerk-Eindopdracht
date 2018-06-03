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

        Frame.getInstance().addText(g2d, "1. Each player chooses a color and places that set of 21 pieces in front of his/her side of the board."
                , 20,Frame.WIDTH/2,200, true);

        Frame.getInstance().addText(g2d,"The order of play is as follows: blue, yellow, red, and then green",20,Frame.WIDTH/2 - 120,225,true);

        Frame.getInstance().addText(g2d,"2. The first player (blue) places any of his/her pieces in a corner square.",20,Frame.WIDTH/2 - 105 , 255,true);

        Frame.getInstance().addText(g2d,"If you want to place a piece on the board in needs to be alligned with the corner of your pieces",20,Frame.WIDTH/2 + 10,280,true);

        Frame.getInstance().addText(g2d,"3. Play continues as each player lays down one piece during a turn.",20,Frame.WIDTH/2 - 122,305,true);


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
