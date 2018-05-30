package game;

import java.util.LinkedList;

public class BlokusPlayer {

    private LinkedList<BlokusPiece> pieces;
    private int score;
    private boolean firstMove;
    private boolean isFinished;

    BlokusPlayer(int id) {
        firstMove = true;
        isFinished = false;

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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished() {
        this.isFinished = true;
    }
}
