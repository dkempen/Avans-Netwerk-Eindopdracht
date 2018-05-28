package game;

import java.util.LinkedList;

public class BlokusPlayer {

    private LinkedList<BlokusPiece> pieces;
    private int score;
    private boolean firstMove;
    private boolean canPlay;

    BlokusPlayer(int id) {
        firstMove = true;
        canPlay = true;

        int[][][] shapes = BlokusPiece.getAllShapes();
        pieces = new LinkedList<>();

        for (int[][] shape : shapes)
            pieces.add(new BlokusPiece(shape, id));
    }

    public int getScore() {
        return score;
    }

    public LinkedList<BlokusPiece> getPieces() {
        return pieces;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }
}
