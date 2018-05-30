package gui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface Panel {
    void handleKeys(KeyEvent keyEvent);
    void handleMouseClick(MouseEvent mouseEvent);
    void handleMouseWheel(MouseWheelEvent mouseWheelEvent);
    void handleMouseMove(MouseEvent mouseEvent);
}
