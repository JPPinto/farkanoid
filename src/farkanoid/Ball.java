package farkanoid;

/**
 * @author ei12130 - Eduardo Fernandes
 * @author ei12164 - José Pinto
 *
 * Ball class
 */

import javax.swing.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static java.lang.Math.abs;

public class Ball extends Sprite implements Common {

    private int xDirection;
    private int yDirection;
    private boolean slowBall;
    private boolean stickyBall;
    private boolean stuckOnPaddle;
    private boolean destructiveBall;
    private int powerUpCounter;

    public Ball() {
        /* Default direction */
        xDirection = 1;
        yDirection = -2;

        this.stickyBall = false;
        this.stuckOnPaddle = true;
        this.destructiveBall = false;

        String ballImageFile = spriteFolder + "ball.png";
        String ballShadowImageFile = spriteFolder + "paddleShadow.png";

        ImageIcon imageIconBall = loadImageIcon(ballImageFile);
        ImageIcon iShadow = loadImageIcon(ballShadowImageFile);

        bitmap = imageIconBall.getImage();
        bitmapShadow = iShadow.getImage();

        width = bitmap.getWidth(null);
        height = bitmap.getHeight(null);

        resetState();
    }

    public void toggleSlow() {
        slowBall = !slowBall;
    }

    public void move() {

        if (!isStuckOnPaddle()) {

            if (destructiveBall || slowBall) powerUpCounter += 10;

            /*Approximately 20 of cooldown on the Power Up*/
            if (powerUpCounter > 10000) {
                powerUpCounter = 0;
                destructiveBall = false;
                slowBall = false;
            }

            setX(getX() + xDirection);
            setY(getY() + yDirection);

            /* Ball limits */
            if (x <= BORDER_SIZE) {
                setXDir(abs(getXDir()));
                setX(BORDER_SIZE);
            }

            if (x >= GAME_WIDTH - BORDER_SIZE - width) {
                setXDir(-1 * getXDir());
                setX((GAME_WIDTH - BORDER_SIZE - width));
            }

            if (y <= TOP_HEIGHT + BORDER_SIZE) {
                setYDir(abs(getYDir()));
                setY(getY() + abs(yDirection));
            }
        }
    }

    public void resetALLPowerUps() {
        this.destructiveBall = false;
        this.stickyBall = false;
        this.stuckOnPaddle = false;
        this.slowBall = false;
    }

    private void resetState() {
        x = (GAME_WIDTH / 2) + 25;
        y = TOP_HEIGHT + GAME_HEIGHT - BOTTOM_HEIGHT - height;
    }

    public int getXDir() {
        return xDirection;
    }

    public void setXDir(int x) {
        xDirection = x;
    }

    public int getYDir() {
        return yDirection;
    }

    public void setYDir(int y) {
        yDirection = y;
    }

    public int getPosX() {
        return x;
    }

    public int getPosY() {
        return y;
    }

    public boolean isStickyBall() {
        return stickyBall;
    }

    public void setStickyBall(boolean stickyBall) {
        this.stickyBall = stickyBall;
    }

    public boolean isStuckOnPaddle() {
        return stuckOnPaddle;
    }

    public void setStuckOnPaddle(boolean stuckOnPaddle) {
        this.stuckOnPaddle = stuckOnPaddle;
    }

    public boolean isSlowBall() {
        return slowBall;
    }

    public void setSlowBall(boolean slowBall) {
        powerUpCounter = 0;
        this.slowBall = slowBall;
    }

    public boolean isDestructiveBall() {
        return !destructiveBall;
    }

    public void setDestructiveBall(boolean destructiveBall) {
        powerUpCounter = 0;
        this.destructiveBall = destructiveBall;
    }

}