package images;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Specifies the parameters required to draw an image.
 */
public abstract class ImageSource {

    /**
     * This method generates a BufferedImage based on the information contained within this image source.
     * @param renderer the Renderer used to generate the image.
     * @param height the height of the image in pixels
     * @param width the width of the image in pixels
     * @return the rendered BufferdImage
     */
    public abstract BufferedImage renderImage(Renderer renderer, int height, int width);

    /**
     * @return the background color of this image source.
     */
    public abstract Color getBackgroundColor();
}
