package images;

import implementation.paintinstructions.PaintInstruction;
import main.Canvas;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A matrix containing imageSources.
 */
public class ImageMatrix {
    private final int imageHeight;
    private final int imageWidth;
    private final int matrixHeight;
    private final int matrixWidth;

    private final ImageSource[][] matrix;
    private final Renderer renderer;

    public ImageMatrix(int iHt, int iWd, int mHt, int mWd) {
        imageHeight = iHt;
        imageWidth = iWd;
        matrixHeight = mHt;
        matrixWidth = mWd;
        matrix = new ImageSource[matrixHeight][matrixWidth];
        renderer = new Renderer(imageHeight, imageWidth);
    }

    public Color getBackgroundColorAt(int x, int y) {
        return matrix[y][x].getBackgroundColor();
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getMatrixHeight() {
        return matrixHeight;
    }

    public int getMatrixWidth() {
        return matrixWidth;
    }

    public void paint(Canvas canvas, int fromX, int fromY, PaintInstruction paintInstruction) {
        ImageSource imageSource;
        BufferedImage renderedImage;
        for (int row = 0; row < matrixHeight; ++row) {
            for (int col = 0; col < matrixWidth; ++col) {
                imageSource = matrix[row][col];
                renderedImage =
                        imageSource == null
                                ? null
                                : imageSource.renderImage(renderer, imageHeight, imageWidth);
                paintInstruction.paint(renderedImage, canvas, fromX + (col * imageWidth), fromY + (row * imageHeight), imageHeight, imageWidth);
            }
        }
    }

    public void set(int row, int column, ImageSource imageSource) {
        matrix[row][column] = imageSource;
    }
}
