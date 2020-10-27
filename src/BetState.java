import java.awt.*;

/**
 * The BetState class uses the State class and its void and tick methods to create a separate loop while the player
 * is making their bet.  They can either click on a square to choose a $1 bet, a $5, or a $10 bet.  From there it
 * transitions to the gameState.
 */
public class BetState extends State {
    /**
     * The default constructor.  Makes a new instance of a betState and imports the current game into it so that
     * they have access to each other.
     * @param game - The current game.
     */
    public BetState(Game game){
        super(game); // Have to call this to give them access to each other.
    }

    /**
     * Called in every State.  This is the method that updates the information every time the computer goes through the
     * infinite thread loop.
     */
    public void tick(){
        if (game.money <= 0){ // Makes sure that the player has not already gotten to $0 of money
            State.setState(game.gameOverState); // If they are at 0, goes to the gameOver state.
        }
        if(MouseManager.mousePressed){ // Checks to see if the mouse has been clicked this frame.

            if(Game.mouseX >= 125 && Game.mouseX <= 275 && Game.mouseY >= 300 && Game.mouseY <= 400){
                // All of these if statements are checking to see if an on screen button has been clicked.
                game.betAmount = 1; // If they clicked the $1 bet, changes it to $1
                game.newGameState(); // Clunky way to reset all variables in the gameState.
                State.setState(game.gameState);
            }
            if(Game.mouseX >= 425 && Game.mouseX <= 575 && Game.mouseY >= 300 && Game.mouseY <= 400){ // ...
                game.betAmount = 5; // ...
                game.newGameState(); // ...
                State.setState(game.gameState);
            }
            if(Game.mouseX >= 725 && Game.mouseX <= 875 && Game.mouseY >= 300 && Game.mouseY <= 400){ // ...
                game.betAmount = 10; // ...
                game.newGameState(); // ...
                State.setState(game.gameState);
            }
        }
    }

    /**
     * Uses the Graphics class to draw things to the canvas.  Draws text to ask how much the player is betting, and
     * draws three buttons for the player to click on.
     * @param g - Graphics object provided by Java.
     */
    public void render(Graphics g){
        g.setColor(Color.BLACK); // Changes the color to black for the text.
        g.setFont(new Font("", Font.BOLD,40)); // Redundant way to change the text size.
        g.drawString("How much are you betting?",240,200); // Text displayed on the canvas.
        if(Game.mouseX >= 125 && Game.mouseX <= 275 && Game.mouseY >= 300 && Game.mouseY <= 400){
            // All of these if statements check to see if a button is hovered, and if so, changes the rectangle color
            // so that it appears to be highlighted.
            g.setColor(Color.WHITE); // Color change to White.
        }
        g.fillRoundRect(125,300,150,100,15,15); // Draws a rectangle.
        if(Game.mouseX >= 425 && Game.mouseX <= 575 && Game.mouseY >= 300 && Game.mouseY <= 400){ // ...
            g.setColor(Color.WHITE); // ...
        }else{
            g.setColor(Color.black); // ...
        }
        g.fillRoundRect(425,300,150,100,15,15);
        if(Game.mouseX >= 725 && Game.mouseX <= 875 && Game.mouseY >= 300 && Game.mouseY <= 400){ // ...
            g.setColor(Color.WHITE); // ...
        }else{
            g.setColor(Color.black); // ...
        }
        g.fillRoundRect(725,300,150,100,15,15); // Draws a rectangle.
        g.setColor(Color.MAGENTA); // Changes the color to pink.
        g.drawString("$1",175,365); // These three display the text for the bet amounts.
        g.drawString("$5",475,365); // ...
        g.drawString("$10",766,365); // ...
    }
}