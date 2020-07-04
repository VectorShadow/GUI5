package images;

import javax.swing.*;
import java.awt.*;

/**
 * Renderer provides the tools for transforming an ImageSource into a BufferedImage.
 * It contains a pre-sized JLabel for visualizing TextImageSources and String manipulation methods for locating
 * conventionally titled external image files for TrueImageSources.
 */
public class Renderer extends JLabel {
    //todo - external image file convention - extensions should indicate subimage size, e.g. "./images-24x24.png"

    private static String imageDirectoryPath = "./";

    private Color emptyColor = Color.BLACK;

    private JLabel jLabel;

    public Renderer(int imageHeight, int imageWidth) {
        setSize(new Dimension(imageWidth, imageHeight));
        setFont(new Font(Font.DIALOG, Font.PLAIN, (int)((double)imageHeight * 0.75)));
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(true);
    }

    //todo - this needs work, but the idea is to establish a convention and easily load conventionally named files
    public String getImageFilePath() {
        return imageDirectoryPath + "" + getWidth() + "x" + getHeight() + ".png";
    }

    public void setEmptyColor(Color color) {
        emptyColor = color;
    }

    public static void setImageDirectoryPath(String directoryPathName) {
        imageDirectoryPath = directoryPathName;
    }
}