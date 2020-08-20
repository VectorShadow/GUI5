package images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Specifies the parameters required to draw an image.
 */
public abstract class ImageSource {


    public static String imageDirectoryPath = "./gfx";

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

    public static void setImageDirectoryPath(String directoryPathName) {
        imageDirectoryPath = directoryPathName;
        if (!Files.exists(Paths.get(imageDirectoryPath))){
            try {
                Files.createDirectory(Paths.get(imageDirectoryPath));
            } catch (IOException e) {
                throw new IllegalArgumentException("Unable to create directory matching " + directoryPathName);
            }
        }
    }
}
