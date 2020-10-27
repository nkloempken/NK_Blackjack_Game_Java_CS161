import java.awt.*;

/**
 * The Result State used the State class to create its own tick and render loops within the game object which will
 * run only after a hand of Blackjack has been played.  This State will display the results of that hand as well
 * as a button that allows the user to move on to a new hand.
 */
public class ResultState extends State {
    private final Card cardBack = new Card(53); // Makes a new Card which can display the image for card backs.

    /**
     * Default constructor of the State allows the game and State to access and affect each other.
     * @param game - The current game.
     */
    public ResultState(Game game){
        super(game);
        game.newGameState(); // Makes a new gameState, but doesn't set the State to it yet.  This is to reset all
        // variables so that the next hand starts fresh.
    }

    /**
     * The tick method runs once every frame and checks to see if any information needs to be updated based on the
     * user input.
     */
    public void tick(){
        if(MouseManager.mousePressed){ // Checks to see if the mouse has been clicked.
            if(Game.mouseX >= 390 && Game.mouseX <= 610 && Game.mouseY >= 415 && Game.mouseY < 500){
                // If the mouse is clicked and the button is hovered, resets the lost and tied variables to false,
                // and then sets the State to betState, getting ready for a new hand.
                GameState.lost = false; // ...
                GameState.tied = false; // ...
                State.setState(game.betState); // ...
            }
        }
    }

    /**
     * The render method runs once every frame and checks to see if any drawings need to be updated based on the
     * user input.
     * @param g - Graphics object given by Java.
     */
    public void render(Graphics g) {
        for(int i = 0; i < 5;i++){ // runs 5 times, and just draws the "deck" of cards sitting off to the side.
            g.drawImage(cardBack.getImage(),110+(i*2),210,null); // draws a pile of card back images
            //// next to each other.
        }
        for(int i = 0; i < game.yourCards.size();i++){ //  Runs once for however many cards the player had in that hand.
            g.drawImage(game.yourCards.get(i).getImage(),500 - (int)((0.5)*game.yourCards.size()*72) + i*72,
                    300,null); // Draws each of the cards that the player had in the previous hand.
        }
        for(int i = 0; i < game.CPUCards.size();i++){ // Does the same for the CPU.
            g.drawImage(game.CPUCards.get(i).getImage(),500 - (int)((0.5)*game.CPUCards.size()*72) + i*72,100,
                    null); // ...
        }
        g.setColor(new Color(48, 52, 52)); // Makes a new color and sets the pen color to it.
        g.setFont(new Font("font", Font.BOLD,36)); // Changes the font size to 36.
        g.drawString("Money: $"+game.money,82,128); // Draws text to the screen.
        g.setColor(Color.black); // ...
        g.setFont(new Font("", Font.BOLD,55)); // ...
        if(!GameState.tied && !GameState.lost) { // Checks to see if the player won the previous hand.
            g.drawString("You won this hand", 235, 240); // ...
            g.drawString("You gained $" + game.betAmount, 314, 290); // ...
        }else if(!GameState.lost){ // If they didn't win, checks to see if they tied.
            g.drawString("You tied this hand",245,240); // ...
            g.drawString("You gained nothing",225,290); // ...
        }else{ // If they didn't win or tie, then they lost.
            g.drawString("You lost this hand",245,240); // ...
            if(game.betAmount !=0 ){ // If they still have money left after losing.
                g.drawString("You lost $"+game.betAmount,330,290); // ...
            }else{ // If they are all out of money.
                g.drawString("You lost it all",330,290); // Tells the player they have lost all their money.
            }
        }
        if(Game.mouseX >= 390 && Game.mouseX <= 610 && Game.mouseY >= 415 && Game.mouseY < 500){
            // Checks to see if the "New Hand" button is hovered or not.
            g.setColor(Color.WHITE); // If so, the rectangle is white.
        }else{
            g.setColor(Color.BLACK); // If not, black.
        }
        g.fillRoundRect(390,415,220,85,13,13); // New Hand button.
        g.setColor(Color.magenta); // Pink text to contrast both white and black somewhat.
        g.setFont(new Font("", Font.BOLD,30)); // ...
        g.drawString("New Hand",427,471); // ...
    }
}