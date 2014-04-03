package farkanoid;

/**
 * Created with IntelliJ IDEA.
 */

import java.io.Serializable;


public class LevelLayout implements Serializable {
    /* 14 lines, 12 columns */
    Brick[][] bricks = new Brick[14][12];

    int numberOfBricks;

    LevelLayout() {

        CountBricks();
    }

    private int CountBricks() {
        int brickCount = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 12; j++) {
                if (bricks[i][j] != null) {
                    brickCount++;
                }
            }
        }
        return brickCount;
    }

    public int getNumberOfBricks() {
        return numberOfBricks;
    }
}
