import java.awt.image.BufferStrategy;
import java.awt.*;
import java.util.ArrayList;

/**
 * The Game class is the meat of the game.  All other classes lead to this one, and this is what is called by the
 * main method in the launcher.  The game class is runnable, meaning that it is able to use a thread, which is
 * one method of creating an infinite loop that can simultaneously run methods, in this case tick and render.
 * The game draws images to the screen while also waiting for user input, and advances in the game accordingly.
 * Overall it should be able to simulate a game of Blackjack.
 */
class Game implements Runnable {

    public static int mouseX,mouseY; // The position of the mouse cursor on the screen, used for button checking.
    public static final Font myFont = new Font("myFont", Font.BOLD, 100); // Basic font used for the title.

    //States
    public State gameState; // Simulates a hand being played.
    public State gameOverState; // Gives the user a message telling them their game is over.
    public State betState; // Asks the user to place a bet of $1, $5, or $10.
    public State resultState; // Tells the user whether they won lost, or tied a hand.

    private final int width; // The width of the window.
    private final int height; // The height of the window.
    private final String title; // The title of the Window.

    private boolean running = false; // Stores whether or not the game is currently running.
    private BufferStrategy bs; // Lets the computer know how many ticks early it should store images before displaying
    //them.  Maximum of three, more creating slower running speed, but smoother image quality.
    private Display display; // The frame and canvas that the game will be drawn on.
    private Graphics g; // The graphics object which will be used to draw anything we need onto the canvas.
    private Thread thread; // The thread that can infinitely run tick and render to create the game loop.
    private final MouseManager mouseManager; // The mouseManager is the object that keeps track of whether or not the
    //mouse has been clicked.

    public ArrayList<Card> yourCards; // An ArrayList which holds all Card objects that the player has in their
    // current hand.
    public ArrayList<Card> CPUCards; // Same for the CPU.

    public int money = 30; // The amount of money that the player has.
    public int betAmount; // The amount that the player is/was betting in their hand.

    /**
     * The default constructor creates a new game and just passes in the title, width and height, that are provided
     * by the launcher.  In this case it is always the following.
     * @param title - "Blackjack"
     * @param width - 1000
     * @param height - 500
     */
    public Game(String title, int width, int height) {
        this.width = width; // ...
        this.height = height; // ...
        this.title = title; // ...
        mouseManager = new MouseManager(); // Creates a new MouseManager to keep track of clicking actions.
    }

    /**
     * The init method is called only once as soon as the game is started.  All of this could probably just be in the
     * constructor but is a little bit cleaner to do it this way.  Initializes the display and all States that will
     * be used later on.  Also adds the MouseManager object to the canvas.
     */
    private void init(){
        display = new Display(title, width, height); // Creates a new display.
        display.getCanvas().addMouseListener(mouseManager); // Adds the mouseListener to the canvas.
        newGameState(); // Initializes the gameState by using a method.
        State menuState = new MenuState(this); // Makes the menuState reference and initializes it.
        gameOverState = new GameOverState(this); // ...
        betState = new BetState(this); // ...
        resultState = new ResultState(this); // ...
        State.setState(menuState); // Starts the game in the menuState.
    }

    /**
     * The tick method ticks 20 times per second and updates all information each time.  This one updates the mouse
     * position, and also calls the tick method of a State if there is currently one in use.
     */
    private void tick() {
        mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX() - display.getFrameX(); // X pos relative to game.
        mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY() - display.getFrameY(); // Same for Y position.
        if(State.getState() != null){ // Checks to see if there is currently a State in use.
            State.getState().tick(); // If so, runs the tick method of that State as well.
        }
    }

