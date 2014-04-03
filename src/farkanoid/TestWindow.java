package farkanoid;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: luis
 * Date: 6/11/13
 * Time: 2:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestWindow extends javax.swing.JFrame {
    Options options;
    public Board board;


    public TestWindow(Options optionsIn) {
        options=optionsIn;
        setParameters();
        newGame();
    }

    public void newGame() {
        board = new Board(options);
        getContentPane().add(board);
        revalidate();
        board.requestFocus();
    }

    private void setParameters() {
        setTitle("Farkanoid");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(Common.GAME_WIDTH, (Common.GAME_HEIGHT + Common.TOP_HEIGHT));
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setIgnoreRepaint(true);
        setResizable(false);
        setVisible(true);
        createBufferStrategy(2);
    }
}
