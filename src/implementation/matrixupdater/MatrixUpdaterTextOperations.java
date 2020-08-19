package implementation.matrixupdater;

import images.ImageMatrix;
import images.TextImageSource;

import java.awt.*;

public class MatrixUpdaterTextOperations {

    public static final int OUT_OF_BOUNDS = -1;

    /**
     * Write a line to the specified matrix at the given row and start column.
     * @return the column immediately after the last character of the line, or -1 if out of bounds.
     */
    public static int writeLine(ImageMatrix imageMatrix, int row, int startColumn, String line, Color background, Color foreground) {
        for (int i = 0; i < line.length(); ++i) {
            if (startColumn + i >= imageMatrix.getMatrixWidth()) return OUT_OF_BOUNDS;
            imageMatrix.set(row, startColumn + i, new TextImageSource(background, foreground, line.charAt(i)));
        }
        int endColumn = startColumn + line.length();
        return endColumn >= imageMatrix.getMatrixWidth() ? OUT_OF_BOUNDS : endColumn;
    }
}
