package game;

import gui.Frame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlokusPiece {
    public static final int SHAPE_SIZE = 7;
    public static final int PIECE = 3;
    public static final int ADJACENT = 2;
    public static final int CORNER = 1;

    public static final int DEFAULT_RESOLUTION = 120;

    private int[][] grid;
    private int color;

    // Constructor of BlokusPiece
    BlokusPiece(int[][] shape, int color) {
        // Check if the shape of the piece is 7 x 7
        if (shape.length != SHAPE_SIZE || shape[0].length != SHAPE_SIZE){
            throw new IllegalArgumentException("The array of the shape needs to be 7 x 7");
        }
        // used for object duplication
        grid = shape.clone();

        this.color = color;
    }

    public void rotateClockwise(){
        //making a grid 7 x 7
        int[][] temp = new int[SHAPE_SIZE][SHAPE_SIZE];

        // rotating grid clockwise
        for (int x = 0; x < SHAPE_SIZE; x++)
            for (int y = 0; y < SHAPE_SIZE; y++)
                temp[SHAPE_SIZE - y- 1][x] = grid[x][y];

        grid = temp;
    }

    public void rotateCounterClockwise(){
        //making a grid 7 x 7
        int[][] temp = new int [SHAPE_SIZE][SHAPE_SIZE];

        // rotating grid counterclockwise
        for (int x = 0; x < SHAPE_SIZE; x++)
            for (int y = 0; y < SHAPE_SIZE; y++)
                temp[y][SHAPE_SIZE - x - 1] = grid[x][y];

        grid = temp;
    }

    public void flipOver(){
        //making a grid 7 x 7
        int[][] temp = new int [SHAPE_SIZE][SHAPE_SIZE];

        // flipping the grid
        for (int x = 0; x < SHAPE_SIZE; x++)
            for (int y = 0; y < SHAPE_SIZE; y++)
                temp[SHAPE_SIZE - x - 1][y] = grid[x][y];

        grid = temp;
    }

    public int getValue(int x, int y){
        return grid[x][y];
    }

    public int getColor(){
        return color;
    }

    public int getPoints(){
        int points = 0;
        
        for(int y = 0; y < SHAPE_SIZE; y++)
            for (int x = 0; x < SHAPE_SIZE; x++)
                if (grid[x][y] == PIECE)
                    points++;

        return points;
    }

    public BufferedImage render(int size){
        BufferedImage image = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
        int cellSize = size / (SHAPE_SIZE);
        Graphics2D g2d = (Graphics2D) image.getGraphics();

        g2d.setColor(Frame.BACKGROUND_COLOR);
        g2d.fillRect(0,0,size,size);

        for (int x = 0; x < SHAPE_SIZE; x++){
            for (int y = 0; y < SHAPE_SIZE; y++){
                if(grid[x][y] == PIECE){
                    g2d.setColor(BlokusBoard.getColor(color));
                    g2d.fillRect(x * cellSize, y * cellSize,cellSize,cellSize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x * cellSize, y * cellSize,cellSize,cellSize);
                }
            }
        }
        return image;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < SHAPE_SIZE; y++){
            for (int x = 0; x < SHAPE_SIZE; x++){
                sb.append(grid[x][y]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static int[][][] getAllShapes(){
        int [][][] shapes = new int [21][SHAPE_SIZE][SHAPE_SIZE];
        int i = 0;

        shapes[i++] = new int[][] {     // * * * * *
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {1, 2, 2, 2, 2, 2, 1},
                {2, 3, 3, 3, 3, 3, 2},
                {1, 2, 2, 2, 2, 2, 1},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {     //   * * * *
                {0, 0, 0, 0, 0, 0, 0},  //   *
                {0, 1, 2, 1, 0, 0, 0},
                {0, 2, 3, 2, 2, 2, 1},
                {0, 2, 3, 3, 3, 3, 2},
                {0, 1, 2, 2, 2, 2, 1},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {      //   * * *
                {0, 0, 1, 2, 1, 0, 0},   // * *
                {0, 0, 2, 3, 2, 0, 0},
                {0, 0, 2, 3, 2, 1, 0},
                {0, 0, 2, 3, 3, 2, 0},
                {0, 0, 1, 2, 3, 2, 0},
                {0, 0, 0, 1, 2, 1, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {     //     *
                {0, 0, 0, 0, 0, 0, 0},  // * * * *
                {0, 0, 1, 2, 1, 0, 0},
                {0, 1, 2, 3, 2, 2, 1},
                {0, 2, 3, 3, 3, 3, 2},
                {0, 1, 2, 2, 2, 2, 1},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {     //     *
                {0, 0, 0, 0, 0, 0, 0},  // * * *
                {0, 0, 1, 2, 1, 0, 0},  //   *
                {0, 1, 2, 3, 2, 1, 0},
                {0, 2, 3, 3, 3, 2, 0},
                {0, 1, 2, 2, 3, 2, 0},
                {0, 0, 0, 1, 2, 1, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {     //   *
                {0, 0, 0, 0, 0, 0, 0},  // * * *
                {0, 0, 1, 2, 1, 0, 0},  //   *
                {0, 1, 2, 3, 2, 1, 0},
                {0, 2, 3, 3, 3, 2, 0},
                {0, 1, 2, 3, 2, 1, 0},
                {0, 0, 1, 2, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {      // * * *
                {0, 0, 0, 0, 0, 0, 0},   // *   *
                {0, 0, 0, 0, 0, 0, 0},
                {0, 1, 2, 2, 2, 1, 0},
                {0, 2, 3, 3, 3, 2, 0},
                {0, 2, 3, 2, 3, 2, 0},
                {0, 1, 2, 1, 2, 1, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {     //   * *
                {0, 0, 0, 0, 0, 0, 0},  // * * *
                {0, 0, 0, 0, 0, 0, 0},
                {0, 1, 2, 2, 2, 1, 0},
                {0, 2, 3, 3, 3, 2, 0},
                {0, 1, 2, 3, 3, 2, 0},
                {0, 0, 1, 2, 2, 1, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {     //     *
                {0, 0, 0, 0, 0, 0, 0},  //   * *
                {0, 0, 0, 1, 2, 1, 0},  // * *
                {0, 0, 1, 2, 3, 2, 0},
                {0, 1, 2, 3, 3, 2, 0},
                {0, 2, 3, 3, 2, 1, 0},
                {0, 1, 2, 2, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {      // *
                {0, 0, 0, 0, 0, 0, 0},   // * * *
                {0, 0, 1, 2, 1, 0, 0},   // *
                {0, 0, 2, 3, 2, 0, 0},
                {0, 1, 2, 3, 2, 1, 0},
                {0, 2, 3, 3, 3, 2, 0},
                {0, 1, 2, 2, 2, 1, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {      // *
                {0, 0, 1, 2, 1, 0, 0},   // *
                {0, 0, 2, 3, 2, 0, 0},   // * * *
                {0, 0, 2, 3, 2, 2, 1},
                {0, 0, 2, 3, 3, 3, 2},
                {0, 0, 1, 2, 2, 2, 1},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {      // *
                {0, 0, 0, 0, 0, 0, 0},   // * * *
                {0, 0, 1, 2, 2, 1, 0},   //     *
                {0, 0, 2, 3, 3, 2, 0},
                {0, 1, 2, 3, 2, 1, 0},
                {0, 2, 3, 3, 2, 0, 0},
                {0, 1, 2, 2, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };


        shapes[i++] = new int[][] {      // * * * *
                {0, 0, 1, 2, 1, 0, 0},   //
                {0, 0, 2, 3, 2, 0, 0},
                {0, 0, 2, 3, 2, 0, 0},
                {0, 0, 2, 3, 2, 0, 0},
                {0, 0, 2, 3, 2, 0, 0},
                {0, 0, 1, 2, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {      // * *
                {0, 0, 0, 0, 0, 0, 0},   //   * *
                {0, 0, 1, 2, 2, 1, 0},
                {0, 1, 2, 3, 3, 2, 0},
                {0, 2, 3, 3, 2, 1, 0},
                {0, 1, 2, 2, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {      // * *
                {0, 0, 0, 0, 0, 0, 0},   // * *
                {0, 1, 2, 2, 1, 0, 0},
                {0, 2, 3, 3, 2, 0, 0},
                {0, 2, 3, 3, 2, 0, 0},
                {0, 1, 2, 2, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {     //   *
                {0, 0, 0, 0, 0, 0, 0},  // * * *
                {0, 0, 1, 2, 1, 0, 0},
                {0, 1, 2, 3, 2, 1, 0},
                {0, 2, 3, 3, 3, 2, 0},
                {0, 1, 2, 2, 2, 1, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {     //     *
                {0, 0, 0, 0, 0, 0, 0},  // * * *
                {0, 0, 0, 0, 0, 0, 0},
                {0, 1, 2, 2, 2, 2, 0},
                {0, 2, 3, 3, 3, 2, 0},
                {0, 1, 2, 2, 3, 2, 0},
                {0, 0, 0, 1, 2, 1, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {      //
                {0, 0, 0, 0, 0, 0, 0},   // * * *
                {0, 0, 0, 0, 0, 0, 0},
                {0, 1, 2, 2, 2, 1, 0},
                {0, 2, 3, 3, 3, 2, 0},
                {0, 1, 2, 2, 2, 1, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {      // *
                {0, 0, 0, 0, 0, 0, 0},   // * *
                {0, 0, 1, 2, 1, 0, 0},
                {0, 0, 2, 3, 2, 1, 0},
                {0, 0, 2, 3, 3, 2, 0},
                {0, 0, 1, 2, 2, 1, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {      // * *
                {0, 0, 0, 0, 0, 0, 0},   //
                {0, 0, 1, 2, 1, 0, 0},
                {0, 0, 2, 3, 2, 0, 0},
                {0, 0, 2, 3, 2, 0, 0},
                {0, 0, 1, 2, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][] {      // *
                {0, 0, 0, 0, 0, 0, 0},   //
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 2, 1, 0, 0},
                {0, 0, 2, 3, 2, 0, 0},
                {0, 0, 1, 2, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };

        return shapes;
    }
}
