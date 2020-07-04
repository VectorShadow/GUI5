package implementation.paintinstructions;

import main.Canvas;

import java.awt.image.BufferedImage;

/**
 * The default paint instruction. This is generally ideal for the base layer, but should not be used for higher
 * layers, as it will overwrite lower layers.
 */
public class DefaultPaintInstruction implements PaintInstruction {

    @Override
    public void paint(BufferedImage image, Canvas canvas, int fromX, int fromY, int height, int width) {
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++ col) {
                canvas.paint(fromY + row, fromX + col, image.getRGB(col, row));
            }
        }
    }
}
