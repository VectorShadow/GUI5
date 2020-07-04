package main;

import images.ImageMatrix;
import implementation.matrixupdater.MatrixUpdater;
import implementation.paintinstructions.PaintInstruction;

import java.awt.*;

/**
 * A construct for storing, accessing, and updating information about multiple draw layers.
 */
public class LayerList {

    private final int IMAGE_HEIGHT, IMAGE_WIDTH, MATRIX_HEIGHT, MATRIX_WIDTH;

    private final MatrixUpdater MATRIX_UPDATER;
    private final PaintInstruction[] PAINT_INSTRUCTIONS;

    private ImageMatrix[] imageMatrices;

    public LayerList(
            int imageHeight, int imageWidth,
            int matrixHeight, int matrixWidth,
            MatrixUpdater matrixUpdater,
            PaintInstruction... paintInstructions
    ) {
        int layerCount = paintInstructions.length;
        IMAGE_HEIGHT = imageHeight;
        IMAGE_WIDTH = imageWidth;
        MATRIX_HEIGHT = matrixHeight;
        MATRIX_WIDTH = matrixWidth;
        MATRIX_UPDATER = matrixUpdater;
        PAINT_INSTRUCTIONS = paintInstructions;
        imageMatrices = new ImageMatrix[layerCount];
        for (int i = 0; i < layerCount; ++i)
            imageMatrices[i] = new ImageMatrix(imageHeight, imageWidth, matrixHeight, matrixWidth);
    }

    /**
     * Paint all layers to the provided canvas in order.
     */
    void paint(Canvas canvas, Point origin) {
        for (int i = 0; i < imageMatrices.length; ++i)
            imageMatrices[i].paint(canvas, origin.x, origin.y, PAINT_INSTRUCTIONS[i]);
    }

    /**
     * Use the MatrixUpdater to update the information in all draw layers.
     * We do this by creating a new array of ImageMatrices, then reassigning the pointer only once it is
     * completely built, to avoid concurrency issues between this method and paint().
     */
    void update() {
        int matrixCount = imageMatrices.length;
        ImageMatrix[] updatedMatrices = new ImageMatrix[matrixCount];
        for (int i = 0; i < matrixCount; ++i)
            updatedMatrices[i] = MATRIX_UPDATER.update(IMAGE_HEIGHT, IMAGE_WIDTH, MATRIX_HEIGHT, MATRIX_WIDTH, i);
        imageMatrices = updatedMatrices;
    }
}
