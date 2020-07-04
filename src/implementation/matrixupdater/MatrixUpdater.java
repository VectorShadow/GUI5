package implementation.matrixupdater;

import images.ImageMatrix;

/**
 * Implementation specific interface for defining how to populate an ImageMatrix.
 * Implementations should make use of this interface to handle their specific needs - for example, a
 * text field should know how to wrap the text(or not) to fill the available space, while a game map screen
 * should know how to center itself and translate game objects to either image indices or tile graphics.
 */
public interface MatrixUpdater {
    ImageMatrix update(int imageHeight, int imageWidth, int matrixHeight, int matrixWidth, int layer);
}
