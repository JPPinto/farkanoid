package farkanoid;

/**
 * ei12130 - Eduardo Fernandes
 * ei12164 - José Pinto
 * Board Tests
 */

import static org.junit.Assert.*;

public class Tests {
    private Options testOptions;
    private TestWindow testWindow;

    /*
     * Create a new farkanoid for each test, so that the tests don't affect each other.
     */
    @org.junit.Before
    public void runBeforeEveryTest() {
        testOptions = new Options();
        testOptions.setSfx(false);
        testOptions.setMusic(false);
        testOptions.setDifficulty(3);
        testWindow = new TestWindow(testOptions);
    }

    /*
     * End the game and dispose of the window.
     */
    @org.junit.After
    public void runAfterEveryTest() {
        testWindow.board.endGame();
        testWindow.dispose();
    }

    /*
     * Test if the game ends when the endGame() function is called.
     */
    @org.junit.Test
    public void testEndGame() throws Exception {
        testWindow.board.endGame();
        if (testWindow.board.getGameStatus()) {
            fail();
        }
    }

    /*
     * Test the pause function by calling the pause function.
     * NOTE: It always fails since the function is not implemented yet.
     */
    @org.junit.Test
    public void testPauseGame() throws Exception {
        testWindow.board.togglePause();

        if (!testWindow.board.getPauseStatus()) {
            fail();
        }
    }

    /*
     * Test the collision function by colliding the paddle against the left wall.
     */
    @org.junit.Test
    public void testCheckCollisions() throws Exception {
        /* Test paddle movement to the left */
        testWindow.board.getPaddle().setMovementLeft();
        Thread.currentThread().sleep(2500);

        if (testWindow.board.getPaddle().getPosX() != 20) {
           fail();
        }
    }

    /*
     * Test paddle movement with a 3 stage test.
     * The x values should change or stay the same according to the movement sent.
     * The y values should never change.
     */
    @org.junit.Test
    public void testPaddleMovement() throws Exception {
        /* Test if the paddle doesn't move on its own (x should remain the same) */
        int x = testWindow.board.getPaddle().getPosX();
        int y = testWindow.board.getPaddle().getPosY();

        if (y != testWindow.board.getPaddle().getPosY()) {
            fail();
        }

        if (x != testWindow.board.getPaddle().getPosX()) {
            fail();
        }

        /* Test paddle movement to the left  (x should decrease) */
        testWindow.board.getPaddle().setMovementLeft();
        Thread.currentThread().sleep(1000);
        testWindow.board.getPaddle().setHaltMovement();

        if (y != testWindow.board.getPaddle().getPosY()) {
            fail();
        }


        if (x <= testWindow.board.getPaddle().getPosX()) {
            fail();
        }

        /* Test paddle movement to the right (x should increase) */
        x = testWindow.board.getPaddle().getPosX();
        y = testWindow.board.getPaddle().getPosY();

        testWindow.board.getPaddle().setMovementRight();
        Thread.currentThread().sleep(1000);
        testWindow.board.getPaddle().setHaltMovement();

        if (y != testWindow.board.getPaddle().getPosY()) {
            fail();
        }

        if (x >= testWindow.board.getPaddle().getPosX()) {
            fail();
        }

    }

    /*
     * Test if a specific brick is destroyed or not.
     */
    @org.junit.Test
    public void testBrickDestroy() throws Exception {
        int brickCount = testWindow.board.getBricks().size();
        testWindow.board.getBall().setStuckOnPaddle(false);
        Thread.currentThread().sleep(5000);
        int afterBrickCount = testWindow.board.getBricks().size();
        if (afterBrickCount == brickCount) {
            fail();
        }
    }

    /*
     * Test the natural ball movement.
     */
    @org.junit.Test
    public void testBallMovement() throws Exception {
        int x = testWindow.board.getBall().getPosX();
        int y = testWindow.board.getBall().getPosY();
        testWindow.board.getBall().setStuckOnPaddle(false);
        Thread.currentThread().sleep(1000);


        if (x == testWindow.board.getBall().getPosX()) {
            fail();
        }

        if (y == testWindow.board.getBall().getPosY()) {
            fail();
        }
    }
}
