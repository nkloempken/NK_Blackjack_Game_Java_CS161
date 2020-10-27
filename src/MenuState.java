import java.awt.*;

/**
 * The MenuState class uses the State class to create a separate tick and render loop fo when the user is on the
 * main menu.
 */
public class MenuState extends State {
    /**
     * The default constructor connects the menu State to the current game so that they have access to, and can affect
     * each other.
     * @param game - The game object that the State refers to.
     */
    public MenuState(Game game){
        super(game); // Have to call this to sync them up.
    }
    /**
     * The tick method occurs in the game, and in all states, this one is added to the game only when the
     * menu screen is to be visible.  It's only job is to check to see if the user has clicked the start button.
     */
    public void tick(){
        if (MouseManager.mousePressed && Game.mouseX >= 410 && Game.mouseX < 590 && Game.mouseY <= 335 &&
                Game.mouseY >= 280){ // Checks to see if the mouse is over the start button and has been clicked.
            State.setState(game.betState); // If so, advances to the betState.
        }
    }

    /**
     * The render method occurs in the game class and in all states and draws the appropriate images on the
     * screen depending on what state the game is in.  This one Just displays the opening menu screen,
     * showing the game BlackJack and providing a start button that is highlighted when hovered.
     * @param g - The graphics object provided by Java.
     */
    public void render(Graphics g){
        g.setColor(Color.BLACK); // Changes the color to black.
        g.setFont(Game.myFont); // Changes the font to the basic font used in the game.
        g.drawString("Black", 220, 200); // Draws the given string to the screen at the given location.
        g.setColor(Color.RED); // ...
        g.drawString("Jack", 510, 200); // ...
        if(Game.mouseX <= 410 || Game.mouseX > 590 || Game.mouseY <= 280 || Game.mouseY > 335) {
            // This checks to see if the start button has NOT been hovered.
            g.setColor(Color.BLACK); // ...
            g.fillRect(410,280,180,55); // ...
            g.setColor(Color.WHITE); // ...
            g.setFont(new Font("font", Font.BOLD,30)); // Changes the font size to 30.
            g.drawString("START",450,320); // ...
        }else {
            g.setColor(Color.WHITE); // ...
            g.fillRect(410, 280, 180, 55); // ...
            g.setColor(Color.BLACK); // ...
            g.setFont(new Font("font", Font.BOLD,30)); // ...
            g.drawString("START",450,320); // ...
        }
    }
}
