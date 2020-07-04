package images;

import main.Canvas;

import java.awt.image.BufferedImage;

/**
 * Specifies the parameters required to draw an image.
 */
public abstract class ImageSource {

    /**
     *
     * @param renderer the Renderer
     * @param height
     * @param width
     * @return
     */
    public abstract BufferedImage renderImage(Renderer renderer, int height, int width);
}
