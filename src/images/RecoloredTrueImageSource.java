package images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RecoloredTrueImageSource extends TrueImageSource {

    public static final int SOURCE_TRANSPARENCY_COLOR = new Color(0, 0, 0).getRGB();
    public static final int SOURCE_PRIMARY_COLOR = new Color(255, 0, 0).getRGB();
    public static final int SOURCE_SECONDARY_COLOR = new Color(0, 255, 0).getRGB();
    public static final int SOURCE_TERTIARY_COLOR = new Color(0, 0, 255).getRGB();

    private final int RECOLOR_TRANSPARENCY;
    private final int RECOLOR_PRIMARY;
    private final int RECOLOR_SECONDARY;
    private final int RECOLOR_TERTIARY;

    public RecoloredTrueImageSource(int column, int row, Color... recoloration) {
        super(column, row);
        RECOLOR_TRANSPARENCY = recoloration.length > 0 ? recoloration[0].getRGB() : 0;
        RECOLOR_PRIMARY = recoloration.length > 1 ? recoloration[1].getRGB() : 0;
        RECOLOR_SECONDARY = recoloration.length > 2 ? recoloration[2].getRGB() : 0;
        RECOLOR_TERTIARY = recoloration.length > 3 ? recoloration[3].getRGB() : 0;
    }

    @Override
    public BufferedImage renderImage(Renderer renderer, int height, int width) {
        BufferedImage sourceSubImage = super.renderImage(renderer, height, width);
        int sourceRGB;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                sourceRGB = sourceSubImage.getRGB(x, y);
                if (RECOLOR_TRANSPARENCY < 0 && sourceRGB == SOURCE_TRANSPARENCY_COLOR)
                    sourceSubImage.setRGB(x, y, RECOLOR_TRANSPARENCY);
                else if (RECOLOR_PRIMARY < 0 && sourceRGB == SOURCE_PRIMARY_COLOR)
                    sourceSubImage.setRGB(x, y, RECOLOR_PRIMARY);
                else if (RECOLOR_SECONDARY < 0 && sourceRGB == SOURCE_SECONDARY_COLOR)
                    sourceSubImage.setRGB(x, y, RECOLOR_SECONDARY);
                else if (RECOLOR_TERTIARY < 0 && sourceRGB == SOURCE_TERTIARY_COLOR)
                    sourceSubImage.setRGB(x, y, RECOLOR_TERTIARY);
                //else no re-coloration is required for this pixel
            }
        }
        return sourceSubImage;
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
