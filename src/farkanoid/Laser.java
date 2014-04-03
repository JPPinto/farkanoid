package farkanoid;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 */
public class Laser extends Sprite implements Common {

    private static int speed = 3;
    private int leftLaserPosX; //Position of the left laser shot in the x axis
    private int rightLaserPosX; //Position of the right laser shot in the x axis
    private int numMAXShots; //Maximum number of shots allowed on the board
    private int yDir; //Velocity of the laser shot
    private Vector<Shot> shots = new Vector();
    private Paddle paddle;

    public Laser(Paddle paddle) {

        this.paddle = paddle;
        this.yDir = speed;

        String laserImageFile = spriteFolder + "laser.png";
        ImageIcon ii = loadImageIcon(laserImageFile);
        bitmap = ii.getImage();

        resetLaserPos();

        width = bitmap.getWidth(null);
        height = bitmap.getHeight(null);

        numMAXShots = 3;
    }

    public void move() {

        for (int i = 0; i < shots.size(); i++) {

            if (shots.get(i).isAvailableLeft()) {

                shots.get(i).setyLeft(shots.get(i).getyLeft() - yDir);

            /* Condition to stop the left laser shot's movement*/
                if (shots.get(i).getyLeft() <= TOP_HEIGHT + BORDER_SIZE) {
                    shots.get(i).setAvailableLeft(false);
                }
            }

            if (shots.get(i).isAvailableRight()) {

                shots.get(i).setyRight(shots.get(i).getyRight() - yDir);

            /* Condition to stop the right laser shot's movement*/
                if (shots.get(i).getyRight() <= TOP_HEIGHT + BORDER_SIZE) {
                    shots.get(i).setAvailableRight(false);
                }
            }
        }
    }

    public void resetLaserPos() {
        leftLaserPosX = paddle.getX() + (paddle.getWidth() / 4);
        rightLaserPosX = paddle.getX() + ((paddle.getWidth() / 4) * 3);
        setY(paddle.getY());
    }

    public boolean addShotToFire() {
        if (shots.size() < numMAXShots) {
            shots.add(new Shot(leftLaserPosX, getY(), rightLaserPosX, getY()));
            return true;
        }
        return false;
    }

    public int getLeftLaserPosX() {
        return leftLaserPosX;
    }

    public int setLeftLaserPosX(int leftLaserPosX) {

        if (leftLaserPosX >= GAME_WIDTH - BORDER_SIZE - ((paddle.getWidth() / 4) * 3)) {
            this.leftLaserPosX = GAME_WIDTH - BORDER_SIZE - ((paddle.getWidth() / 4) * 3);
            return 0;
        }

        if (leftLaserPosX <= BORDER_SIZE + (paddle.getWidth() / 4)) {
            this.leftLaserPosX = BORDER_SIZE + (paddle.getWidth() / 4);
            return 0;
        }

        this.leftLaserPosX = leftLaserPosX;
        return 0;

    }

    public int getRightLaserPosX() {
        return rightLaserPosX;
    }

    public int setRightLaserPosX(int rightLaserPosX) {

        if (rightLaserPosX >= GAME_WIDTH - BORDER_SIZE - ((paddle.getWidth() / 4))) {
            this.rightLaserPosX = GAME_WIDTH - BORDER_SIZE - ((paddle.getWidth() / 4));
            return 0;
        }

        if (rightLaserPosX <= BORDER_SIZE + ((paddle.getWidth() / 4) * 3)) {
            this.rightLaserPosX = BORDER_SIZE + ((paddle.getWidth() / 4) * 3);
            return 0;
        }

        this.rightLaserPosX = rightLaserPosX;
        return 0;
    }

    public int getyDir() {
        return yDir;
    }

    public void setyDir(int yDir) {
        this.yDir = yDir;
    }

    public Vector<Shot> getShots() {
        return shots;
    }

    public void setShots(Vector<Shot> shots) {
        this.shots = shots;
    }

    public void removeUsedShots() {

        for (int i = 0; i < shots.size(); i++) {

            if (!shots.get(i).isAvailableLeft() && !shots.get(i).isAvailableRight()) {
                shots.remove(i);
                i--;
            }
        }

    }

    public class Shot {

        private int xLeft;
        private int yLeft;
        private int xRight;
        private int yRight;
        private boolean availableLeft;
        private boolean availableRight;
        private Rectangle rectLeft;
        private Rectangle rectRight;

        public Shot(int xLeft, int yLeft, int xRight, int yRight) {

            this.xLeft = xLeft;
            this.yLeft = yLeft;
            this.xRight = xRight;
            this.yRight = yRight;
            this.availableLeft = true;
            this.availableRight = true;

            rectLeft = new Rectangle(xLeft, yLeft, getWidth(), getHeight());
            rectRight = new Rectangle(xRight, yRight, getWidth(), getHeight());
        }

        public int getxLeft() {
            return xLeft;
        }

        public void setxLeft(int xLeft) {
            this.xLeft = xLeft;
        }

        public int getyLeft() {
            return yLeft;
        }

        public void setyLeft(int yLeft) {
            this.yLeft = yLeft;
        }

        public int getxRight() {
            return xRight;
        }

        public void setxRight(int xRight) {
            this.xRight = xRight;
        }

        public int getyRight() {
            return yRight;
        }

        public void setyRight(int yRight) {
            this.yRight = yRight;
        }

        public boolean isAvailableLeft() {
            return availableLeft;
        }

        public void setAvailableLeft(boolean availableLeft) {
            this.availableLeft = availableLeft;
        }

        public boolean isAvailableRight() {
            return availableRight;
        }

        public void setAvailableRight(boolean availableRight) {
            this.availableRight = availableRight;
        }

        public Rectangle getRectLeft() {
            return rectLeft = new Rectangle(xLeft, yLeft, getWidth(), getHeight());
        }

        public void setRectLeft(Rectangle rectLeft) {
            this.rectLeft = rectLeft;
        }

        public Rectangle getRectRight() {
            return rectRight = new Rectangle(xRight, yRight, getWidth(), getHeight());
        }

        public void setRectRight(Rectangle rectRight) {
            this.rectRight = rectRight;
        }
    }
}
