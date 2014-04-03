package farkanoid;

/**
 * @author ei12130 - Eduardo Fernandes
 * @author ei12164 - José Pinto
 *         <p>
 *         Brick class
 *         </p>
 *      This class represents a Brick on the game board, which when destroyed, give the player's Score a certain amount of points.
 *      Also it can drop a <t>PowerUp</t> whether it was inicialized with one or not.
 */

import javax.swing.*;
import java.awt.*;

public class Brick extends Sprite implements Common {

    private int lives; //Number of hits necessary to destroy the Brick
    private boolean destroyed; //Turns true when the brick is destroyed
    private int type; //Type of brick since different Bricks have different number of lives
    private int value; //Value that will be added to the final score depending on the type of Brick
    private PowerUp power; //Each Brick can or not have a Power Up, which will drop on its destruction
    private Rectangle centerBlock; //Rectangle used to verify the collision with the ball in game deciding its direction
    private Rectangle sidesBlock; //Rectangle used to verify the collision with the ball in game deciding its direction


    public Brick(int x, int y, int type, int powerUpType) {
        this.x = x;
        this.y = y;
        this.centerBlock = new Rectangle();
        this.sidesBlock = new Rectangle();
        this.type = type;
        this.destroyed = false;
        /*Inicialize the brick's Power Up*/
        if (powerUpType != 0)
            this.power = new PowerUp(this, powerUpType);
        else
            this.power = null;

        ImageIcon ii;

        switch (type) {
            default:
            case 1:
                value = 50;
                lives = 0;
                String brickImageFile01 = spriteFolder + "brick01.png";
                ii = loadImageIcon(brickImageFile01);
                break;
            case 2:
                value = 60;
                lives = 0;
                String brickImageFile02 = spriteFolder + "brick02.png";
                ii = loadImageIcon(brickImageFile02);
                break;

            case 3:
                value = 70;
                lives = 0;
                String brickImageFile03 = spriteFolder + "brick03.png";
                ii = loadImageIcon(brickImageFile03);
                break;

            case 4:
                value = 80;
                lives = 0;
                String brickImageFile04 = spriteFolder + "brick04.png";
                ii = loadImageIcon(brickImageFile04);
                break;

            case 5:
                value = 90;
                lives = 0;
                String brickImageFile05 = spriteFolder + "brick05.png";
                ii = loadImageIcon(brickImageFile05);
                break;

            case 6:
                value = 100;
                lives = 0;
                String brickImageFile06 = spriteFolder + "brick06.png";
                ii = loadImageIcon(brickImageFile06);
                break;

            case 7:
                value = 110;
                lives = 0;
                String brickImageFile07 = spriteFolder + "brick07.png";
                ii = loadImageIcon(brickImageFile07);
                break;

            case 8:
                value = 120;
                lives = 0;
                String brickImageFile08 = spriteFolder + "brick08.png";
                ii = loadImageIcon(brickImageFile08);
                break;

            case 9:
                value = 500;
                lives = 3;
                String brickImageFile09 = spriteFolder + "brick09.png";
                ii = loadImageIcon(brickImageFile09);
                break;

            case 10:
                value = 1000;
                lives = 5;
                String brickImageFile10 = spriteFolder + "brick10.png";
                ii = loadImageIcon(brickImageFile10);
                break;
        }
        /*Inicializing fields of the class extended Sprite*/
        bitmap = ii.getImage();
        width = bitmap.getWidth(null);
        height = bitmap.getHeight(null);

        sidesBlock.setBounds((int) this.getRect().getMinX(),
                (int) this.getRect().getMinY() + 2,
                (int) this.getRect().getWidth(),
                (int) this.getRect().getHeight() - 4);
        centerBlock.setBounds((int) this.getRect().getMinX() + 2,
                (int) this.getRect().getMinY(),
                (int) this.getRect().getWidth() - 4,
                (int) this.getRect().getHeight());
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Rectangle getCenterBlock() {
        return centerBlock;
    }

    public Rectangle getSidesBlock() {
        return sidesBlock;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    /*Removes a life until none are left*/
    public void removeLive() {
        if (lives == 0) {
            this.destroyed = true;
        } else {
            lives--;
        }
    }

    public int getValue() {
        return value;
    }

    public PowerUp getPower() {
        return power;
    }

    public void setPower(PowerUp power) {
        this.power = power;
    }

}