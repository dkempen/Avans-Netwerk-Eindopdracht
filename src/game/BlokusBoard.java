package game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlokusBoard {

    public static final int NONE = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int YELLOW = 3;
    public static final int GREEN = 4;
    public static final int BOARD_SIZE = 20;
    public static final int DEFAULT_RESOLUTION = 700;

    public static final Color BOARD_COLOR = Color.LIGHT_GRAY;
    public static final Color GRID_LINE_COLOR = Color.GRAY;

    private BufferedImage redSprite;
    private BufferedImage blueSprite;
    private BufferedImage yellowSprite;
    private BufferedImage greenSprite;

    private int[][] grid;
    private int[][] overlay;

    BlokusBoard() {
        grid = new int[BOARD_SIZE][BOARD_SIZE];
        overlay = new int[BOARD_SIZE][BOARD_SIZE];
        initSprites();
    }

    public void draw(Graphics2D g2d) {
        int cellSize = DEFAULT_RESOLUTION / BOARD_SIZE;
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (!drawSprite(x, y, cellSize, g2d)) { // Draw a sprite if possible, else draw background
                    g2d.setColor(BOARD_COLOR);
                    g2d.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    g2d.setColor(GRID_LINE_COLOR);
                    g2d.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    private void initSprites() {
        redSprite = createBufferedImage(Color.RED);
        blueSprite = createBufferedImage(Color.BLUE);
        yellowSprite = createBufferedImage(Color.YELLOW);
        greenSprite = createBufferedImage(Color.GREEN);
    }

    private BufferedImage createBufferedImage(Color color) {
        int cellSize = DEFAULT_RESOLUTION / BOARD_SIZE;
        BufferedImage b_img = new BufferedImage(cellSize, cellSize, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = b_img.createGraphics();
        graphics.setPaint(color);
        graphics.fillRect(0, 0, b_img.getWidth(), b_img.getHeight());
        return b_img;
    }

    private boolean drawSprite(int x, int y, int cellSize, Graphics2D g2d) {
        switch (grid[x][y]) {
            case BLUE:
                g2d.drawImage(blueSprite, x * cellSize, y * cellSize, null);
                break;
            case RED:
                g2d.drawImage(redSprite, x * cellSize, y * cellSize, null);
                break;
            case YELLOW:
                g2d.drawImage(yellowSprite, x * cellSize, y * cellSize, null);
                break;
            case GREEN:
                g2d.drawImage(greenSprite, x * cellSize, y * cellSize, null);
                break;
            default:
                return false;
        }
        return true;
    }

    public static Color getColor(int color){

        switch(color){
            case BLUE: return Color.BLUE;
            case GREEN: return Color.GREEN;
            case RED: return Color.RED;
            case YELLOW: return Color.YELLOW;
            default: return BOARD_COLOR;
        }
    }

    public int[][] getGrid() {
        grid[1][1] = BLUE;
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}
