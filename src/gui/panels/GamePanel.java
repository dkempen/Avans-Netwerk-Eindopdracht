package gui.panels;

import game.Blokus;
import game.BlokusPiece;
import gui.Frame;
import gui.PanelType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel implements gui.Panel {

    Blokus blokus;
    private JPanel piecesPanel;

    public GamePanel() {
        setLayout(new FlowLayout());
        JPanel panel = new JPanel();
        add(panel);
    }

    public void init(Blokus blokus) {
        this.blokus = blokus;
        GameRenderPanel gameRenderPanel = new GameRenderPanel(this);
        setLayout(new BorderLayout());

        JScrollPane jScrollPane = initScrollPane();

        add(jScrollPane, BorderLayout.WEST);
        add(gameRenderPanel, BorderLayout.CENTER);
    }

    private JScrollPane initScrollPane() {
        piecesPanel = new JPanel();
        piecesPanel.setLayout(new BoxLayout(piecesPanel, BoxLayout.Y_AXIS));
        JScrollPane jScrollPane = new JScrollPane(piecesPanel);
        jScrollPane.setPreferredSize(new Dimension(BlokusPiece.DEFAULT_RESOLUTION, BlokusPiece.DEFAULT_RESOLUTION));


        return jScrollPane;
    }

    public JPanel getPiecesPanel() {
        return piecesPanel;
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
            Frame.getInstance().setPanel(PanelType.MENU_PANEL);
    }

    @Override
    public void handleMouse(MouseEvent mouseEvent) {

    }
}
