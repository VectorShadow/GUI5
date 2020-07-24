package images;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Renderer provides the tools for transforming an ImageSource into a BufferedImage.
 * It contains a pre-sized JLabel for visualizing TextImageSources and String manipulation methods for locating
 * conventionally titled external image files for TrueImageSources.
 */
public class Renderer extends JLabel {

    private static BufferedImage SOURCE_IMAGE = null;

    private static String imageDirectoryPath = "./gfx";

    Renderer(int imageHeight, int imageWidth) {
        setSize(new Dimension(imageWidth, imageHeight));
        setFont(new Font(Font.DIALOG, Font.PLAIN, (int)((double)imageHeight * 0.75)));
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(true);
    }

    public BufferedImage getSourceImage() {
        try {
            return SOURCE_IMAGE == null ? SOURCE_IMAGE = ImageIO.read(new File(getImageFilePath())) : SOURCE_IMAGE;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    //todo - this needs work, but the idea is to establish a convention and easily load conventionally named files
    private String getImageFilePath() {
        return imageDirectoryPath + "/" + getWidth() + "x" + getHeight() + ".png";
    }

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
