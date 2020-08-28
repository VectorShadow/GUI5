package images;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Define an imageSource based on coordinates in an external image file.
 */
public class TrueImageSource extends ImageSource {
    protected final int sourceColumn;
    protected final int sourceRow;

    public TrueImageSource(int column, int row) {
        sourceColumn = column;
        sourceRow = row;
    }

    @Override
    public BufferedImage renderImage(Renderer renderer, int height, int width) {
        BufferedImage sourceImage = renderer.getSourceImage();
        return sourceImage.getSubimage(sourceColumn * width,sourceRow * height, width, height);
    }

    @Override
    public Color getBackgroundColor() {
        throw new UnsupportedOperationException("Non-recolored TrueImageSources must be rendered to populate background color.");
    }

}
