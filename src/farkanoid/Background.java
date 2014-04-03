package farkanoid;

import javax.swing.*;

/**
 * @author ei12130 - Eduardo Fernandes
 * @author ei12164 - José Pinto
 *
 * Background class
 *
 * This class represents the image on the background on every level
 */
public class Background extends Sprite implements Common {

    protected Background(int index) {

        String backgroundImageFile; //Path to folder containing all the background images available
        /* Index defines the background image */
        switch (index) {
            // Frame *only*
            case 0:
                backgroundImageFile = imageFolder + "board.png";
                break;
            case 1:
                backgroundImageFile = imageFolder + "board01.png";
                break;
            case 2:
                backgroundImageFile = imageFolder + "board02.png";
                break;
            case 3:
                backgroundImageFile = imageFolder + "board03.png";
                break;
            case 4:
                backgroundImageFile = imageFolder + "board04.png";
                break;
            case 99:
                backgroundImageFile = imageFolder + "board99.png";
                break;
            default:
                backgroundImageFile = imageFolder + "board.png";
                break;
        }

        ImageIcon ii = loadImageIcon(backgroundImageFile);
        /*Initializing fields of the class extended Sprite*/
        bitmap = ii.getImage();
        width = bitmap.getWidth(null);
        height = bitmap.getHeight(null);
    }

}
