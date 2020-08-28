package images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RecoloredTrueImageSource extends TrueImageSource {

    public static final int SOURCE_TRANSPARENCY_COLOR = new Color(0, 0, 0).getRGB();
    public static final int SOURCE_PRIMARY_COLOR = new Color(255, 0, 0).getRGB();
    public static final int SOURCE_SECONDARY_COLOR = new Color(0, 255, 0).getRGB();
    public static final int SOURCE_TERTIARY_COLOR = new Color(0, 0, 255).getRGB();

    private final Integer RECOLOR_TRANSPARENCY;
    private final Integer RECOLOR_PRIMARY;
    private final Integer RECOLOR_SECONDARY;
    private final Integer RECOLOR_TERTIARY;

    public RecoloredTrueImageSource(int column, int row, Color... recoloration) {
        super(column, row);
        RECOLOR_TRANSPARENCY = recoloration.length > 0 ? recoloration[0].getRGB() : null;
        RECOLOR_PRIMARY = recoloration.length > 1 ? recoloration[1].getRGB() : null;
        RECOLOR_SECONDARY = recoloration.length > 2 ? recoloration[2].getRGB() : null;
        RECOLOR_TERTIARY = recoloration.length > 3 ? recoloration[3].getRGB() : null;
    }

    /**
     * Apply a re-coloration to an existing TrueImageSource.
     */
    public RecoloredTrueImageSource(TrueImageSource original, Color... recoloration) {
        this(original.sourceColumn, original.sourceRow, recoloration);
    }

    @Override
    public BufferedImage renderImage(Renderer renderer, int height, int width) {
        BufferedImage sourceSubImage = super.renderImage(renderer, height, width);
        BufferedImage recoloredSubImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int sourceRGB;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                sourceRGB = sourceSubImage.getRGB(x, y);
                if (RECOLOR_TRANSPARENCY != null && sourceRGB == SOURCE_TRANSPARENCY_COLOR)
                    recoloredSubImage.setRGB(x, y, RECOLOR_TRANSPARENCY);
                else if (RECOLOR_PRIMARY != null && sourceRGB == SOURCE_PRIMARY_COLOR)
                    recoloredSubImage.setRGB(x, y, RECOLOR_PRIMARY);
                else if (RECOLOR_SECONDARY != null && sourceRGB == SOURCE_SECONDARY_COLOR)
                    recoloredSubImage.setRGB(x, y, RECOLOR_SECONDARY);
                else if (RECOLOR_TERTIARY != null && sourceRGB == SOURCE_TERTIARY_COLOR)
                    recoloredSubImage.setRGB(x, y, RECOLOR_TERTIARY);
                else
                    recoloredSubImage.setRGB(x, y, sourceRGB);
            }
        }
        return recoloredSubImage;
    }

    @Override
    public Color getBackgroundColor() {
        if (RECOLOR_SECONDARY < 0)
            return new Color(RECOLOR_SECONDARY);
        if (RECOLOR_PRIMARY < 0)
            return new Color(RECOLOR_PRIMARY);
        throw new UnsupportedOperationException("Background color not supported in this re-coloration scheme: [" +
                RECOLOR_TRANSPARENCY + "/" + RECOLOR_PRIMARY + "/" + RECOLOR_SECONDARY + "/" + RECOLOR_TERTIARY + "]");
    }
}
