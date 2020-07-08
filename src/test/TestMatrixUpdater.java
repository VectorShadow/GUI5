package test;

import images.ImageMatrix;
import images.ImageSource;
import images.TextImageSource;
import implementation.matrixupdater.MatrixUpdater;

import java.awt.*;

public class TestMatrixUpdater implements MatrixUpdater {
    @Override
    public ImageMatrix update(int imageHeight, int imageWidth, int matrixHeight, int matrixWidth, int layer) {
        ImageMatrix imageMatrix = new ImageMatrix(imageHeight, imageWidth, matrixHeight, matrixWidth);
        switch (layer) {
            case 0:
                break;
            case 1:
                ImageSource testSource = new TextImageSource(Color.RED, Color.LIGHT_GRAY, '!');
                imageMatrix.set(1, 1, testSource);
                imageMatrix.set(1, matrixWidth - 2, testSource);
                imageMatrix.set(matrixHeight - 2, 1, testSource);
                imageMatrix.set(matrixHeight - 2, matrixWidth - 2, testSource);
                imageMatrix.set(3, 7, new TextImageSource(Color.BLACK, Color.GREEN, 'T'));
                imageMatrix.set(3, 8, new TextImageSource(Color.BLACK, Color.GREEN, 'E'));
                imageMatrix.set(3, 9, new TextImageSource(Color.BLACK, Color.GREEN, 'S'));
                imageMatrix.set(3, 10, new TextImageSource(Color.BLACK, Color.GREEN, 'T'));
                Point p = TestMouseInputHandler.LAST_CLICK;
                if (p != null)
                    imageMatrix.set(p.y, p.x, new TextImageSource(Color.BLACK, Color.YELLOW, 'X'));
                break;
                default:
                    throw new IllegalArgumentException("This MatrixUpdater does not support layer " + layer);
        }
        return imageMatrix;
    }
}
