import java.awt.image.BufferedImage;

/**
 * The SpriteSheet class is used to hold the image stored in the resources folder, and to then crop that image when
 * asked by the Assets class into smaller individual images of the cards that will be used in the game.
 */
class SpriteSheet {
    private final BufferedImage sheet; // Stores the image into a final reference so that it does not have
    // to be retrieved every time a crop is needed.

    /**
     * The default constructor which takes a sheet as reference.  This sheet will be the image processed in ImageLoader
     * and the reference field of this class will then point to that image.
     * @param sheet - The image of the cards processed by the ImageLoader class.
     */
    public SpriteSheet(BufferedImage sheet){
        this.sheet = sheet; // Sets the field within this class to reference that image.
    }

    /**
     * The crop method is used to create a new, smaller image out of the larger one.  This will be used to get the
     * pictures of each of the individual cards out of the sheet image.
     * @param x - The x location the cropped image should begin at.
     * @param y - The y location the cropped image should begin at.
     * @param width - The width of the smaller image.
     * @param height - The height of the smaller image.
     * @return A BufferedImage object of that smaller image.
     */
    public BufferedImage crop(int x, int y, int width, int height){
        return sheet.getSubimage(x,y,width,height); // Returns the cropped image.
    }

}
