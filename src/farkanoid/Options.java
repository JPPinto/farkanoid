package farkanoid;

import java.awt.event.KeyEvent;
import java.io.Serializable;

/**
 * @author ei12130 - Eduardo Fernandes
 * @author ei12164 - José Pinto
 *
 * Option class
 */
public class Options implements Serializable {

    private boolean musicOn;
    private boolean sfxOn;
    private int difficulty;
    private int leftVK, rightVK, fireVK, pauseVK;
    private long highScoreCheater, highScoreEasy, highScoreNormal, highScoreHard, highScoreAsian;

    /*
    * Difficulty
    * 0 Cheater
    * 1 Easy
    * 2 Normal
    * 3 Hard
    * 4 Asian
    * */

    /* Default options constructor */
    public Options() {
        musicOn = true;
        sfxOn = true;
        difficulty = 2;
        leftVK = KeyEvent.VK_LEFT;
        rightVK = KeyEvent.VK_RIGHT;
        fireVK = KeyEvent.VK_SPACE;
        pauseVK = KeyEvent.VK_P;
        highScoreCheater = 0;
    }

    public void resetOptions() {
        musicOn = true;
        sfxOn = true;
        difficulty = 2;
        leftVK = KeyEvent.VK_LEFT;
        rightVK = KeyEvent.VK_RIGHT;
        fireVK = KeyEvent.VK_SPACE;
    }

    public boolean getMusicOn() {
        return musicOn;
    }

    public void setMusic(boolean input) {
        musicOn = input;
    }

    public boolean getSfxOn() {
        return sfxOn;
    }

    public void setSfx(boolean input) {
        sfxOn = input;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int input) {
        if (input >= 0 && input <= 4)
            difficulty = input;
    }

    public int getLeftKey() {
        return leftVK;
    }

    public void setLeftKey(int input) {
        leftVK = input;
    }

    public int getRightKey() {
        return rightVK;
    }

    public void setRightKey(int input) {
        rightVK = input;
    }

    public int getFireKey() {
        return fireVK;
    }

    public void setFireKey(int input) {
        fireVK = input;
    }

    public int getPauseKey() {
        return pauseVK;
    }

    public long getHighScore() {
        switch (difficulty) {
            case 0:
                return highScoreCheater;
            case 1:
                return highScoreEasy;
            case 2:
                return highScoreNormal;
            case 3:
                return highScoreHard;
            case 4:
                return highScoreAsian;
        }
        return highScoreNormal;
    }

    public void setHighScore(long in) {
        switch (difficulty) {
            case 0:
                highScoreCheater = in;
            case 1:
                highScoreEasy = in;
            case 2:
                highScoreNormal = in;
            case 3:
                highScoreHard = in;
            case 4:
                highScoreAsian = in;
        }
    }
}
