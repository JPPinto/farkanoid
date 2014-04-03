package farkanoid;

import javax.swing.*;

/**
 * @author ei12130 - Eduardo Fernandes
 * @author ei12164 - José Pinto
 *         <p>
 *         PowerUp class
 *         </p>
 *         This class represents a feature in the game which is the possibility of power ups.
 *         Given the <t>type</t> of the power up and the <t>brick</t> in which it will be contained its position will be the same as its container
 *         and given the <t>brick</>'s destruction the power up will appear on the screen, descending until reaching the paddle or the end of the screen.
 */
public class PowerUp extends Sprite implements Common {

    /* Types of Power up available
    1 - C (catch):  	catches the energy ball and shoots it when you want to
    2 - E (expand):  	expands the Vaus, making it longer
    3 - D (disrupt):    allows ball to break Bricks easily without changing direction
    4 - L (laser):  	enables the Vaus to fire laser beams
    5 - B (break):  	allows player to move to next playfield, bonus is awarded
    6 - P (player):  	gains an additional Vaus
    7 - S (slow):       makes the ball slower
    */

    private int type; //The type of Power up
    private int xDir; //The directions on which the power up will move (x axis), which will only be downwards
    private int yDir; //The directions on which the power up will move (y axis), which will only be downwards
    private boolean available; //Determines either if the Power Up is available or not
    private Brick brick; //The Brick on which the Power Up will be contained
    private String imageFile; //Path to the folder that contains the several types of Power Ups

    public PowerUp(Brick brick, int in) {

        this.brick = brick;
        this.available = true;
        this.type = in;

        ImageIcon ii;

        switch (type) {
            case 1:
                imageFile = spriteFolder + "pCatch.gif";
                ii = loadImageIcon(imageFile);
                break;
            case 2:
                imageFile = spriteFolder + "pEnlarge.gif";
                ii = loadImageIcon(imageFile);
                break;
            case 3:
                imageFile = spriteFolder + "pDisruption.gif";
                ii = loadImageIcon(imageFile);
                break;
            case 4:
                imageFile = spriteFolder + "pLasers.gif";
                ii = loadImageIcon(imageFile);
                break;
            case 5:
                imageFile = spriteFolder + "pBreak.gif";
                ii = loadImageIcon(imageFile);
                break;
            case 6:
                imageFile = spriteFolder + "pPlayer.gif";
                ii = loadImageIcon(imageFile);
                break;
            case 7:
                imageFile = spriteFolder + "pSlow.gif";
                ii = loadImageIcon(imageFile);
                break;
            default:
                imageFile = spriteFolder + "pCatch.gif";
                ii = loadImageIcon(imageFile);
                break;
        }

        /* Initializing fields of the class extended Sprite */
        bitmap = ii.getImage();
        width = bitmap.getWidth(null);
        height = bitmap.getHeight(null);
        x = brick.getX();
        y = brick.getY();

        /*Set its direction downwards*/
        xDir = 0;
        yDir = 1;
    }

    /*Moves the Power Up*/
    public void move() {

        if (available) {

            setX(getX() + xDir);
            setY(getY() + yDir);

            /* Condition to stop the power up's  movement*/
            if (y > GAME_HEIGHT + getHeight()) available = false;
        }
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Brick getBrick() {
        return brick;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getxDir() {
        return xDir;
    }

    public void setxDir(int xDir) {
        this.xDir = xDir;
    }

    public int getyDir() {
        return yDir;
    }

    public void setyDir(int yDir) {
        this.yDir = yDir;
    }
}
