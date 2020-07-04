package images;

import java.awt.image.BufferedImage;

/**
 * Define an imageSource based on coordinates in an external image file.
 */
public class TrueImageSource extends ImageSource {
    private final int sourceColumn;
    private final int sourceRow;

    public TrueImageSource(int column, int row) {
        sourceColumn = column;
        sourceRow = row;
    }

    @Override
    public BufferedImage renderImage(Renderer renderer, int height, int width) {
        //todo - use the Renderer to derive the source image file and use parameters to locate a subImage
        // to copy to a new Image.
        return null;
    }

}
