package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BlokusBoard {

    public static final int NONE = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int YELLOW = 3;
    public static final int GREEN = 4;
    public static final int BOARD_SIZE = 20;
    public static final int DEFAULT_RESOLUTION = 700;

    public static final Color BOARD_COLOR = Color.LIGHT_GRAY;
    public static final float ALPHA = 0.5f;

    private BufferedImage boardSprite;
    private BufferedImage redSprite;
    private BufferedImage blueSprite;
    private BufferedImage yellowSprite;
    private BufferedImage greenSprite;

    private BufferedImage redSpriteLabel;
    private BufferedImage blueSpriteLabel;
    private BufferedImage yellowSpriteLabel;
    private BufferedImage greenSpriteLabel;

    private static final String OFF_BOARD_ERROR = "Piece must be placed entirely on the board.";
    private static final String ADJACENCY_ERROR = "Pieces of the same color cannot share edges with one another.";
    private static final String OVERLAP_ERROR = "Pieces cannot overlap.";
    private static final String START_ERROR = "Starting peice must occupy the player's respective corner.";
    private static final String CORNER_ERROR = "Pieces must be connected to at least one other piece of the the same color by the corner.";

    private int[][] grid;
    private int[][] overlay;

    BlokusBoard() {
        grid = new int[BOARD_SIZE][BOARD_SIZE];
        overlay = new int[BOARD_SIZE][BOARD_SIZE];
        initSprites();
    }

    @SuppressWarnings("ConstantConditions")
    public BufferedImage draw(boolean isValidPlacement) {
        int size = DEFAULT_RESOLUTION;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        int cellSize = size / (BOARD_SIZE);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        Composite originalComposite = g2d.getComposite();

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                // Draw a sprite
                drawSprite(grid, x, y, cellSize, g2d);

                // if there is a piece on the overlay
                if (overlay[x][y] != NONE) {
                    if (!isValidPlacement)
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ALPHA));
                    drawSprite(overlay, x, y, cellSize, g2d);
                    g2d.setComposite(originalComposite);
                }

                // if it's empty and a corner, draw a dot
                boolean corner = false;
                Point p = new Point(x, y);

                if (grid[p.x][p.y] != NONE || overlay[p.x][p.y] != NONE)
                    continue;

                if (getCorner(BLUE).equals(p)) {
                    g2d.setColor(getColor(BLUE));
                    corner = true;
                } else if (getCorner(RED).equals(p)) {
                    g2d.setColor(getColor(RED));
                    corner = true;
                } else if (getCorner(GREEN).equals(p)) {
                    g2d.setColor(getColor(GREEN));
                    corner = true;
                } else if (getCorner(YELLOW).equals(p)) {
                    g2d.setColor(getColor(YELLOW));
                    corner = true;
                }
                if (corner) {
                    g2d.fillOval(x * cellSize + cellSize / 2 - cellSize / 6,
                            y * cellSize + cellSize / 2 - cellSize / 6,
                            cellSize / 3, cellSize / 3);
                }
            }
        }
        return image;
    }

    public void overlay(BlokusPiece bp, int xOff, int yOff) {
        clear(overlay);

        for (int x = 0; x < BlokusPiece.SHAPE_SIZE; x++)
            for (int y = 0; y < BlokusPiece.SHAPE_SIZE; y++)
                if (isInBounds(x + xOff - BlokusPiece.SHAPE_SIZE / 2,
                        y + yOff - BlokusPiece.SHAPE_SIZE / 2) && bp.getValue(x, y) == BlokusPiece.PIECE)
                    overlay[x + xOff - BlokusPiece.SHAPE_SIZE / 2][y + yOff - BlokusPiece.SHAPE_SIZE / 2] = bp.getColor();
    }

    void clear(int[][] array) {
        for (int x = 0; x < BOARD_SIZE; x++)
            for (int y = 0; y < BOARD_SIZE; y++)
                array[x][y] = NONE;
    }

    private void initSprites() {
        try {
            boardSprite = resize(ImageIO.read(getClass().getResource("/boardSprite.jpg")), true);
            redSprite = resize(ImageIO.read(getClass().getResource("/redSprite.jpg")), true);
            blueSprite = resize(ImageIO.read(getClass().getResource("/blueSprite.jpg")), true);
            yellowSprite = resize(ImageIO.read(getClass().getResource("/yellowSprite.jpg")), true);
            greenSprite = resize(ImageIO.read(getClass().getResource("/greenSprite.jpg")), true);

            redSpriteLabel = resize(redSprite, false);
            blueSpriteLabel = resize(blueSprite, false);
            yellowSpriteLabel = resize(yellowSprite, false);
            greenSpriteLabel = resize(greenSprite, false);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage resize(BufferedImage img, boolean fullSize) {
        int size;
        if (fullSize)
            size = DEFAULT_RESOLUTION / BOARD_SIZE;
        else
            size = BlokusPiece.DEFAULT_RESOLUTION / BlokusPiece.SHAPE_SIZE;
        Image tmp = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private void drawSprite(int[][] grid, int x, int y, int cellSize, Graphics2D g2d) {
        BufferedImage sprite = boardSprite;
        switch (grid[x][y]) {
            case BLUE:
                sprite = blueSprite;
                break;
            case RED:
                sprite = redSprite;
                break;
            case YELLOW:
                sprite = yellowSprite;
                break;
            case GREEN:
                sprite = greenSprite;
                break;
            case NONE:
                sprite = boardSprite;
                break;
        }
        g2d.drawImage(sprite, x * cellSize + 1, y * cellSize + 1, null);
    }

    private static Color getColor(int color) {
        switch (color) {
            case BLUE:
                return Color.BLUE;
            case GREEN:
                return Color.GREEN;
            case RED:
                return Color.RED;
            case YELLOW:
                return Color.YELLOW;
            default:
                return BOARD_COLOR;
        }
    }

    public static String getColorById(int id) {
        String string = "";
        switch (id) {
            case 1:
                string = "blue";
                break;
            case 2:
                string = "red";
                break;
            case 3:
                string = "yellow";
                break;
            case 4:
                string = "green";
                break;
        }
        return string;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public void placePiece(BlokusPiece bp, int xOffset, int yOffset, boolean firstMove) throws IllegalMoveException {
        isValidMove(bp, xOffset, yOffset, firstMove);
        for (int x = 0; x < BlokusPiece.SHAPE_SIZE; x++)
            for (int y = 0; y < BlokusPiece.SHAPE_SIZE; y++)
                if (bp.getValue(x, y) == BlokusPiece.PIECE)
                    this.grid[x + xOffset][y + yOffset] = bp.getColor();
    }

    private boolean isInBounds(int x, int y) {
        return (x >= 0 && y >= 0 && x < BOARD_SIZE && y < BOARD_SIZE);
    }

    private Point getCorner(int color) {
        switch (color) {
            case BLUE:
                return new Point(0, 0);
            case GREEN:
                return new Point(0, BOARD_SIZE - 1);
            case RED:
                return new Point(BOARD_SIZE - 1, 0);
            case YELLOW:
                return new Point(BOARD_SIZE - 1, BOARD_SIZE - 1);
            default:
        }
        return null;
    }

    void isValidMove(BlokusPiece bp, int xOffset, int yOffset, boolean firstMove) throws IllegalMoveException {
        boolean corner = false;
        for (int x = 0; x < BlokusPiece.SHAPE_SIZE; x++) {
            for (int y = 0; y < BlokusPiece.SHAPE_SIZE; y++) {
                int value = bp.getValue(x, y);
                boolean inBounds = isInBounds(x + xOffset, y + yOffset);

                if (inBounds) {
                    int gridValue = grid[x + xOffset][y + yOffset];

                    if (gridValue != NONE) {
                        if (value == BlokusPiece.PIECE)
                            throw new IllegalMoveException(OVERLAP_ERROR);
                        if (gridValue == bp.getColor()) {
                            if (value == BlokusPiece.ADJACENT)
                                throw new IllegalMoveException(ADJACENCY_ERROR);
                            if (value == BlokusPiece.CORNER)
                                corner = true;
                        }
                    } else if (firstMove && value == BlokusPiece.PIECE && new Point(x + xOffset, y + yOffset).equals(getCorner(bp.getColor()))) {
                        corner = true;
                    }
                } else {
                    if (value == BlokusPiece.PIECE)
                        throw new IllegalMoveException(OFF_BOARD_ERROR);
                }
            }
        }
        if (!corner)
            throw new IllegalMoveException(firstMove ? START_ERROR : CORNER_ERROR);
    }

    public int[][] getOverlay() {
        return overlay;
    }

    public BufferedImage getRedSprite() {
        return redSpriteLabel;
    }

    public BufferedImage getBlueSprite() {
        return blueSpriteLabel;
    }

    public BufferedImage getYellowSprite() {
        return yellowSpriteLabel;
    }

    public BufferedImage getGreenSprite() {
        return greenSpriteLabel;
    }

    class IllegalMoveException extends Exception {
        IllegalMoveException(String message) {
            super(message);
        }
    }
}
