package implementation.matrixupdater;

import images.ImageMatrix;

public class EmptyMatrixUpdater extends MatrixUpdater {

    public EmptyMatrixUpdater() {
        super(1);
    }

    @Override
    protected ImageMatrix doUpdate() {
        return LAYERS[currentLayer];
    }
}
