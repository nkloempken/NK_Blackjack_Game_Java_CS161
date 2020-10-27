import java.awt.*;
import java.util.ArrayList;
/**
 * The GameState class uses the State class to have separate tick and render methods for when the player is
 * Playing a hand of blackjack.  The player is given the option to hit or stand after seeing his own cards and
 * one of the CPU cards.  As long as his total is under 21, he can continue hitting.  Once he is over 21, or he chooses
 * to stand, the game will advance to the results state to show whether he won, tied, or lost that hand.
 */
public class GameState extends State {

    public static boolean tied = false; // Stores whether the result is a tie or not.
    public static boolean lost = false; // Stores whether the result is a loss or not.

    private int yourTotal; // The total of the values of the player's cards.

    private final Card cardBack = new Card(53); // A card that can be used to draw card backs.

    /**
     * The default constructor which is used to tie the game and State classes together so that they have access to,
     * and can influence each other.
     * @param game - The current game.
     */
    public GameState(Game game){
        super(game); // Connect the State to the Game/Thread.
        newTurn(); // Calls the newTurn method which resets all variables and starts a hand of Blackjack.
    }

    /**
     * The tick method is called inside of the game's tick method.  Every time it is called, information is updated
     * if necessary based on the user input.
     */
    public void tick(){
        if(Game.mouseX >=250 && Game.mouseX <=410 && Game.mouseY >= 400 && Game.mouseY <= 500
                && MouseManager.mousePressed){ // Checks to see if The player has clicked the "Hit" button.
            drawCard(); // If so, draws a new card and adds it to the player's hand.
        }
        if(Game.mouseX >=590 && Game.mouseX <=850 && Game.mouseY >= 400 && Game.mouseY <= 500
                && MouseManager.mousePressed){ // Checks to see if the player has clicked the "Stand" button.
            CPUTurn(); // If so checks the CPU's hand to see who wins.
        }
        checkPlayer(); // Checks the player to make sure that he does not have/has not gone over 21.
    }

    /**
     *The render method is called inside of the game's tick method.  Every time it is called, images are updated
     * if necessary based on the user input.
     * @param g - Graphics object given by Java.
     */
    public void render(Graphics g){
        if(Game.mouseX >=250 && Game.mouseX <=410 && Game.mouseY >= 400 && Game.mouseY <= 500){
            // Checks to see if the button is hovered or not.
            g.setColor(Color.white); // If so, highlights it with a white color.
            g.fillOval(250,400,160,160); // Draws the button.
        }else { // If not
            g.setColor(Color.black); // ...
            g.fillOval(250, 400, 160, 160); // ...
        }
        if(Game.mouseX >=590 && Game.mouseX <=850 && Game.mouseY >= 400 && Game.mouseY <= 500){ // Same for the other
            g.setColor(Color.white); // ...
            g.fillOval(590,400,160,160); // ...
        }else{
            g.setColor(Color.black); // ...
            g.fillOval(590, 400, 160, 160); // ...
        }

        g.setColor(new Color(45, 68, 158)); // Makes a new blue color and sets the current color to it.
        g.fillOval(255,405,150,150); // Draws the buttons
        g.fillOval(595,405,150,150); // ...

        g.setColor(Color.WHITE); // ...
        g.setFont(new Font("font", Font.BOLD,36)); // Changes the font size to 36.
        g.drawString("HIT",300,470); // Writes the button labels.
        g.drawString("STAND",610,470); // ...

        for(int i = 0; i < 5;i++){ // Loops five times to make a mini deck as a decoration off to the side.
            g.drawImage(cardBack.getImage(),110+(i*3),210,null); // Draws five card back images
        }
        for(int i = 0; i < game.yourCards.size();i++){ // Loops once for how many cards the player has.
            g.drawImage(game.yourCards.get(i).getImage(),500 - (int)((0.5)*game.yourCards.size()*72) + i*72,
                    300,null); // draws all the cards next to each other and overall centered.
        }
        for(int i = 0; i < game.CPUCards.size();i++){ // Same for CPU.
            g.drawImage(game.CPUCards.get(i).getImage(),500 - (int)((0.5)*game.CPUCards.size()*72) + i*72,
                    100,null); // ...
        }
        g.setColor(new Color(48, 52, 52)); // ...
        g.drawString("Money: $"+game.money,82,128); // ...
        g.drawString("Bet: $"+game.betAmount, 105,178); // ...
        if(game.betAmount > game.money){ // Checks to see if the player tried to bet more than they have.
            game.betAmount = game.money; // If so, sets the bet to the maximum/the amount they have left.
        }
    }

    /**
     * The drawCard method is used to simulate the player hitting and being given a new card from the dealer.
     */
    private void drawCard(){
        game.yourCards.add(new Card()); // A new card is added to the player's ArrayList of cards.
        yourTotal += game.yourCards.get(game.yourCards.size()-1).getValue(); // The player's total is updated.
    }

    /**
     * The newTurn method is used to reset variables at the beginning of a new turn.  All of these could be in the
     * constructor but these all have to do with the cards and totals of the player and CPU so it is nice to have
     * them all organized together.
     */
    private void newTurn(){
        yourTotal = 0; // ...
        game.yourCards = new ArrayList<>(); // Resets the player's cards.
        game.CPUCards = new ArrayList<>(); // Resets the CPU cards.
        game.yourCards.add(new Card()); // Gives the player two new cards.
        game.yourCards.add(new Card()); // ...
        game.CPUCards.add(new Card ()); // Gives the CPU a new random card.
        game.CPUCards.add(new Card(53)); // Gives the player a card back to display until they are ready to show.
        yourTotal += game.yourCards.get(0).getValue(); // Updates the player's total.
        yourTotal += game.yourCards.get(1).getValue(); // ...
    }

