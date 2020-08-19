package implementation.matrixupdater;

import images.ImageMatrix;

public class EmptyMatrixUpdater extends MatrixUpdater {

    @Override
    protected ImageMatrix doUpdate(int currentLayer) {
        return ImageMatrix.emptyCopy(layers[currentLayer]);
    }
}
