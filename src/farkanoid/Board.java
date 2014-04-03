package farkanoid;

/**
 * ei12130 - Eduardo Fernandes
 * ei12164 - José Pinto
 */

import ibxm.IBXMBackgroundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Vector;

public class Board extends JPanel implements ActionListener, Common, Strings {
    private final Timer timer;

    /* Status variables */
    private boolean gameHasFocus;
    private boolean gameIsPaused = false;
    private boolean inGame = true;

    /* Background music */
    private final IBXMBackgroundPlayer ibxmBackgroundPlayer;

    /* Backgrounds */
    private Background background;
    private final StaticSprite shade;
    private final StaticSprite spriteGameOver;
    private final StaticSprite spritePressAnyKey;
    private final StaticSprite outsideBorder;

    /* Sfx sounds */
    private final String hitPaddleFile = soundFolder + "HitPaddle.wav";
    private final byte[] hitPaddleArray;
    private final Runnable hitPaddleRunnable;
    private final String hitBrick = soundFolder + "HitBrick.wav";
    private final byte[] hitBrickArray;
    private final Runnable hitBrickRunnable;
    private final String lostALife = soundFolder + "LostALife.wav";
    private final byte[] lostALifeArray;
    private final Runnable lostALifeRunnable;
    private final String laserFile = soundFolder + "Laser.wav";
    private final byte[] laserFileArray;
    private final Runnable laserRunnable;
    private final String liveFile = soundFolder + "WonLife.wav";
    private final byte[] liveFileArray;
    private final Runnable liveRunnable;
    private final String cheaterFile = soundFolder + "cheater.wav";
    private final byte[] cheaterFileArray;
    private final Runnable cheaterRunnable;
    private final String babesFile = soundFolder + "babes.wav";
    private final byte[] babesFileArray;
    private final Runnable babesRunnable;

    final Options options;

    private int paddleLine;
    private boolean hasCollided = false;
    private boolean laserInMotion = false;
    private int livesLeft = 0;
    private long score = 0;
    private Ball ball;
    private Paddle paddle;
    private PowerUp powerCatch;
    private Vector<Brick> bricks = new Vector();
    private StaticSprite spriteLife;
    private boolean slowTurn = false;
    private boolean gameIsInitialized = false;
    private boolean cheatsEnabled = false;
    private int awardedLives = 0;

    public Board(Options optionsIn) {

        this.options = optionsIn;
        int timerSpeed = 10;

        switch (options.getDifficulty()) {
            case 0:
                timerSpeed = 14;
                livesLeft = Common.MAX_LIVES;
                break;
            case 1:
                timerSpeed = 12;
                livesLeft = 5;
                break;
            case 2:
                timerSpeed = 10;
                livesLeft = 3;
                break;
            case 3:
                timerSpeed = 9;
                livesLeft = 1;
                break;
            case 4:
                timerSpeed = 6;
                livesLeft = 0;
                break;
        }

        shade = new StaticSprite(imageFolder + "shade.png");
        spriteLife = new StaticSprite(spriteFolder + "life.png");
        spriteGameOver = new StaticSprite(imageFolder + "gameOver.png");
        spritePressAnyKey = new StaticSprite(imageFolder + "pressAnyKey.png");
        outsideBorder = new StaticSprite(imageFolder + "board.png");

        /* Initialize bg music */
        ibxmBackgroundPlayer = new IBXMBackgroundPlayer(musicFolder + "sweetdreams.xm");

        /* Initialize sfx */
        hitPaddleArray = loadAudio(hitPaddleFile);
        hitBrickArray = loadAudio(hitBrick);
        lostALifeArray = loadAudio(lostALife);
        laserFileArray = loadAudio(laserFile);
        liveFileArray = loadAudio(liveFile);
        cheaterFileArray = loadAudio(cheaterFile);
        babesFileArray = loadAudio(babesFile);

        hitPaddleRunnable = new PlayThread(hitPaddleArray, options.getSfxOn());
        hitBrickRunnable = new PlayThread(hitBrickArray, options.getSfxOn());
        lostALifeRunnable = new PlayThread(lostALifeArray, options.getSfxOn());
        laserRunnable = new PlayThread(laserFileArray, options.getSfxOn());
        liveRunnable = new PlayThread(liveFileArray, options.getSfxOn());
        cheaterRunnable = new PlayThread(cheaterFileArray, options.getSfxOn());
        babesRunnable = new PlayThread(babesFileArray, options.getSfxOn());

        addKeyListener(new TAdapter());
        setFocusable(true);
        addFocusListener(windowFocusListener);

        timer = new Timer(timerSpeed, this);
        timer.setRepeats(true);
        timer.start();
    }

