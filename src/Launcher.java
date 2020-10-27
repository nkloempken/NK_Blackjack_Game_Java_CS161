/**
 * --------------------------------------------------------------------------------------------------------------------
 * The structure of the game loop was guided by a YouTube series made by  ------CodeNMore------
 *
 * This series is called New "Beginner 2D Game Programming" and starts here- https://www.youtube.com/watch?v=dEKs-3GhVKQ
 * His game was a 2D side scrolling game, but I did use the following parts of his code:
 *
 * Launcher class - Video 1
 *
 * Display class structure - Video 2
 *    - includes canvas info - Video 3
 *
 * Threads and Game loop in the Game class - Video 4
 *
 * Buffering / Images / Graphics in multiple classes : Game, Assets, SpriteSheet, ImageLoader - Videos 5 - 9
 *
 * Game loop timing mechanism(The key to getting the MouseListener working) - Video 10
 *
 * GameState system - Video 11
 *
 * Keyboard input video(for mouse input) - Video 14.

 * ------------------------------------------------------------------------------------------------------------------
 /**
 * The Class within the program which contains only the main method that will be run to start the game loop.
 * Makes a new game, and then starts the game.
 */
class Launcher{
    /**
     * Main method that creates and runs the game of Blackjack.
     * @param args //
     */
    public static void main(String args[])
    {
        Game game = new Game("Blackjack",1000,500); // Creates a new Game object called game.
        game.start(); // Starts the thread inside of the game which infinitely loops.
    }
}