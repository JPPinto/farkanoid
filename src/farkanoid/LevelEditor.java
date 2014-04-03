package farkanoid;

/**
 * ei12130 - Eduardo Fernandes
 * ei12164 - José Pinto
 * Level Editor class.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class LevelEditor extends JPanel implements ActionListener, Common {
    Background background;
    LevelLayout layout;
    final Timer timer;

    /* Focus listener */
    FocusListener windowFocusListener = new FocusListener() {
        public void focusGained(FocusEvent e) {
            if (!timer.isRunning()) {
                timer.restart();
            }
        }

        public void focusLost(FocusEvent e) {
            /* Stop the graphical interface timer (save cpu while out of focus) */
            timer.stop();
        }
    };

    LevelEditor() {

        setFocusable(true);
        addFocusListener(windowFocusListener);

        timer = new Timer(10, this);
        timer.setRepeats(true);
        timer.start();

    }

    public void addNotify() {
        super.addNotify();
        levelEditorInit();
    }

    private void levelEditorInit() {
        layout = new LevelLayout();
    }

    /* Override paint */
    public void paint(Graphics graphic) {
        super.paint(graphic);
        drawBackground(graphic);
        Toolkit.getDefaultToolkit().sync();
        graphic.dispose();
    }

    private void drawBackground(Graphics graphic) {
        /* Draw game background */
        graphic.drawImage(background.getImage(), 0, Common.TOP_HEIGHT, background.getWidth(), background.getHeight(), this);
    }

    /* Action Listener for timer */
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
