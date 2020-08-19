package implementation.matrixupdater;

import images.ImageMatrix;

/**
 * Implementation specific class for defining how to populate an ImageMatrix.
 * Implementations should make use of extensions to handle their specific needs - for example, a
 * text field should know how to wrap the text(or not) to fill the available space, while a game map screen
 * should know how to center itself and translate game objects to either image indices or tile graphics.
 */
public abstract class MatrixUpdater {
    protected ImageMatrix[] layers;

    public void setLayers(ImageMatrix[] imageMatrices) {
        layers = imageMatrices;
    }

    public void update() {
        for (int i = 0; i < layers.length; ++i)
            layers[i] = doUpdate(i);
    }
    protected abstract ImageMatrix doUpdate(int currentLayer);
}
