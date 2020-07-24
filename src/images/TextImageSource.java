package images;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Define an image source based on a symbol with foreground and background colors.
 */
//todo - solve the rendering problem!
public class TextImageSource extends ImageSource {

    private final Color background;
    private final Color foreground;
    private final char symbol;

    public TextImageSource(char sym) {
        this(Color.WHITE, sym);
    }
    public TextImageSource(Color fg, char sym) {
        this(Color.BLACK, fg, sym);
    }
    public TextImageSource(Color bg, Color fg, char sym) {
        background = bg;
        foreground = fg;
        symbol = sym;
    }

    /**
     * Use the provided renderer to set colors and symbol and derive an image from it.
     */
    public BufferedImage renderImage(Renderer renderer, int height, int width) {
        renderer.setBackground(background);
        renderer.setForeground(foreground);
        renderer.setText("" + symbol);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        renderer.paint(bi.getGraphics());
        return bi;
    }

}
