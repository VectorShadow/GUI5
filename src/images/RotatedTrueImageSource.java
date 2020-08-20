package images;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class RotatedTrueImageSource extends TrueImageSource {

    final double ROTATION;

    public RotatedTrueImageSource(int column, int row, double rotation) {
        super(column, row);
        ROTATION = rotation;
    }

    @Override
    public BufferedImage renderImage(Renderer renderer, int height, int width) {
        BufferedImage originalImage = super.renderImage(renderer, height, width);
        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(ROTATION, (double)width / 2.0, (double)height / 2.0);
        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return affineTransformOp.filter(originalImage, rotatedImage);
    }
}
