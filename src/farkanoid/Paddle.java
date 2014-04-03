package farkanoid;

/**
 * @author ei12130 - Eduardo Fernandes
 * @author ei12164 - José Pinto
 *
 * Paddle class
 */

import javax.swing.*;
import java.awt.*;

public class Paddle extends Sprite implements Common {

    private int speed;
    private boolean hasLaser;
    private boolean bigPaddle;
    private int dx;
    private Laser laser;
    private Image bitmapLasers;
    private Image bitmapBigPaddle;

    public Paddle() {
        this.hasLaser = false;
        this.laser = null;


        String paddleImageFile = spriteFolder + "paddle.png";
        String paddleShadowImageFile = spriteFolder + "paddleShadow.png";
        String paddleLasersImageFile = spriteFolder + "paddleLasers.png";
        String paddleBigPaddleImageFile = spriteFolder + "paddleBig.png";

        ImageIcon ii = loadImageIcon(paddleImageFile);
        ImageIcon iShadow = loadImageIcon(paddleShadowImageFile);
        ImageIcon iLasers = loadImageIcon(paddleLasersImageFile);
        ImageIcon iBigPaddle = loadImageIcon(paddleBigPaddleImageFile);

        bitmap = ii.getImage();
        bitmapShadow = iShadow.getImage();
        bitmapLasers = iLasers.getImage();
        bitmapBigPaddle = iBigPaddle.getImage();

        /* Default paddle speed */
        speed = 3;

        width = bitmap.getWidth(null);
        height = bitmap.getHeight(null);

        resetState();
    }

    public int move() {

        x += dx;

        if (hasLaser) {
            laser.setLeftLaserPosX(laser.getLeftLaserPosX() + dx);
            laser.setRightLaserPosX(laser.getRightLaserPosX() + dx);
        }

        if (x <= BORDER_SIZE) {
            x = BORDER_SIZE;
            return 0;
        }

        if (x >= (GAME_WIDTH - BORDER_SIZE - width)) {
            x = (GAME_WIDTH - BORDER_SIZE - width);
            return 0;
        }

        return dx;
    }

    public int getDirection(){
        return dx;
    }

    public void setDirection(boolean in) {
        if (in) {
            setMovementRight();
        } else {
            setMovementLeft();
        }

    }

    public void setMovementLeft() {
        dx = -speed;
    }

    public void setMovementRight() {
        dx = speed;
    }

    public void setHaltMovement() {
        dx = 0;
    }

    public void resetState() {
        x = GAME_WIDTH / 2;
        y = TOP_HEIGHT + GAME_HEIGHT - BOTTOM_HEIGHT;
        hasLaser = false;
    }

    public int getPosX() {
        return x;
    }

    public int getPosY() {
        return y;
    }

    public Laser getLaser() {
        return laser;
    }

    public void setLaser(Laser laser) {
        this.laser = laser;
    }

    public boolean isHasLaser() {
        return hasLaser;
    }

    public void setHasLaser(boolean in) {
        hasLaser = in;
        if (hasLaser) {
            laser = new Laser(this);
        } else {
            laser = null;
        }

    }

    public boolean isBigPaddle() {
        return bigPaddle;
    }

    public void setBigPaddle(boolean bigPaddle) {
        this.bigPaddle = bigPaddle;
    }

    Image getImage() {

        if (hasLaser) {

            width = bitmap.getWidth(null);
            height = bitmap.getHeight(null);

            return bitmapLasers;

        } else {

            if (bigPaddle) {

                width = bitmapBigPaddle.getWidth(null);
                height = bitmapBigPaddle.getHeight(null);

                return bitmapBigPaddle;

            } else {

                width = bitmap.getWidth(null);
                height = bitmap.getHeight(null);

                return bitmap;
            }
        }
    }
}