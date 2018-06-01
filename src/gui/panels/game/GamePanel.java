package gui.panels.game;

import game.Blokus;
import game.BlokusPiece;
import gui.Frame;
import gui.PanelType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements gui.Panel {

    private Blokus blokus;
    private JPanel piecesPanel;
    private GameRenderPanel gameRenderPanel;
    private GameInfoPanel infoPanel;
    private JPanel sidePanel;
    private int pieceIndex;
    private ArrayList<BlokusPieceLabel> pieces;

    public GamePanel() {
        setLayout(new FlowLayout());
        JPanel panel = new JPanel();
        add(panel);
    }

    public void init(Blokus blokus) {
        this.blokus = blokus;
        setLayout(new BorderLayout());
        
        gameRenderPanel = new GameRenderPanel(this);
        infoPanel = new GameInfoPanel();
        JScrollPane jScrollPane = initScrollPane();

        add(jScrollPane, BorderLayout.WEST);
        add(gameRenderPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }

    private JScrollPane initScrollPane() {
        piecesPanel = new JPanel();
        piecesPanel.setLayout(new BoxLayout(piecesPanel, BoxLayout.Y_AXIS));
        JScrollPane jScrollPane = new JScrollPane(piecesPanel);
        jScrollPane.setPreferredSize(new Dimension(BlokusPiece.DEFAULT_RESOLUTION, BlokusPiece.DEFAULT_RESOLUTION));
        jScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));

        piecesPanel.removeAll();

        for (int i = 0; i < blokus.getPlayer().getPieces().size(); i++) {

            BlokusPieceLabel pieceLabel =
                    new BlokusPieceLabel(i, blokus.getPlayer().getPieces().get(i), BlokusPiece.DEFAULT_RESOLUTION);
            pieceLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    BlokusPieceLabel bp = (BlokusPieceLabel) e.getComponent();
                    clearBorder();
                    pieceIndex = bp.pieceIndex;
                    blokus.setSelectedPieceIndex(pieceIndex);
                    drawBorder();


                }
            });
            pieceLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            piecesPanel.add(pieceLabel);
        }

        pieceIndex = 0;
        drawBorder();
        piecesPanel.repaint();

        return jScrollPane;
    }


    private void drawBorder() {
        JComponent piece = (JComponent) piecesPanel.getComponent(blokus.getSelectedPieceIndex());
        System.out.println(blokus.getSelectedPieceIndex());
        piece.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
    }

    private void clearBorder()
    {
        JComponent piece = (JComponent) piecesPanel.getComponent(blokus.getSelectedPieceIndex());
        System.out.println(blokus.getSelectedPieceIndex());
        piece.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    public JPanel getPiecesPanel() {
        return piecesPanel;
    }

    public int getPieceIndex(){
        return pieceIndex;
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
            Frame.getInstance().setPanel(PanelType.MENU_PANEL);
    }

    @Override
    public void handleMouseClick(MouseEvent mouseEvent) {
        blokus.handleMouseClick(mouseEvent);
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent mouseWheelEvent) {
        blokus.handleMouseWheel(mouseWheelEvent);
    }

    @Override
    public void handleMouseMove(MouseEvent mouseEvent) {
        blokus.handleMouseMove(mouseEvent);
    }

    public GameRenderPanel getGameRenderPanel() {
        return gameRenderPanel;
    }

    public GameInfoPanel getInfoPanel() {
        return infoPanel;
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
