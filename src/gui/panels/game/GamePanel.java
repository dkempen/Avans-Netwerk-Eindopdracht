package gui.panels.game;

import game.Blokus;
import game.BlokusPiece;
import gui.Frame;
import gui.PanelType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements gui.Panel {

    private Blokus blokus;
    private JPanel piecesPanel;
    private GameRenderPanel gameRenderPanel;
    private GameInfoPanel gameInfoPanel;
    private int pieceIndex;

    public GamePanel() {
        setLayout(new FlowLayout());
        JPanel panel = new JPanel();
        add(panel);
    }

    public void init(Blokus blokus) {
        this.blokus = blokus;
        setLayout(new BorderLayout());

        gameRenderPanel = new GameRenderPanel(this);
        gameInfoPanel = new GameInfoPanel(this);
        JScrollPane jScrollPane = initScrollPane();

        add(jScrollPane, BorderLayout.WEST);
        add(gameRenderPanel, BorderLayout.CENTER);
        add(gameInfoPanel, BorderLayout.SOUTH);
    }

    private JScrollPane initScrollPane() {
        piecesPanel = new JPanel();
        piecesPanel.setLayout(new BoxLayout(piecesPanel, BoxLayout.Y_AXIS));
        piecesPanel.setBackground(Frame.BACKGROUND_COLOR);
        JScrollPane jScrollPane = new JScrollPane(piecesPanel);
        jScrollPane.setPreferredSize(new Dimension(BlokusPiece.DEFAULT_RESOLUTION + 23, BlokusPiece.DEFAULT_RESOLUTION));
        jScrollPane.getVerticalScrollBar().setUnitIncrement(BlokusPiece.DEFAULT_RESOLUTION / 3);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));

        updatePiecePanel();

        return jScrollPane;
    }

    public void updatePiecePanel() {
        piecesPanel.removeAll();

        if (blokus.getSelectedPieceIndex() > -1) {
            for (int i = 0; i < blokus.getPlayer().getPieces().size(); i++) {
                BlokusPieceLabel pieceLabel =
                        new BlokusPieceLabel(i, blokus.getPlayer().getPieces().get(i), BlokusPiece.DEFAULT_RESOLUTION);
                pieceLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        BlokusPieceLabel bp = (BlokusPieceLabel) e.getComponent();
                        clearBorder((JComponent) piecesPanel.getComponent(blokus.getSelectedPieceIndex()));
                        pieceIndex = bp.pieceIndex;
                        blokus.setSelectedPieceIndex(pieceIndex);
                        drawBorder();
                    }
                });
                pieceLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                piecesPanel.add(pieceLabel);
            }
            pieceIndex = 0;
            setAllBorders();
            drawBorder();
        }
        piecesPanel.revalidate();
        piecesPanel.repaint();
    }

    private void drawBorder() {
        JComponent piece = (JComponent) piecesPanel.getComponent(blokus.getSelectedPieceIndex());
        piece.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
    }

    private void clearBorder(JComponent piece) {
        piece.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
    }

    private void setAllBorders() {
        for (Component piece : piecesPanel.getComponents())
            clearBorder((JComponent) piece);
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
            Frame.getInstance().setPanel(PanelType.MENU_PANEL);
    }

    @Override
    public void handleMouseClick(MouseEvent mouseEvent) {
        blokus.handleMouseClick(mouseEvent);
        gameInfoPanel.handleMouseClick(mouseEvent);
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent mouseWheelEvent) {
        blokus.handleMouseWheel(mouseWheelEvent);
    }

    @Override
    public void handleMouseMove(MouseEvent mouseEvent) {
        blokus.handleMouseMove(mouseEvent);
        gameInfoPanel.handleMouseMove(mouseEvent);
    }

    public GameRenderPanel getGameRenderPanel() {
        return gameRenderPanel;
    }

    public GameInfoPanel getGameInfoPanel() {
        return gameInfoPanel;
    }

    public Blokus getBlokus() {
        return blokus;
    }

    public static class BlokusPieceLabel extends JLabel {
        private int pieceIndex;

        BlokusPieceLabel(int pieceIndex, BlokusPiece bp, int size) {
            super(new ImageIcon(bp.render(size)));
            this.pieceIndex = pieceIndex;
        }
    }
}