    /**
     * The checkPlayer method is used to make sure that the player does not have, or has not gone over 21 yet.
     */
    private void checkPlayer(){
        boolean aces = false; // Stores whether or not the player has aces left that could be changed from 11 to 1;
        for(int i = 0; i < game.yourCards.size(); i ++){ // Looks through all of the player's cards.
            if(game.yourCards.get(i).getValue() == 11){ // If any are aces.
                aces = true; // Sets the aces variable to true;
                break; // Escapes early.
            }
        }
        if(yourTotal > 21){ // Checks to see if the player's total is greater than 21.
            if(!aces){ // If so, checks to see if they have any convertible aces.
                GameState.lost = true; // If not, they lose.
                game.money -= game.betAmount; // ...
                State.setState(game.resultState); // ...
            }else{ // If they do have at least one convertible ace
                for(int i = 0; i < game.yourCards.size(); i ++){ // Looks through all of the players cards.
                    if(game.yourCards.get(i).getValue() == 11){ // Once a convertible ace is found, changes it to 1.
                        game.yourCards.get(i).setValue(1); // ...
                        yourTotal -= 10; // Updates the player's total.
                        break; // Escapes early.
                    }
                }
            }
        }else if(yourTotal == 21 && game.yourCards.size() > 2){ // If the player's total is 21 and they have more than
            // 2 cards in their hand.
            CPUTurn(); // Advances to the computer's turn to see if it can tie.
        }else if(yourTotal == 21){ // If the player has 21 with only two cards (A Blackjack).
            game.betAmount *= 1.5; // The player wins 1.5 times their bet rounded down.  Rip 1$ bets.
            game.money += game.betAmount; // Updates the player's money.
            State.setState(game.resultState); // Advances to the results screen.
        }
    }

    /**
     * The CPUTurn method simulates the dealer taking their turn to find out whether the player wins, ties, or loses.
     * The CPU must take into account aces and must hit at 16 or less unless already beating the player.
     */
    private void CPUTurn(){
        int CPUTotal = 0; // Sets the CPU total to 0.
        game.CPUCards.remove(1); // Removes the card back card from the CPU's ArrayList.
        game.CPUCards.add(new Card()); // Adds a new random card to replace it.
        CPUTotal += game.CPUCards.get(0).getValue(); // Updates the CPU total.
        CPUTotal += game.CPUCards.get(1).getValue(); // ...
        boolean ace = false; // Stores whether or not the CPU has a convertible ace.
        boolean lost = false; // Stores whether or not the player has already lost (Added this to fix an odd bug.)
        if(game.CPUCards.get(0).getValue() == 11||game.CPUCards.get(1).getValue() == 11){ // Checks to see if the
            // CPU has any aces and then updates the ace boolean.
            ace = true; // ...
        }
        if(game.CPUCards.get(0).getValue() == 11&&game.CPUCards.get(1).getValue() == 11){ // Checks to see if the CPU
            // happens to have two aces.
            game.CPUCards.get(1).setValue(1); //If so, one has to immediately be converted.
            CPUTotal -= 10; // CPU total is updated.
        }
        if(CPUTotal > yourTotal && CPUTotal <= 21){ // Checks to see if the CPU has already won.
            lost = true; // If so, the player has lost.
            GameState.lost = true; // ...
            game.money -= game.betAmount; // Player loses money equal to their bet amount.
            State.setState(game.resultState); // Transitions to the results State.
        }
        while(!lost && (CPUTotal < 17 || ((CPUTotal - 11) < 17 && ace && CPUTotal < yourTotal)|| CPUTotal < yourTotal)){
            // This loops as long as the dealer would need to draw.  If under 17, if under 17 after converting an ace,
            // or if the player is currently beating the dealer.
            Card newCard = new Card(); // The CPU gets another card.
            game.CPUCards.add(newCard); // ...
            CPUTotal += newCard.getValue(); // CPU total is updated.
            for(int i = 0; i < game.CPUCards.size(); i ++){ // Loops through all of the CPU cards.
                if(game.CPUCards.get(i).getValue() == 11){ // If an ace is found.
                    ace = true; // Updates ace variable.
                }
            }
            if(ace && CPUTotal > 21){ // If the CPU would lose, but has a convertible ace, converts it.
                for(int i = 0; i < game.CPUCards.size(); i ++){ // Finds a convertible ace.
                    if(game.CPUCards.get(i).getValue() == 11){ // ...
                        game.CPUCards.get(i).setValue(1); // Sets the value to 1 instead of 11.
                        CPUTotal -= 10; // Updates the CPU total.
                        break; // Escapes the loop early.
                    }
                }
            }
            ace = false; // Resets the ace tracker variable.
        }
        if(!lost && CPUTotal != yourTotal && CPUTotal >= 22 ){ // Checks to see if the player hasn't lost, and the
            // game is not tied, and the computer's total is over 21.
            game.money += game.betAmount; // If so, the player wins and is given their money.
            State.setState(game.resultState); // ...
        }else if(CPUTotal == yourTotal && CPUTotal < 22){ // Checks to see if the game was tied.
            GameState.tied = true; // If so, advances to results with no money updating.
            State.setState(game.resultState); // Advances to resultsState.
        }else if(!lost && CPUTotal > yourTotal){ // Checks to see if the player hasn't lost but should.
            GameState.lost = true; // If so, the player loses.
            game.money -= game.betAmount; // The money is updated.
            State.setState(game.resultState); // The State advances to the resultsState.
        }

    }
}
