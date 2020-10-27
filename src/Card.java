import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * The Card class creates instances of Card objects which are used by the player and by the computer
 * in order to play the game of blackjeck.  They hold a value which is between 1 and 11.
 */
class Card {

    private final BufferedImage image; // The image that the value of the card represents
                                       // From the ArrayList in the Assets class.
    private int value; // The value of the card between 1 and 11.
    /**
     * Default constructor creates a Card with a random value;
     */
    public Card() {
        this(-1);
    }
    /**
     * Alternate constructor that creates a new Card but with a set value
     * @param c - the value the new card will have.
     */
    public Card(int c){
        int card; // Between 1 and 53, relating to the ArrayList in the Assets class.
        if(c > 0 && c < 54){ // If the input is valid, assigns the input c to card, 1 to 53.
            card = c;
        }else {
            Random rng; // Creates a new Random object.
            rng = new Random(); // Instantiates.
            card = rng.nextInt(52) + 1; // Makes a new random card between 1 and 52 if given invalid input.
        }
        image = Assets.images.get(card); // Assigns the image held in this Card to the one in the Assets class.
        if((card > 9 && card < 14)||(card >22 && card < 27)||(card > 35 && card < 40)||(card >48 && card < 53)) {
            // The pictures of all face cards and 10's of all suits.
            value = 10; //Assigns the value.
        }else if(card < 14){ // If not, and it is less than 14, the value is just the card number that it is.
            value = card; //Assigns the value.
        }else if(card < 27){ // If not, but it is less than 27, the value is the card - 13
            value = card - 13;// ...
        }else if(card < 40){ // ...
            value = card - 26; // ...
        }else if (card < 53){ // ...
            value = card - 39; // ...
        }else{ // If the card is the card back, gets -1 just in case
            value = -1; // ...
        }
        if(value == 1){ // Assigns Aces to begin at 11 instead of 1.
            value = 11; // ...
        }
    }

    /**
     * The getImage method returns the image stored by a card object.
     * @return - The image stored in image.
     */
    public BufferedImage getImage(){
        return this.image; // Returns the image from the Assets class to be used in the Game class.
    }

    /**
     * The getValue method returns the value of the card to be used in the game.
     * @return - The value of the card.
     */
    public int getValue(){
        return value; // ...
    }

    /**
     * The setValue method allows outside classes to change the value of Card objects.
     * This is used mostly for aces since they can either have a value of 11 or of 1.
     * @param value - The value the card will obtain after being called.
     */
    public void setValue(int value){
        this.value = value; // Sets the value of the card.
    }
}
