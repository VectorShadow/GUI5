package images;

import main.Canvas;

/**
 * Specifies the parameters required to draw an image.
 */
public abstract class ImageSource {
    public abstract void paint(Canvas canvas, int fromX, int fromY, int height, int width);
}