    /**
     * The tick method ticks 20 times per second and updates all images on the screen each time.  This one uses a
     * BufferStrategy in order to smooth transitions between image changes, and then sets the graphics object
     * using that BufferStrategy.  The render begins by clearing everything off of the screen, and then redraws
     * the background of the Card table and players.  Then, if there is a State currently in use, calls the render
     * method of that State.  Finally, displays all of this on the canvas and then gets rid of the graphics object.
     * Sets the mousePressed boolean to false just in case and then begins again.
     */
    private void render() {
        bs = display.getCanvas().getBufferStrategy(); // Sets the game BufferStrategy to that of the canvas.
        if (bs == null) { // Makes sure that the BufferStrategy isn't null.
            display.getCanvas().createBufferStrategy(3); // If it is, creates one.
            return; // Restarts the loop.
        }
        g = bs.getDrawGraphics(); // Sets the graphics object to use the newly set BufferStrategy.
        g.clearRect(0, 0, width, height); // Clears the entire canvas.
        drawBackground(); // Draws the background of the game.
        if(State.getState() != null){ // Checks to see if a State is being used.
            State.getState().render(g); // If so, calls the render method of that state.
        }
        bs.show(); // Displays the drawn objects on the screen.
        g.dispose(); // Gets rid of the graphics object.
        MouseManager.mousePressed = false; // Sets to false just in case it has not already reset.
    }

    /**
     * The start method is called in the main method, and begins the infinite thread loop.
     */
    public synchronized void start() {
        if (running) { // If the game is already running do nothing.
            return;
        }
        running = true; // If not, set the boolean to true that indicates that it is running.
        thread = new Thread(this); // Make a new Thread.
        thread.start(); // Start the thread.
    }

    /**
     * The stop method, when called, stops the thread from running and sets the running boolean to false;
     */
    private synchronized void stop() {
        if (!running) { // Checks to make sure the thread is even running.
            return; // If not does nothing.
        }
        running = false; // If so, sets the running boolean to false;
        try {
            thread.join(); // Kills the thread.
        } catch (InterruptedException e) {
            e.printStackTrace(); // Have to put this to make Java happy.
        }
    }

    /**
     * Draws the background image used for all states of the game.  Shows the player, the cpu player, and a card table.
     */
    private void drawBackground() {
        Color brown = new Color(90, 50, 0); // Makes a new color called brown.
        g = bs.getDrawGraphics(); // Redundant but necessary so java knows whats going on.

        g.setColor(Color.GRAY); // Sets the color to Gray.
        g.fillRect(0, 0, 1000, 500); // Draws a rectangle for the background color/floor.

        g.setColor(brown); // ...
        g.fillRect(50, 50, 900, 400); // Edges of the table.

        g.setColor(new Color(30, 160, 30)); // ...
        g.fillRect(68, 68, 864, 364); // Table top

        g.setColor(brown); // ...
        g.fillRect(440, 0, 120, 50); // Chairs for player abd CPU
        g.fillRect(440, 450, 120, 50); // ...

        g.setColor(Color.BLACK); // ...
        g.fillOval(460, 0, 80, 80); // Player and CPU drawings.
        g.fillRect(445, 30, 20, 20); // ...
        g.fillRect(535, 30, 20, 20); // ...
        g.fillOval(460, 420, 80, 80); // ...
        g.fillRect(445, 450, 20, 20); // ...
        g.fillRect(535, 450, 20, 20); // ...
    }

    /**
     * The run method is what is called when the thread is started.  Contains the infinite loop that the game uses.
     */
    public void run() {
        init(); // Initializes the States and and display.
        int fps = 20; // Sets the default frame rate to 20 fps(Doesn't need to be high, and lower actually helps
        // the computer accurately recognize when the user has clicked and when they have not.)
        double timePerTick = 1000000000.0/fps; // Converts the fps to nano-seconds
        double delta = 0; // Used to keep track of whether or not enough time has passed to loop again.
        long now; // Keeps track of current CPU time.
        long lastTime = System.nanoTime(); // Keeps track of the previous CPU time.
        while (running) { // While the game/thread is running.
            now = System.nanoTime(); // sets the current time to the current CPU time in nanos.
            delta += (now-lastTime)/timePerTick; // Increments the delta variable.
            lastTime = now; // Sets the last time to the current time.
            if(delta >= 1){ // If enough time has passed since the last time the game has looped.
                tick(); // ticks
                render(); // renders
                delta -=1; // resets delta to 0 ish;
            }
        }
        stop(); // stops the thread once the game is no longer running.
    }

    /**
     * Allows other classes, or this one to create a new gameState State.  This resets all variables and such which
     * is necessary to do between each hand.
     */
    public void newGameState(){
        gameState = new GameState(this); // Makes a new GameState.
    }
}