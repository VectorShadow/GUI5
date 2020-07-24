package implementation.matrixupdater;

import images.ImageMatrix;

/**
 * Implementation specific class for defining how to populate an ImageMatrix.
 * Implementations should make use of extensions to handle their specific needs - for example, a
 * text field should know how to wrap the text(or not) to fill the available space, while a game map screen
 * should know how to center itself and translate game objects to either image indices or tile graphics.
 */
public abstract class MatrixUpdater {
    protected int currentLayer = -1;
    protected final ImageMatrix[] LAYERS;

    public MatrixUpdater(int layerCount) {
        LAYERS = new ImageMatrix[layerCount];
    }

    public ImageMatrix update(int imageHeight, int imageWidth, int matrixHeight, int matrixWidth, int layer) {
        if (layer >= LAYERS.length)
            throw new IllegalArgumentException("Tried to update unsupported layer " +
                    layer + "; highest supported layer is " + (LAYERS.length - 1));
        currentLayer = layer;
        LAYERS[currentLayer] = new ImageMatrix(imageHeight, imageWidth, matrixHeight, matrixWidth);
        return doUpdate();
    }
    protected abstract ImageMatrix doUpdate();
}
