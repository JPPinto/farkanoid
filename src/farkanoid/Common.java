package farkanoid;

/**
 * @authore i12130 - Eduardo Fernandes
 * @authore i12164 - José Pinto
 *
 * Interface Common stuff
 *
 * A few elements necessary between the interations of elements in the game
 */

public interface Common {
    /* Size of the top part (score + other stuff) */
    public static final int TOP_HEIGHT = 40;
    public static final int GAME_WIDTH = 452;
    public static final int GAME_HEIGHT = 482;
    /* Bottom limit for ball */
    public static final int BOTTOM_HEIGHT = 70;
    public static final int BOTTOM = GAME_HEIGHT + TOP_HEIGHT;
    /* Limits */
    public static final int BORDER_SIZE = 20;
    public static final int MAX_LIVES = 14;
    /* Enemy scores */
    public static final int SCORE_BOSS_1 = 1000;
    public static final int SCORE_BOSS_FINAL = 150000;
    /* Folders */
    public static final String imageFolder = "resources/images/";
    public static final String spriteFolder = "resources/sprites/";
    public static final String soundFolder = "resources/sounds/";
    public static final String musicFolder = "resources/music/";
    /* Copyrights */
    public static final String copyrightLine0 = "Using IBXM Version a67 (c)2013 mumart@gmail.com.\n";
    public static final String copyrightLine1 = "Using Llageran's Arkanoid tune composition.\n";
    public static final String copyrightLine2 = "Using Elwood's Sweet dreams music.\n";
    public static final String copyrightLine3 = "Using Arkanoid sprites, (c)Taito Corporation Japan.\n";
    public static final String copyright = copyrightLine0 + copyrightLine1 + copyrightLine2 + copyrightLine3;
}



