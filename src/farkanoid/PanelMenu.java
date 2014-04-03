package farkanoid;

/**
 * ei12130 - Eduardo Fernandes
 * ei12164 - José Pinto
 * PanelMenu class.
 */

import ibxm.IBXMBackgroundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelMenu extends javax.swing.JPanel implements ActionListener, Common {
    /* Background music */
    private IBXMBackgroundPlayer ibxmBackgroundPlayer;

    /* Sprites + positions */
    private StaticSprite farkanoidLogo;
    private int logoWidthStart;

    private StaticSprite spriteNewGame, spriteNetworkGame, spriteLevelEditor, spriteOptions, spriteExit;
    private int newGameWidthStart, networkGameWidthStart, levelEditorWidthStart, optionsWidthStart, exitWidthStart;

    /* Selector */
    private StaticSprite selectorLeft, selectorRight;
    private int currentSelection;

    /* Keys */
    private KeyAdapter keyAdapter;

    /* Timer */
    private final Timer timer;

    /* X positions for the panelMenu options */
    private final int distX = 60, optionX0 = 170;
    private final int optionX1 = optionX0 + distX;
    private final int optionX2 = optionX0 + distX * 2;
    private final int optionX3 = optionX0 + distX * 3;
    private final int optionX4 = optionX0 + distX * 4;


    public PanelMenu() {
        setBackground(Color.BLACK);

        /* Background music */
        ibxmBackgroundPlayer = new IBXMBackgroundPlayer(musicFolder + "arkanoid.xm");

        /* Logo */
        farkanoidLogo = new StaticSprite(imageFolder + "m_logo.png");
        logoWidthStart = (Common.GAME_WIDTH - farkanoidLogo.getWidth()) / 2;

        /* Options */
        spriteNewGame = new StaticSprite(imageFolder + "m_newGame.png");
        spriteNetworkGame = new StaticSprite(imageFolder + "m_networkGame.png");
        spriteLevelEditor = new StaticSprite(imageFolder + "m_levelEditor.png");
        spriteOptions = new StaticSprite(imageFolder + "m_options.png");
        spriteExit = new StaticSprite(imageFolder + "m_exit.png");

        newGameWidthStart = (Common.GAME_WIDTH - spriteNewGame.getWidth()) / 2;
        networkGameWidthStart = (Common.GAME_WIDTH - spriteNetworkGame.getWidth()) / 2;
        levelEditorWidthStart = (Common.GAME_WIDTH - spriteLevelEditor.getWidth()) / 2;
        optionsWidthStart = (Common.GAME_WIDTH - spriteOptions.getWidth()) / 2;
        exitWidthStart = (Common.GAME_WIDTH - spriteExit.getWidth()) / 2;

        /* Selection */
        selectorLeft = new StaticSprite(imageFolder + "m_selectorLeft.png");
        selectorRight = new StaticSprite(imageFolder + "m_selectorRight.png");
        currentSelection = 0;

        setFocusable(true);
        addFocusListener(windowFocusListener);

        keyAdapter = new MenuKeyAdapter();
        addKeyListener(keyAdapter);

        timer = new Timer(10, this);
        timer.setRepeats(true);
        timer.start();
    }

    /* Paint magic */
    public void paint(Graphics graphic) {
        super.paint(graphic);

        Graphics2D graphics2D = (Graphics2D) graphic;
        /* Set the background to black */
        graphic.setColor(Color.black);
        graphic.fillRect(0, 0, getWidth(), getHeight());
        drawLogo(graphics2D);
        drawMenuOptions(graphics2D);
        drawSelectors(graphics2D);
        graphic.dispose();
    }

    /* Draw components */
    private void drawLogo(Graphics2D graphics2D) {
        graphics2D.drawImage(farkanoidLogo.getImage(), logoWidthStart, 40, farkanoidLogo.getWidth(), farkanoidLogo.getHeight(), this);
    }

    private void drawMenuOptions(Graphics2D graphics2D) {
        graphics2D.drawImage(spriteNewGame.getImage(), newGameWidthStart, optionX0, spriteNewGame.getWidth(), spriteNewGame.getHeight(), this);
        graphics2D.drawImage(spriteNetworkGame.getImage(), networkGameWidthStart, optionX1, spriteNetworkGame.getWidth(), spriteNetworkGame.getHeight(), this);
        graphics2D.drawImage(spriteLevelEditor.getImage(), levelEditorWidthStart, optionX2, spriteLevelEditor.getWidth(), spriteLevelEditor.getHeight(), this);
        graphics2D.drawImage(spriteOptions.getImage(), optionsWidthStart, optionX3, spriteOptions.getWidth(), spriteOptions.getHeight(), this);
        graphics2D.drawImage(spriteExit.getImage(), exitWidthStart, optionX4, spriteExit.getWidth(), spriteExit.getHeight(), this);
    }

    private void drawSelectors(Graphics2D graphics2D) {
        switch (currentSelection) {
            case 0:
                graphics2D.drawImage(selectorLeft.getImage(), newGameWidthStart - 20, optionX0 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                graphics2D.drawImage(selectorRight.getImage(), newGameWidthStart + spriteNewGame.getWidth() + 5, optionX0 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                break;

            case 1:
                graphics2D.drawImage(selectorLeft.getImage(), networkGameWidthStart - 20, optionX1 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                graphics2D.drawImage(selectorRight.getImage(), networkGameWidthStart + spriteNetworkGame.getWidth() + 5, optionX1 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                break;

            case 2:
                graphics2D.drawImage(selectorLeft.getImage(), levelEditorWidthStart - 20, optionX2 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                graphics2D.drawImage(selectorRight.getImage(), levelEditorWidthStart + spriteLevelEditor.getWidth() + 5, optionX2 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                break;

            case 3:
                graphics2D.drawImage(selectorLeft.getImage(), optionsWidthStart - 20, optionX3 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                graphics2D.drawImage(selectorRight.getImage(), optionsWidthStart + spriteOptions.getWidth() + 5, optionX3 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                break;

            case 4:
                graphics2D.drawImage(selectorLeft.getImage(), exitWidthStart - 20, optionX4 + 5, selectorLeft.getWidth(), selectorLeft.getHeight(), this);
                graphics2D.drawImage(selectorRight.getImage(), exitWidthStart + spriteExit.getWidth() + 5, optionX4 + 5, selectorRight.getWidth(), selectorLeft.getHeight(), this);
                break;

            default:
                break;
        }
    }

    /* Focus listener */
    private FocusListener windowFocusListener = new FocusListener() {
        public void focusGained(FocusEvent e) {
            /* Restart the graphical interface timer */
            if (!timer.isRunning()) {
                timer.restart();
            }

            /* Play the music or un pause it if it as already begun */
            if (ibxmBackgroundPlayer.getStatus()) {
                ibxmBackgroundPlayer.playSong();
            }

        }

        public void focusLost(FocusEvent e) {
            /* Stop the graphical interface timer (save cpu while out of focus) */
            timer.stop();

            /* Pause music */
            if (ibxmBackgroundPlayer.getStatus()) {
                ibxmBackgroundPlayer.pauseSong();
            }
        }
    };

    /* Action Listener for panelMenu repaint */
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    /* Keyboard input */
    private class MenuKeyAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            /* Down */
            if (currentSelection < 4 && key == KeyEvent.VK_DOWN) {
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
                currentSelection = 4;
                return;
            }

            if (currentSelection == 4 && key == KeyEvent.VK_DOWN) {
                currentSelection = 0;
                return;
            }

            /* Select */
            if (currentSelection >= 0 && currentSelection <= 4 && key == KeyEvent.VK_ENTER) {
                SelectOption();
            }
        }
    }

    private void SelectOption() {
        MainWindow topFrame = (MainWindow) SwingUtilities.getWindowAncestor(this);
        switch (currentSelection) {
            case 0:
                topFrame.removeMenu();
                topFrame.newGame();
                break;

            case 1:

                break;

            case 2:

                break;

            case 3:
                topFrame.removeMenu();
                topFrame.openOptions();
                break;

            case 4:
                topFrame.exitApp();
                break;

            default:
                break;
        }

    }
}

