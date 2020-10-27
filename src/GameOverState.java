import java.awt.*;

/**
 * The game over state is used for after they player has run out of money.  The User has no options for leaving this screen other than
 * closing out of the window.
 */
public class GameOverState extends State {
    /**
     * Default Constructor creates a new State in order to call these tick and render methods.
     * @param game - The current game.
     */
    public GameOverState(Game game){
        super(game); // Has to be called so that the game and state can access each other.
    }

    //<editor-fold desc="I don't need to tick anything in this state since there should be no updating.">
    public void tick(){

    }
    //</editor-fold>

    /**
     * The render method is required in any State, and in this one is used to still display the background, and then to put
     * some text over the top letting the player know that they ran out of money.
     * @param g - The current game.
     */
    public void render(Graphics g){
        g.setColor(Color.black); // Black
        g.setFont(new Font("", Font.BOLD,60)); // Changes the font size to 60.
        g.drawString("GAME OVER",313,240); // Prints out "GAME OVER" in the middle of the screen.
        g.setFont(new Font("", Font.BOLD,30)); // Changes font size to 30.
        g.drawString("You ran out of money :(",333,330); // Displays this text below the game over text.
    }
}