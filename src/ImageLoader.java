import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * The ImageLoader class is used to take an image from the resources folder and to turn it into something that
 * the program can read and use.  The image that is used in this game is that of all 52 card faces and a card back.
 */
class ImageLoader {
    /**
     * This is a class method which will be used to call from another class in order to pull in an image from a
     * folder into some class that can use it for something.  In this case, the Assets class.
     * @param path - Refers to the file name of the image and where it is located.
     * @return - The image as a bufferedImage object.
     */
    public static BufferedImage loadImage(String path) {
        try { //Tries to return the image.
            return ImageIO.read(ImageLoader.class.getResource(path)); // ...
        } catch (IOException e) { // If it cannot, catches this exception and then exits the program.
            e.printStackTrace(); // ...
            System.exit(1); // ...
        }
        return null; // Java says it needs a return even though theoretically, would never reach this point.
    }
}
