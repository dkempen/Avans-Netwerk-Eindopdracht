package gui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface Panel {
    void handleKeys(KeyEvent keyEvent);
    void handleMouse(MouseEvent mouseEvent);
}
