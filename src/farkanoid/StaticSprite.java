package farkanoid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ei12130 - Eduardo Fernandes
 * @author ei12164 - José Pinto
 *
 * StaticSprite class
 *
 * This class represents sprites applied in the game which don't necessarly interact with any element
 */

public class StaticSprite extends Sprite {

    public StaticSprite(String imageFile) {
        ImageIcon image = null;
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(imageFile);
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

        }
        catch (Exception e) {
            System.out.println(e.toString());
        }


        /*Initializing fields of the class extended Sprite*/
        bitmap = image.getImage();
        width = bitmap.getWidth(null);
        height = bitmap.getHeight(null);
    }
}
