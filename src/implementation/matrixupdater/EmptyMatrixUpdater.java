package implementation.matrixupdater;

import images.ImageMatrix;

public class EmptyMatrixUpdater implements MatrixUpdater {
    @Override
    public ImageMatrix update(int imageHeight, int imageWidth, int matrixHeight, int matrixWidth, int layer) {
        return new ImageMatrix(imageHeight, imageWidth, matrixHeight, matrixWidth);
    }
}
