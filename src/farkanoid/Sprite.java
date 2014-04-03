package farkanoid;

/**
 * ei12130 - Eduardo Fernandes
 * ei12164 - José Pinto
 *
 * Sprite Class
 *
 *  Contains all the necessary elements to define a sprite for any object which extends it.
 */

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Sprite implements Common {

    protected int x; //x : x value
    protected int y; //y : y value
    protected int width;//width : sprite width
    protected int height;//height : sprite height
    protected Image bitmap; //bitmap : sprite image
    protected Image bitmapShadow; //bitmap : sprite image's shadow

    /*
     * Sets x
     */
    public void setX(int x) {
        this.x = x;

        /* Ball limits */
    }

    /*
    * Returns the value of x
    * */
    public int getX() {
        return x;
    }

    public int getShadowX() {
        return x + 7;
    }

    /*
     * Sets y
     */
    public void setY(int y) {
        this.y = y;
    }

    /*
     * Returns the value of y
     */
    public int getY() {
        return y;
    }

    public int getShadowY() {
        return y + 7;
    }

    /*
     * Returns the width of the sprite
     */
    public int getWidth() {
        return width;
    }

    /*
     * Returns the height of the sprite
     */
    public int getHeight() {
        return height;
    }

    /*
     * Returns the image from the sprite
     */
    Image getImage() {
        return bitmap;
    }

    Image getShadow() {
        return bitmapShadow;
    }

    /*
     * Returns a rectangle with the sprite dimensions
     */
    Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public ImageIcon loadImageIcon(String input) {
        ImageIcon image = null;
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(input);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            int next = in.read();
            while (next > -1) {
                bos.write(next);
                next = in.read();
            }
            bos.flush();
            byte[] result = bos.toByteArray();
            image = new ImageIcon(result);
            return image;

        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return image;
    }
}
