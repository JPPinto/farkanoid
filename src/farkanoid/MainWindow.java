package farkanoid;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MainWindow extends javax.swing.JFrame implements Common {
    private static String optionsFile = "options.dat";
    private PanelMenu panelMenu;
    private Board board;
    private PanelOptions panelOptions;
    private Options options;

    /* The main panelMenu window */
    public MainWindow() {
        if (checkSavedOptions()) {
            loadSavedOptions();
        } else {
            options = new Options();
            saveOptionsFile();
        }

        openMenu();
        setParameters();
    }

    public void openMenu() {
        panelMenu = new PanelMenu();
        getContentPane().add(panelMenu);
        revalidate();
        panelMenu.requestFocus();
    }

    public void removeMenu() {
        if (panelMenu != null) {
            getContentPane().remove(panelMenu);
        }
        panelMenu = null;
    }

    public void removeBoard() {
        if (board != null) {
            getContentPane().remove(board);
        }
        board = null;
    }

    public void newGame() {
        board = new Board(options);
        getContentPane().add(board);
        revalidate();
        board.requestFocus();
    }

    public void openOptions() {
        panelOptions = new PanelOptions(options);
        getContentPane().add(panelOptions);
        revalidate();
        panelOptions.requestFocus();
    }

    public void removeOptions() {
        if (panelOptions != null) {
            getContentPane().remove(panelOptions);
        }
        panelOptions = null;
        saveOptionsFile();
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options newOptions) {
        this.options = newOptions;
    }

    private boolean checkSavedOptions() {
        File f = new File(optionsFile);
        return f.exists();
    }

    private void saveOptionsFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(optionsFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(options);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    private void loadSavedOptions() {
        try {
            FileInputStream fileIn = new FileInputStream(optionsFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            options = (Options) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class Options not found!");
            c.printStackTrace();
        }
    }

    private void deleteSavedOptions() {
        File f = new File(optionsFile);
        // Make sure the file exists
        if (!f.exists())
            throw new IllegalArgumentException("Delete: no such file or directory: " + optionsFile);
        if (!f.canWrite())
            throw new IllegalArgumentException("Delete: write protected: " + optionsFile);
        // Attempt to delete it
        if (!f.delete())
            throw new IllegalArgumentException("Delete: deletion failed");
    }

    public void exitApp() {
        dispose();
        System.exit(0);
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

    public static void main(String[] args) {
        /* Copyright notices */
        System.out.println(copyright);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
    }
}
