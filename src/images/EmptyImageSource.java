package images;

import main.Canvas;

import java.awt.*;

/**
 * Define an image source for when no source is needed - an empty image.
 * The default color can be set via the single static method.
 */
public class EmptyImageSource extends ImageSource {

    private static Color emptyColor = Color.BLACK;

    private static final EmptyImageSource EMPTY_IMAGE_SOURCE = new EmptyImageSource();

    public static void setEmptyColor(Color color) {
        emptyColor = color;
    }

    public static EmptyImageSource get() {
        return EMPTY_IMAGE_SOURCE;
    }

    private EmptyImageSource(){}

    @Override
    public void paint(Canvas canvas, int fromX, int fromY, int height, int width) {
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                canvas.paint(fromY + row, fromX + col, emptyColor.getRGB());
            }
        }
    }
}
