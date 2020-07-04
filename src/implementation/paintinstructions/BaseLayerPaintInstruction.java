package implementation.paintinstructions;

import main.Canvas;

import java.awt.image.BufferedImage;

/**
 * Apply the base RGB value to all pixels.
 */
public class BaseLayerPaintInstruction implements PaintInstruction {
    @Override
    public void paint(BufferedImage image, Canvas canvas, int fromX, int fromY, int height, int width) {
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++ col) {
                canvas.paint(fromY + row, fromX + col, canvas.getBaseRGBValue());
            }
        }
    }
}
