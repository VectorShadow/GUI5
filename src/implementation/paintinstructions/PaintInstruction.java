package implementation.paintinstructions;

import main.Canvas;

import java.awt.image.BufferedImage;

/**
 * Specific guidance on how to transfer data from an existing image over to a new image.
 * Designed for drawing in layers.
 */
public interface PaintInstruction {
    /**
     * Render and paint an image of the specified dimensions based on this source to the canvas at the specified
     * origin coordinates.
     * @param image the image to be painted
     * @param canvas the Canvas on which to paint the image.
     * @param fromX the X coordinate of the Canvas at which to start painting.
     * @param fromY the Y coordinate of the Canvas at which to start painting.
     * @param height the height of the image to be loaded or generated.
     * @param width the width of the image to be loaded or generated.
     * @param baseRGB the RGB value of the base layer.
     */
    void paint(BufferedImage image, Canvas canvas, int fromX, int fromY, int height, int width);
}
