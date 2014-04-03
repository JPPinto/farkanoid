package farkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA.
 * User: luis
 * Date: 6/9/13
 * Time: 11:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class PanelOptions extends javax.swing.JPanel implements ActionListener, Common {
    /* Sprites + positions */
    private StaticSprite spriteOptions, spriteDifficulty, spriteMusic, spriteSFX, spriteKeys, spriteLeft, spriteRight, spriteFire, spriteLoadDefaults, spriteSave;
    private int spriteOptionsWidthStart, spriteKeysWidthStart, spriteLeftWidthStart, spriteRightWidthStart, spriteFireWidthStart,spriteLoadDefaultsWidthStart, spriteSaveWidthStart;

    private StaticSprite spriteCheater, spriteEasy, spriteNormal, spriteHard, spriteAsian;
    /* Sprite On + Off */
    private StaticSprite spriteOn, spriteOff, selectorLeft, selectorRight, selectorLeftRed, selectorRightRed;

    private int currentSelection;

    /* Timer */
    private final Timer timer;

    /* X positions for the options */
    private final int distX = 47, optionX0 = 20;
    private final int optionX1 = optionX0 + distX;
    private final int optionX2 = optionX0 + distX * 2;
    private final int optionX3 = optionX0 + distX * 3;
    private final int optionX4 = optionX0 + distX * 4;
    private final int optionX5 = optionX0 + distX * 5;
    private final int optionX6 = optionX0 + distX * 6;
    private final int optionX7 = optionX0 + distX * 7;
    private final int optionX8 = optionX0 + distX * 8;
    private final int optionX9 = optionX0 + distX * 9;
    private final int posXSecOption = 250;

    private boolean choosingKeyMode = false;

    private Options options;

    public PanelOptions(Options in) {
        options=in;

        loadSprites();

        setFocusable(true);
        FocusListener windowFocusListener = new FocusListener() {
            public void focusGained(FocusEvent e) {
            /* Restart the graphical interface timer */
                if (!timer.isRunning()) {
                    timer.restart();
                }
            }

            public void focusLost(FocusEvent e) {
            /* Stop the graphical interface timer (save cpu while out of focus) */
                timer.stop();
            }
        };
        addFocusListener(windowFocusListener);

        KeyAdapter keyAdapter = new MenuKeyAdapter();
        addKeyListener(keyAdapter);

        timer = new Timer(10, this);
        timer.setRepeats(true);
        timer.start();
    }

    private void loadSprites() {
        /* Logo */
        spriteOptions = new StaticSprite(Common.imageFolder + "m_options.png");
        spriteOptionsWidthStart = (Common.GAME_WIDTH - spriteOptions.getWidth()) / 2;

        /* The options */
        spriteDifficulty = new StaticSprite(Common.imageFolder + "mo_difficulty.png");
        spriteMusic = new StaticSprite(Common.imageFolder + "mo_music.png");
        spriteSFX = new StaticSprite(Common.imageFolder + "mo_sfx.png");
        spriteKeys = new StaticSprite(Common.imageFolder + "mo_keys.png");
        spriteKeysWidthStart = (Common.GAME_WIDTH - spriteKeys.getWidth()) / 2;
        spriteLeft = new StaticSprite(Common.imageFolder + "mo_left.png");
        spriteLeftWidthStart = (Common.GAME_WIDTH - spriteLeft.getWidth()) / 2;
        spriteRight = new StaticSprite(Common.imageFolder + "mo_right.png");
        spriteRightWidthStart = (Common.GAME_WIDTH - spriteRight.getWidth()) / 2;
        spriteFire = new StaticSprite(Common.imageFolder + "mo_fire.png");
        spriteFireWidthStart = (Common.GAME_WIDTH - spriteFire.getWidth()) / 2;
        spriteLoadDefaults = new StaticSprite(Common.imageFolder + "mo_load_defaults.png");
        spriteLoadDefaultsWidthStart = (Common.GAME_WIDTH - spriteLoadDefaults.getWidth()) / 2;
        spriteSave = new StaticSprite(Common.imageFolder + "mo_save.png");
        spriteSaveWidthStart = (Common.GAME_WIDTH - spriteSave.getWidth()) / 2;

        /* Difficulty */
        spriteCheater = new StaticSprite(Common.imageFolder + "mo_d_cheater.png");
        spriteEasy = new StaticSprite(Common.imageFolder + "mo_d_easy.png");
        spriteNormal = new StaticSprite(Common.imageFolder + "mo_d_normal.png");
        spriteHard = new StaticSprite(Common.imageFolder + "mo_d_hard.png");
        spriteAsian = new StaticSprite(Common.imageFolder + "mo_d_asian.png");

        spriteOn = new StaticSprite(Common.imageFolder + "mo_on.png");
        spriteOff = new StaticSprite(Common.imageFolder + "mo_off.png");

        /* Selection */
        selectorLeft = new StaticSprite(Common.imageFolder + "m_selectorLeft.png");
        selectorRight = new StaticSprite(Common.imageFolder + "m_selectorRight.png");

        selectorLeftRed = new StaticSprite(Common.imageFolder + "m_selectorLeftRed.png");
        selectorRightRed = new StaticSprite(Common.imageFolder + "m_selectorRightRed.png");
    }

    /* Paint magic */
    public void paint(Graphics graphic) {
        super.paint(graphic);

        Graphics2D graphics2D = (Graphics2D) graphic;
        /* Set the background to black */
        graphic.setColor(Color.black);
        graphic.fillRect(0, 0, getWidth(), getHeight());
        drawOptionTitle(graphics2D);
        drawOptions(graphics2D);
        /**/
        drawOptionsDifficulty(graphics2D);
        drawOptionsMusicSFX(graphics2D);

        drawSelectors(graphics2D);
        graphic.dispose();
    }

    private void drawOptionTitle(Graphics2D graphics2D) {
        graphics2D.drawImage(spriteOptions.getImage(), spriteOptionsWidthStart, optionX0, spriteOptions.getWidth(), spriteOptions.getHeight(), this);
    }

    private void drawOptions(Graphics2D graphics2D) {
        graphics2D.drawImage(spriteDifficulty.getImage(), 25, optionX1, spriteDifficulty.getWidth(), spriteDifficulty.getHeight(), this);
        graphics2D.drawImage(spriteMusic.getImage(), 25, optionX2, spriteMusic.getWidth(), spriteMusic.getHeight(), this);
        graphics2D.drawImage(spriteSFX.getImage(), 25, optionX3, spriteSFX.getWidth(), spriteSFX.getHeight(), this);
        graphics2D.drawImage(spriteKeys.getImage(), spriteKeysWidthStart, optionX4, spriteKeys.getWidth(), spriteKeys.getHeight(), this);
        graphics2D.drawImage(spriteLeft.getImage(), spriteLeftWidthStart, optionX5, spriteLeft.getWidth(), spriteLeft.getHeight(), this);
        graphics2D.drawImage(spriteRight.getImage(), spriteRightWidthStart, optionX6, spriteRight.getWidth(), spriteRight.getHeight(), this);
        graphics2D.drawImage(spriteFire.getImage(), spriteFireWidthStart, optionX7, spriteFire.getWidth(), spriteFire.getHeight(), this);
        graphics2D.drawImage(spriteLoadDefaults.getImage(), spriteLoadDefaultsWidthStart, optionX8, spriteLoadDefaults.getWidth(), spriteLoadDefaults.getHeight(), this);
        graphics2D.drawImage(spriteSave.getImage(), spriteSaveWidthStart, optionX9, spriteSave.getWidth(), spriteSave.getHeight(), this);
    }

    private void drawOptionsDifficulty(Graphics2D graphics2D) {
        switch (options.getDifficulty()) {
            case 0:
                graphics2D.drawImage(spriteCheater.getImage(), posXSecOption, optionX1, spriteCheater.getWidth(), spriteCheater.getHeight(), this);
                break;

            case 1:
                graphics2D.drawImage(spriteEasy.getImage(), posXSecOption, optionX1, spriteEasy.getWidth(), spriteEasy.getHeight(), this);
                break;

            case 2:
                graphics2D.drawImage(spriteNormal.getImage(), posXSecOption, optionX1, spriteNormal.getWidth(), spriteNormal.getHeight(), this);
                break;

            case 3:
                graphics2D.drawImage(spriteHard.getImage(), posXSecOption, optionX1, spriteHard.getWidth(), spriteHard.getHeight(), this);
                break;

            case 4:
                graphics2D.drawImage(spriteAsian.getImage(), posXSecOption, optionX1, spriteAsian.getWidth(), spriteAsian.getHeight(), this);
                break;

            default:
                break;
        }
    }

    private void drawOptionsMusicSFX(Graphics2D graphics2D) {
        if (options.getMusicOn()) {
            graphics2D.drawImage(spriteOn.getImage(), posXSecOption, optionX2, spriteOn.getWidth(), spriteOn.getHeight(), this);
        } else {
            graphics2D.drawImage(spriteOff.getImage(), posXSecOption, optionX2, spriteOff.getWidth(), spriteOff.getHeight(), this);
        }

        if (options.getSfxOn()) {
            graphics2D.drawImage(spriteOn.getImage(), posXSecOption, optionX3, spriteOn.getWidth(), spriteOn.getHeight(), this);
        } else {
            graphics2D.drawImage(spriteOff.getImage(), posXSecOption, optionX3, spriteOff.getWidth(), spriteOff.getHeight(), this);
        }
    }

    private void drawSelectors(Graphics2D graphics2D) {
        switch (currentSelection) {
            case 0:
                graphics2D.drawImage(selectorLeft.getImage(), posXSecOption - 20, optionX1 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                graphics2D.drawImage(selectorRight.getImage(), (int) getCurrentDifficultySprite().getRect().getMaxX() + posXSecOption +  5, optionX1 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                break;

            case 1:
                graphics2D.drawImage(selectorLeft.getImage(), posXSecOption - 20, optionX2 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                graphics2D.drawImage(selectorRight.getImage(), (int) getCurrentMusicSprite().getRect().getMaxX() + posXSecOption +  5, optionX2 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                break;

            case 2:
                graphics2D.drawImage(selectorLeft.getImage(), posXSecOption - 20, optionX3 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                graphics2D.drawImage(selectorRight.getImage(), (int) getCurrentSFXSprite().getRect().getMaxX() + posXSecOption +  5, optionX3 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                break;

            case 3:
                if (choosingKeyMode) {
                    graphics2D.drawImage(selectorLeftRed.getImage(), spriteLeftWidthStart - 20, optionX5 + 5, selectorLeftRed.getWidth(), selectorLeftRed.getHeight(), this);
                    graphics2D.drawImage(selectorRightRed.getImage(), (int) spriteLeft.getRect().getMaxX() + spriteLeftWidthStart +  5, optionX5 + 5, selectorRightRed.getWidth(), selectorLeftRed.getHeight(), this);
                } else {
                    graphics2D.drawImage(selectorLeft.getImage(), spriteLeftWidthStart - 20, optionX5 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                    graphics2D.drawImage(selectorRight.getImage(), (int) spriteLeft.getRect().getMaxX() + spriteLeftWidthStart +  5, optionX5 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                }
                break;

            case 4:
                if (choosingKeyMode) {
                    graphics2D.drawImage(selectorLeftRed.getImage(), spriteRightWidthStart - 20, optionX6 + 5, selectorLeftRed.getWidth(), selectorLeftRed.getHeight(), this);
                    graphics2D.drawImage(selectorRightRed.getImage(), (int) spriteRight.getRect().getMaxX() + spriteRightWidthStart +  5, optionX6 + 5, selectorRightRed.getWidth(), selectorLeftRed.getHeight(), this);
                } else {
                    graphics2D.drawImage(selectorLeft.getImage(), spriteRightWidthStart - 20, optionX6 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                    graphics2D.drawImage(selectorRight.getImage(), (int) spriteRight.getRect().getMaxX() + spriteRightWidthStart +  5, optionX6 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                }
                break;

            case 5:
                if (choosingKeyMode) {
                    graphics2D.drawImage(selectorLeftRed.getImage(), spriteFireWidthStart - 20, optionX7 + 5, selectorLeftRed.getWidth(), selectorLeftRed.getHeight(), this);
                    graphics2D.drawImage(selectorRightRed.getImage(), (int) spriteFire.getRect().getMaxX() + spriteFireWidthStart +  5, optionX7 + 5, selectorRightRed.getWidth(), selectorLeftRed.getHeight(), this);
                } else {
                    graphics2D.drawImage(selectorLeft.getImage(), spriteFireWidthStart - 20, optionX7 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                    graphics2D.drawImage(selectorRight.getImage(), (int) spriteFire.getRect().getMaxX() + spriteFireWidthStart +  5, optionX7 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                }
                break;

            case 6:
                graphics2D.drawImage(selectorLeft.getImage(), spriteLoadDefaultsWidthStart - 20, optionX8 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                graphics2D.drawImage(selectorRight.getImage(), (int) spriteLoadDefaults.getRect().getMaxX() + spriteLoadDefaultsWidthStart +  5, optionX8 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                break;

            case 7:
                graphics2D.drawImage(selectorLeft.getImage(), spriteSaveWidthStart - 20, optionX9 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                graphics2D.drawImage(selectorRight.getImage(), (int) spriteSave.getRect().getMaxX() + spriteSaveWidthStart +  5, optionX9 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                break;

            default:
                break;
        }
    }

    private StaticSprite getCurrentDifficultySprite() {
        switch (options.getDifficulty()) {
            case 0:
                return spriteCheater;

            case 1:
                return spriteEasy;

            case 2:
                return spriteNormal;

            case 3:
                return spriteHard;

            case 4:
                return spriteAsian;

            default:
                return spriteCheater;
        }
    }

    private StaticSprite getCurrentMusicSprite() {
        if (options.getMusicOn()) {
            return spriteOn;
        } else {
            return spriteOff;
        }
    }

    private StaticSprite getCurrentSFXSprite() {
        if (options.getSfxOn()) {
            return spriteOn;
        } else {
            return spriteOff;
        }
    }

    /* Action Listener for panelMenu repaint */
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    /* Keyboard input */
    private class MenuKeyAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (!choosingKeyMode){
            /* Down */
                if (currentSelection < 7 && key == KeyEvent.VK_DOWN) {
                    currentSelection++;
                    return;
                }

            /* Up */
                if (currentSelection > 0 && key == KeyEvent.VK_UP) {
                    currentSelection--;
                    return;
                }

            /* Loop */
                if (currentSelection == 0 && key == KeyEvent.VK_UP) {
                    currentSelection = 7;
                    return;
                }

                if (currentSelection == 7 && key == KeyEvent.VK_DOWN) {
                    currentSelection = 0;
                    return;
                }

            /* Select */
                if (currentSelection >= 0 && currentSelection <= 7 && key == KeyEvent.VK_ENTER) {
                    setOptionsEnter();
                }

            /* Toggle stuff left + right */
                if (currentSelection >= 0 && currentSelection <= 3 && key == KeyEvent.VK_LEFT) {
                    setOptionsLeft();
                }

                if (currentSelection >= 0 && currentSelection <= 3 && key == KeyEvent.VK_RIGHT) {
                    setSpriteOptionsRight();
                }

            } else {
                // Change key
                setKey(currentSelection - 3, key);
                choosingKeyMode = false;
            }
        }
    }

    private void setKey(int keyNumber, int newKey) {
        if (newKey == KeyEvent.VK_C || newKey == KeyEvent.VK_S || newKey == KeyEvent.VK_W || newKey == KeyEvent.VK_Q || newKey == KeyEvent.VK_P || newKey == KeyEvent.VK_B || newKey == KeyEvent.VK_ESCAPE) {
            return;
        }
        switch (keyNumber) {
            case 0:
                // Left
                options.setLeftKey(newKey);
                break;

            case 1:
                // Right
                options.setRightKey(newKey);
                break;

            case 2:
                // Fire
                options.setFireKey(newKey);
                break;

            default:
                break;
        }

    }

    private void setOptionsEnter() {
        MainWindow topFrame = (MainWindow) SwingUtilities.getWindowAncestor(this);

        switch (currentSelection) {
            case 0:
                if (options.getDifficulty() == 4) {
                    options.setDifficulty(0);
                } else {
                    options.setDifficulty(options.getDifficulty() + 1);
                }
                break;

            case 1:
                // Toggle music
                options.setMusic(!options.getMusicOn());
                break;

            case 2:
                // Toggle sfx
                options.setSfx(!options.getSfxOn());
                break;

            case 3:
                // Chose left key
                choosingKeyMode = true;
                break;

            case 4:
                // Chose right key
                choosingKeyMode = true;
                break;

            case 5:
                // Chose fire key
                choosingKeyMode = true;
                break;

            case 6:
                // Load defaults
                options.resetOptions();
                break;

            case 7:
                // save and exit
                topFrame.setOptions(options);
                topFrame.removeOptions();
                topFrame.openMenu();
                break;

            default:
                break;
        }

    }

    private void setOptionsLeft() {
        switch (currentSelection) {
            case 0:
                if (options.getDifficulty() == 0) {
                    options.setDifficulty(4);
                } else {
                    options.setDifficulty(options.getDifficulty() - 1);
                }
                break;

            case 1:
                // Toggle music
                options.setMusic(!options.getMusicOn());
                break;

            case 2:
                // Toggle sfx
                options.setSfx(!options.getSfxOn());
                break;
            default:
                break;
        }
    }

    private void setSpriteOptionsRight() {
        switch (currentSelection) {
            case 0:
                if (options.getDifficulty() == 4) {
                    options.setDifficulty(0);
                } else {
                    options.setDifficulty(options.getDifficulty() + 1);
                }
                break;

            case 1:
                // Toggle music
                options.setMusic(!options.getMusicOn());
                break;

            case 2:
                // Toggle sfx
                options.setSfx(!options.getSfxOn());
                break;
            default:
                break;
        }
    }
}
