package gui.panels;

import gui.Frame;
import gui.Panel;
import gui.PanelType;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class HostGamePanel extends JPanel implements Panel {
    @Override
    public void handleKeys(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
            Frame.getInstance().setPanel(PanelType.LOBBY_PANEL);
    }

    @Override
    public void handleMouseClick(MouseEvent mouseEvent) {

    }

    @Override
    public void handleMouseMove(MouseEvent mouseEvent) {

    }
}
