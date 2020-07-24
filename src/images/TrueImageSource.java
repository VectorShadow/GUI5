package images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        BufferedImage sourceImage = null;
        try {
            sourceImage = ImageIO.read(new File(renderer.getImageFilePath()));
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        //todo - apply image recoloring if desired
        return sourceImage;
    }

    @Override
    public Color getBackgroundColor() {
        throw new UnsupportedOperationException("TrueImageSources must be rendered to populate background color.");
    }

}
