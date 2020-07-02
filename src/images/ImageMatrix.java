package images;

import main.Canvas;

/**
 * A matrix containing imageSources.
 */
public class ImageMatrix {
    private final int imageHeight;
    private final int imageWidth;
    private final int matrixHeight;
    private final int matrixWidth;

    private final ImageSource[][] matrix;

    public ImageMatrix(int iHt, int iWd, int mHt, int mWd) {
        imageHeight = iHt;
        imageWidth = iWd;
        matrixHeight = mHt;
        matrixWidth = mWd;
        matrix = new ImageSource[matrixHeight][matrixWidth];
        clear();
    }

    public void paint(Canvas canvas, int fromX, int fromY) {
        for (int row = 0; row < matrixHeight; ++row) {
            for (int col = 0; col < matrixWidth; ++col) {
                matrix[row][col].paint(
                        canvas,
                        fromX + row * imageHeight,
                        fromY + col * imageWidth,
                        imageHeight,
                        imageWidth
                );
            }
        }
    }

    public void set(int row, int column, ImageSource imageSource) {
        matrix[row][column] = imageSource;
    }

    public void clear() {
        for (int row = 0; row < matrixHeight; ++row) {
            for (int col = 0; col < matrixWidth; ++col) {
                set(row, col, EmptyImageSource.get());
            }
        }
    }

}
