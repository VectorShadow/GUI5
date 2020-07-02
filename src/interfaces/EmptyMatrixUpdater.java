package interfaces;

import images.ImageMatrix;

public class EmptyMatrixUpdater implements MatrixUpdater {
    @Override
    public ImageMatrix update(int imageHeight, int imageWidth, int matrixHeight, int matrixWidth) {
        return new ImageMatrix(imageHeight, imageWidth, matrixHeight, matrixWidth);
    }
}
