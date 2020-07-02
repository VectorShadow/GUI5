package images;

import main.Canvas;

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
    public void paint(Canvas canvas, int fromX, int fromY, int height, int width) {
        //todo - handle setting sources - static field?
        //todo - use parameters to locate a subImage in the source to copy to a new Image.
    }
}
