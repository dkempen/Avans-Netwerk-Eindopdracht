package game;

import java.util.LinkedList;

public class BlokusPlayer {

    private LinkedList<BlokusPiece> pieces;
    private int score;

    BlokusPlayer(int id) {
        int[][][] shapes = BlokusPiece.getAllShapes();
        pieces = new LinkedList<>();

        for (int[][] shape : shapes)
            pieces.add(new BlokusPiece(shape, id));
    }

    public int getScore() {
        return score;
    }
}