    public byte[] loadAudio(String input) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(input);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            int next = in.read();
            while (next > -1) {
                bos.write(next);
                next = in.read();
            }
            bos.flush();
            return bos.toByteArray();
        } catch (Exception e) {

        }
        return null;
    }

    public void addNotify() {
        super.addNotify();
        initializeGame();
    }

    public void initializeGame() {
        /* Create the paddle and the ball */
        ball = new Ball();
        paddle = new Paddle();
        background = new Background(options.getDifficulty());
        initializeBricks();
        paddleLine = paddle.getY() - paddle.getHeight();
        gameIsInitialized = true;
    }

    private void initializeBricks() {

        int k = 0;
        int bricksPerRow = 14;
        for (int i = 0; i < bricksPerRow; i++) {
            int bricksPerLine = 12;
            for (int j = 0; j < bricksPerLine; j++) {

                if (k >= 0 && k < 12)
                    bricks.add(new Brick(j * 34 + Common.BORDER_SIZE + 2, i * 18 + 110, 1, 6));
                else if (k >= 24 && k < 36)
                    bricks.add(new Brick(j * 34 + Common.BORDER_SIZE + 2, i * 18 + 110, 2, 1));
                else if (k >= 48 && k < 60)
                    bricks.add(new Brick(j * 34 + Common.BORDER_SIZE + 2, i * 18 + 110, 3, 0));
                else if (k >= 72 && k < 84)
                    bricks.add(new Brick(j * 34 + Common.BORDER_SIZE + 2, i * 18 + 110, 4, 2));
                else if (k >= 96 && k < 108)
                    bricks.add(new Brick(j * 34 + Common.BORDER_SIZE + 2, i * 18 + 110, 5, 0));
                else if (k >= 120 && k < 132)
                    bricks.add(new Brick(j * 34 + Common.BORDER_SIZE + 2, i * 18 + 110, 6, 4));
                else if (k >= 144 && k < 156)
                    bricks.add(new Brick(j * 34 + Common.BORDER_SIZE + 2, i * 18 + 110, 8, 3));
                else
                    bricks.add(new Brick(j * 34 + Common.BORDER_SIZE + 2, i * 18 + 110, 1, 0));
                k++;
            }
        }
    }

    /* Override paint */
    public void paint(Graphics graphic) {
        super.paint(graphic);
        Graphics2D graphics2D = (Graphics2D) graphic;

        drawTopScreen(graphics2D);
        drawBackground(graphics2D);
        drawLaserShots(graphics2D);
        drawLifeBar(graphics2D);
        drawBall(graphics2D);
        drawBricks(graphics2D);
        drawPowerUps(graphics2D);
        drawPaddle(graphics2D);
        drawBorder(graphics2D);


        if (gameIsPaused) {
            drawPauseScreen(graphics2D);
        }

        if (!gameHasFocus) {
            drawShade(graphics2D);
        }

        if (!inGame) {
            drawGameOverScreen(graphics2D);
        }

        Toolkit.getDefaultToolkit().sync();
        graphic.dispose();
    }

    /* Draw various parts of the game */
    private void drawTopScreen(Graphics2D graphics2D) {
        /* Draw top background + score */
        graphics2D.setColor(Color.black);
        graphics2D.fillRect(0, 0, Common.GAME_WIDTH, Common.TOP_HEIGHT);

        Font font = new Font("Verdana", Font.BOLD, 14);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(font);
        String topStringLeft = "Score: " + score;
        String topStringRight = "High score: " + options.getHighScore();
        graphics2D.drawString(topStringLeft, 2, 12);
        graphics2D.drawString(topStringRight, Common.GAME_WIDTH / 2, 12);
        if (cheatsEnabled) {
            String cheaterString = "CHEATER | 'q' + score | 'w' + player | 's' slow ball";
            graphics2D.drawString(cheaterString, 2, 30);
        }
    }

    private void drawBackground(Graphics2D graphics2D) {
        /* Draw game background */
        graphics2D.drawImage(background.getImage(), 0, Common.TOP_HEIGHT, background.getWidth(), background.getHeight(), this);
    }

    private void drawBorder(Graphics2D graphics2D) {
        /* Draw game background */
        graphics2D.drawImage(outsideBorder.getImage(), 0, Common.TOP_HEIGHT, background.getWidth(), background.getHeight(), this);
    }

    private void drawLaserShots(Graphics2D graphics2D) {
        if (paddle.isHasLaser()) {
            for (int i = 0; i < paddle.getLaser().getShots().size(); i++) {

                if (paddle.getLaser().getShots().get(i).isAvailableLeft()) {
                    graphics2D.drawImage(paddle.getLaser().getImage(), paddle.getLaser().getShots().get(i).getxLeft(), paddle.getLaser().getShots().get(i).getyLeft(), paddle.getLaser().getWidth(), paddle.getLaser().getHeight(), this);
                }
                if (paddle.getLaser().getShots().get(i).isAvailableRight()) {
                    graphics2D.drawImage(paddle.getLaser().getImage(), paddle.getLaser().getShots().get(i).getxRight(), paddle.getLaser().getShots().get(i).getyRight(), paddle.getLaser().getWidth(), paddle.getLaser().getHeight(), this);
                }
            }
        }
    }

    private void drawBall(Graphics2D graphics2D) {
        graphics2D.drawImage(ball.getShadow(), ball.getShadowX(), ball.getShadowY(), ball.getWidth(), ball.getHeight(), this);
        graphics2D.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), this);
    }

    private void drawPaddle(Graphics2D graphics2D) {
        graphics2D.drawImage(paddle.getShadow(), paddle.getShadowX(), paddle.getShadowY(), paddle.getWidth(), paddle.getHeight(), this);
        graphics2D.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), this);
    }

    private void drawBricks(Graphics2D graphics2D) {
        for (int i = 0; i < bricks.size(); i++) {
            if (!bricks.get(i).isDestroyed())
                graphics2D.drawImage(bricks.get(i).getImage(), bricks.get(i).getX(), bricks.get(i).getY(), bricks.get(i).getWidth(), bricks.get(i).getHeight(), this);
        }
    }

    private void drawPowerUps(Graphics2D graphics2D) {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).isDestroyed() && bricks.get(i).getPower() != null)
                graphics2D.drawImage(bricks.get(i).getPower().getImage(), bricks.get(i).getPower().getX(), bricks.get(i).getPower().getY(), bricks.get(i).getPower().getWidth(), bricks.get(i).getPower().getHeight(), this);
        }
    }

    private void drawLifeBar(Graphics2D graphics2D) {
        for (int i = 0; i < livesLeft; i++) {
            int xPosTemp = Common.BORDER_SIZE + (5 * i) + (spriteLife.getHeight() * i * 2);
            graphics2D.drawImage(spriteLife.getImage(), xPosTemp, 482, spriteLife.getWidth(), spriteLife.getHeight(), this);
        }
    }

    private void drawPauseScreen(Graphics2D graphics2D) {
        drawShade(graphics2D);
    }

    private void drawGameOverScreen(Graphics2D graphics2D) {
        drawShade(graphics2D);
        graphics2D.drawImage(spriteGameOver.getImage(), (getWidth() - spriteGameOver.getWidth()) / 2, getHeight() / 2, spriteGameOver.getWidth(), spriteGameOver.getHeight(), this);
        graphics2D.drawImage(spritePressAnyKey.getImage(), (getWidth() - spritePressAnyKey.getWidth()) / 2, getHeight() / 2 + 40, spritePressAnyKey.getWidth(), spritePressAnyKey.getHeight(), this);
    }


    private void drawShade(Graphics2D graphics2D) {
        graphics2D.drawImage(shade.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
    /* Graphics section ends here */

    public void endGame() {
        timer.stop();
        inGame = false;

    }

    /* Check collision is part of game logic */
    private void checkCollisions() {
        /* Check only one collision at a time */
        if (ball.getPosY() > paddleLine) {
            if (!hasCollided) {
                checkCollisionPaddle();
            }
        } else {
            hasCollided = false;
            checkCollisionBricks();
        }
    }

    private void checkCollisionBricks() {
        /* Check ball intersection with the bricks */
        for (int i = 0; i < bricks.size(); i++) {
            if ((ball.getRect()).intersects(bricks.get(i).getRect())) {

                if (!bricks.get(i).isDestroyed()) {
                    /*Play sound when ball hits a brick*/
                    new Thread(hitBrickRunnable).start();

                    if (ball.getRect().intersects(bricks.get(i).getSidesBlock())) {

                        /*Change ball's direction*/
                        if (ball.isDestructiveBall()) ball.setXDir(ball.getXDir() * -1);

                        bricks.get(i).removeLive();
                        if (bricks.get(i).isDestroyed()) addToScore(bricks.get(i).getValue());

                    } else if (ball.getRect().intersects(bricks.get(i).getCenterBlock())) {

                        /*Change ball's direction*/
                        if (ball.isDestructiveBall()) ball.setYDir(ball.getYDir() * -1);

                        bricks.get(i).removeLive();
                        if (bricks.get(i).isDestroyed()) addToScore(bricks.get(i).getValue());

                    }
                    break;
                }
            }
        }

         /* Check laser shots intersection with the bricks */
        if (paddle.isHasLaser()) {

            for (int j = 0; j < paddle.getLaser().getShots().size(); j++) {

                for (int i = 0; i < bricks.size(); i++) {

                    if (!bricks.get(i).isDestroyed()) {
                        if (paddle.getLaser().getShots().get(j).isAvailableLeft()) {

                            if (paddle.getLaser().getShots().get(j).getRectLeft().intersects(bricks.get(i).getRect())) {
                            /*Removes Shot*/
                                paddle.getLaser().getShots().get(j).setAvailableLeft(false);

                                bricks.get(i).removeLive();
                                if (bricks.get(i).isDestroyed()) addToScore(bricks.get(i).getValue());
                            }
                        }

                        if (paddle.getLaser().getShots().get(j).isAvailableRight()) {

                            if (paddle.getLaser().getShots().get(j).getRectRight().intersects(bricks.get(i).getRect())) {
                            /*Removes Shot*/
                                paddle.getLaser().getShots().get(j).setAvailableRight(false);

                                bricks.get(i).removeLive();
                                if (bricks.get(i).isDestroyed()) addToScore(bricks.get(i).getValue());
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkCollisionPaddle() {
        /* Check ball intersection with the paddle */
        if ((ball.getRect()).intersects(paddle.getRect())) {
            hasCollided = true;

            //Prevent sounds from overplaying a large number of times
            if (!ball.isStuckOnPaddle()) {
                new Thread(hitPaddleRunnable).start();
            }

            //Prevents ball from leaving the paddle until the Space bar is pressed
            if (ball.isStickyBall()) {
                ball.setStuckOnPaddle(true);
            }

            /* Type cast the doubles to int since we are working at the pixel level */
            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX() + (int) (ball.getRect().getWidth() / 2);
            int ballHalfTopVerticalPosition = (int) ball.getRect().getMaxY() - (ball.getHeight() / 2);
            /* Move out of here (the value never changes) */
            int paddleHalfVertical = (int) paddle.getRect().getMaxY() - (paddle.getHeight() / 2);

            /* 7 different impact zone separators */
            int first = paddleLPos + (paddle.getWidth() / 7);
            int second = paddleLPos + ((paddle.getWidth() / 7) * 2);
            int third = paddleLPos + ((paddle.getWidth() / 7) * 3);
            int fourth = paddleLPos + ((paddle.getWidth() / 7) * 4);
            int fifth = paddleLPos + ((paddle.getWidth() / 7) * 5);
            int sixth = paddleLPos + ((paddle.getWidth() / 7) * 6);

            /* [Zone 1] Left most part of the paddle */
            if (ballLPos < first) {
                ball.setXDir(-2);

                /* Check the Height of the ball on collision */
                if (ballHalfTopVerticalPosition < paddleHalfVertical) ball.setYDir(-1);

            } else {

                if (ballLPos >= first && ballLPos < second) {
                    /* [Zone 2] The Second Light most part of the paddle */
                    ball.setXDir(-2);
                    ball.setYDir(-2);
                } else {

            /* [Zone 3] The Third Left most part of the paddle */
                    if (ballLPos >= second && ballLPos < third) {
                        ball.setXDir(-1);
                        ball.setYDir(-2);
                    } else {

            /* [Zone 4] Center part of the paddle */
                        if (ballLPos >= third && ballLPos < fourth) {
                            //ball.setXDir((ball.getXDir() > 0 ? ball.getXDir() - 1 : ball.getXDir() + 1));
                            ball.setXDir(0);
                            ball.setYDir(-2);
                        } else {

            /* [Zone 5] The Third Right most part of the paddle */
                            if (ballLPos >= fourth && ballLPos < fifth) {
                                ball.setXDir(1);
                                ball.setYDir(-2);
                            } else {
            /* [Zone 6] The Second Right most part of the paddle */
                                if (ballLPos >= fifth && ballLPos < sixth) {
                                    ball.setXDir(2);
                                    ball.setYDir(-2);
                                } else {
            /* [Zone 7] Right most part of the paddle */
                                    if (ballLPos >= sixth /*&& ballLPos < seventh*/) {
                                        ball.setXDir(2);

                                        /* Check the Height of the ball on collision */
                                        if (ballHalfTopVerticalPosition < paddleHalfVertical)
                                            ball.setYDir(-1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* Check if there is any brick left */
    private void checkVictory() {

         int remainingBricks = bricks.size();

        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).isDestroyed()) {
                remainingBricks--;
                break;
            }
        }

        if (remainingBricks == 0) {
            endGame();
        }

    }

    private void checkBallOutOfBoard() {
        /* The ball is out of the board aka you lost */
        if (ball.getRect().getMaxY() > Common.BOTTOM) {
            removeLive();
        }
    }

    private void addLive() {
        if (livesLeft == Common.MAX_LIVES) {
        } else {
            new Thread(liveRunnable).start();
            livesLeft++;
        }
    }

    private void removeLive() {

        ball.resetALLPowerUps();
        removeFallingPowerUps();

        new Thread(lostALifeRunnable).start();

        if (livesLeft == 0) {
            endGame();
        } else {
            livesLeft--;
            paddle = new Paddle();
            ball = new Ball();
        }
    }

    private void addToScore(int in) {
        score += in;
        if ((score >= 20000) && (awardedLives == 0)) {
            awardedLives++;
            addLive();
        } else if (score >= (60000 * awardedLives) && (awardedLives > 0)) {
            awardedLives++;
            addLive();
        }
        if (score > options.getHighScore()) {
            options.setHighScore(score);
        }
    }

    public void resetScore() {
        score = 0;
    }

    public boolean getGameStatus() {
        return inGame;
    }

    public boolean getPauseStatus() {
        return gameIsPaused;
    }

    public Vector<Brick> getBricks() {
        return bricks;
    }

    public Ball getBall() {
        return ball;
    }

    public void togglePause() {
        gameIsPaused = !gameIsPaused;
    }

    /* Action Listener for timer */
    public void actionPerformed(ActionEvent e) {
        if (inGame && gameIsInitialized && !gameIsPaused) {

            /*Move every element in the game*/
            /* Slow ball */
            if (ball.isSlowBall()) {
                if (slowTurn) {
                    ball.move();
                    slowTurn = false;
                } else {
                    slowTurn = true;
                }
            } else {
                ball.move();
            }
            /*Sticky ball*/
            if (ball.isStuckOnPaddle()) {
                ball.setX(ball.getX() + paddle.move());
            } else {
                paddle.move();
            }
            /*Paddle with lasers*/
            if (paddle.isHasLaser()) paddle.getLaser().move();

            movePowerUps();

            /*Check collisions between elements*/
            checkPowerUpCollision();
            checkCollisions();
            checkBallOutOfBoard();
            checkVictory();
            removeDestroyedPowerUpAndBricksAndShots();

        }

        repaint();
    }

    private void removeDestroyedPowerUpAndBricksAndShots() {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).isDestroyed() && bricks.get(i).getPower() == null) {
                bricks.remove(i);
                break;
            }
            if (bricks.get(i).isDestroyed() && bricks.get(i).getPower() != null && !bricks.get(i).getPower().isAvailable()) {
                bricks.remove(i);
                break;
            }
        }
        if (paddle.isHasLaser()) {
            paddle.getLaser().removeUsedShots();
        }

    }

    private void removeFallingPowerUps() {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).isDestroyed() && bricks.get(i).getPower() != null && bricks.get(i).getPower().isAvailable()) {
                bricks.get(i).getPower().setAvailable(false);
            }
        }
    }

    private void disableAllPowerUps() {
        ball.resetALLPowerUps();
        paddle.setHasLaser(false);
        paddle.setBigPaddle(false);
    }

    private void checkPowerUpCollision() {

        Rectangle rectPaddle = this.paddle.getRect();

        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).isDestroyed() && bricks.get(i).getPower() != null) {

                if (rectPaddle.intersects(bricks.get(i).getPower().getRect())) {
                    switch (bricks.get(i).getPower().getType()) {

                        //Power Up: Sticky Ball
                        case 1:
                            //Disables any other power on
                            disableAllPowerUps();
                            //Stops draw of the power up
                            bricks.get(i).getPower().setAvailable(false);
                            //Turn power up on
                            ball.setStickyBall(true);
                            break;

                        //Power Up: Enlarged Paddle
                        case 2:
                            //Disables any other power on
                            disableAllPowerUps();
                            //Stops draw of the power up
                            bricks.get(i).getPower().setAvailable(false);
                            //Turn power up on
                            paddle.setBigPaddle(true);
                            break;

                        //Power Up: Destructive Ball
                        case 3:
                            //Disables any other power on
                            disableAllPowerUps();
                            //Stops draw of the power up
                            bricks.get(i).getPower().setAvailable(false);
                            //Turn power up on
                            ball.setDestructiveBall(true);
                            break;

                        //Power Up: Lasers
                        case 4:
                            //Disables any other power on
                            disableAllPowerUps();
                            //Stops draw of the power up
                            bricks.get(i).getPower().setAvailable(false);
                            //Turn lasers on
                            paddle.setHasLaser(true);
                            break;

                        //Power Up:
//                        case 5:
//
//                            bricks.get(i).getPower().setAvailable(false);
//                            break;

                        //Power Up: Extra Life
                        case 6:
                            addLive();
                            bricks.get(i).getPower().setAvailable(false);
                            break;

                        //Power Up: Slow Ball
                        case 7:
                            //Disables any other power on
                            ball.resetALLPowerUps();
                            //Stops draw of the power up
                            bricks.get(i).getPower().setAvailable(false);
                            //Turn power up on
                            ball.setSlowBall(true);
                            break;
                    }
                }
            }
        }
    }

    private void movePowerUps() {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).isDestroyed() && bricks.get(i).getPower() != null)
                bricks.get(i).getPower().move();
        }
    }

    public Paddle getPaddle() {
        return paddle;
    }

    private void exitGame() {
        MainWindow topFrame = (MainWindow) SwingUtilities.getWindowAncestor(this);
        topFrame.removeBoard();
        topFrame.openMenu();
    }

    /* Focus listener */
    FocusListener windowFocusListener = new FocusListener() {
        public void focusGained(FocusEvent e) {
            gameHasFocus = true;
            if (!timer.isRunning()) {
                timer.restart();
            }
            /* Play the music or un pause it if it as already begun */
            if (ibxmBackgroundPlayer.getStatus() && options.getMusicOn()) {
                ibxmBackgroundPlayer.playSong();
            }

        }

        public void focusLost(FocusEvent e) {
            /* Stop the graphical interface timer (save cpu while out of focus) */
            timer.stop();
            gameHasFocus = false;
            /* Do a last repaint for the pause screen */
            repaint();

            /* Pause music */
            if (ibxmBackgroundPlayer.getStatus() && options.getMusicOn()) {
                ibxmBackgroundPlayer.pauseSong();
            }

        }
    };

    /* Keyboard adapter */
    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {

            if (!(e.getKeyCode() == options.getLeftKey() && paddle.getDirection() > 0)) {
                if (e.getKeyCode() == options.getLeftKey())
                    paddle.setHaltMovement();
            }

            if (!(e.getKeyCode() == options.getRightKey() && paddle.getDirection() < 0)) {
                if (e.getKeyCode() == options.getRightKey())
                    paddle.setHaltMovement();
            }

            /*if (e.getKeyCode() == options.getLeftKey() || e.getKeyCode() == options.getRightKey()) {
                paddle.setHaltMovement();
            }*/

            if (e.getKeyCode() == options.getPauseKey()) {
                gameIsPaused = !gameIsPaused;
            }

            if (e.getKeyChar() == 'c' && !cheatsEnabled) {
                cheatsEnabled = true;
                new Thread(cheaterRunnable).start();
            }

            if (cheatsEnabled) {
                if (e.getKeyChar() == 's') {
                    ball.toggleSlow();
                }
            }

            if (e.getKeyCode() == options.getFireKey()) {

                ball.setStuckOnPaddle(false);

                if (paddle.isHasLaser()) {
                    if (paddle.getLaser().addShotToFire())
                        new Thread(laserRunnable).start();
                }
            }

            if (!inGame) {
                exitGame();
            }
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == options.getLeftKey()) {
                paddle.setDirection(false);
            }

            if (e.getKeyCode() == options.getRightKey()) {
                paddle.setDirection(true);
            }


            if (cheatsEnabled) {

                if (e.getKeyChar() == 'w') {
                    addLive();
                }

                if (e.getKeyChar() == 'q') {
                    addToScore(9999);
                }

                if (e.getKeyChar() == 'b') {
                    new Thread(babesRunnable).start();
                    background = new Background(99);
                }
            }

        }
    }

}