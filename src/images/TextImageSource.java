package images;

import main.Canvas;

import java.awt.*;

/**
 * Define an image source based on a symbol with foreground and background colors.
 */
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

    @Override
    public void paint(Canvas canvas, int fromX, int fromY, int height, int width) {
        //todo - generate an image based on the fields and paint it onto the canvas
    }
}
