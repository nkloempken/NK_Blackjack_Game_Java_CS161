import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The Assets class holds an Array List of images.  One for each of the 52 cards + 1 for the card back image.
 * These can then be accessed in order to display each individual card.
 */
abstract class Assets {

    public static final ArrayList<BufferedImage> images = new ArrayList<>(); // ArrayList holding all card images from the Cards SpriteSheet.
    private static final int width = 72, height = 96; // Width and Height of each card in pixels.

    static {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/cards.png")); // Gets the Cards image from the SpriteSheet class.
        images.add(sheet.crop(0,0,100,100)); // Adds a random image to slot 0 since it won't be used.  But simplifies some math.
        for(int i = 0; i < 4; i ++){ // Loops 4 times for 4 suits.
            for(int j = 0; j < 13;j++){ // Loops 13 times for 13 different possible cards of each suit.
                images.add(sheet.crop(j * width,i*height,width,height)); // Adds the cropped card image into the ArrayList.
            }
        }
        images.add(sheet.crop(13*width,0,width,height)); // Adds the image of the card back to the end of the ArrayList at slot 53.
    }
}
