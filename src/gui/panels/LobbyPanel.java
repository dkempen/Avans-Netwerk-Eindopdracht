package gui.panels;

import gui.Frame;
import gui.Panel;
import network.Client;
import network.Server;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

public class LobbyPanel extends JPanel implements Panel {

    private JTextArea jTextArea;
    private Rectangle2D startbutton;
    private boolean startButtonSelected;

    public LobbyPanel() {
        //setLayout(new BorderLayout());
        //jTextArea = new JTextArea();
        //add(new JScrollPane(jTextArea), BorderLayout.CENTER);

        setBackground(Frame.BACKGROUND_COLOR);
    }

    @Override
    public void handleKeys(KeyEvent keyEvent) {

    }

    @Override
    public void handleMouseClick(MouseEvent mouseEvent) {
        Point relativePoint = Frame.getPoint(mouseEvent.getPoint(),this);
        if (startbutton.contains(relativePoint)){

        }

    }

    @Override
    public void handleMouseWheel(MouseWheelEvent mouseWheelEvent) {

    }

    @Override
    public void handleMouseMove(MouseEvent mouseEvent) {
        startButtonSelected= false;

        Point relative = Frame.getPoint(mouseEvent.getPoint(), this);
        if (startbutton.contains(relative))
            startButtonSelected = true;

        repaint();
    }

    public void initHost() {
        Server.getInstance().start(8000);
    }

    public void initClient() {
        Client.getInstance().start(8000);
    }

    public void setjTextArea(String message) {
        //jTextArea.append(message + "\n");
        //invalidate();
       //revalidate();
       //repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        Frame.getInstance().addText(g2d, "LobbyPanel", 50, Frame.WIDTH / 2 - 30, 100, true);
        Frame.getInstance().addText(g2d,"Number of players: ", 30, 800,750,true);
        startbutton = Frame.getInstance().addButton(g2d, "Start game", 50, Frame.HEIGHT/2 -40, 650, false, startButtonSelected);
    }

}
