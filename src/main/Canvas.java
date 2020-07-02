package main;

import java.awt.image.BufferedImage;

/**
 * The base layer to which all graphics are drawn.
 */
public class Canvas {
    final int HEIGHT;
    final int WIDTH;

    private final BufferedImage IMAGE;

    public Canvas(int h, int w) {
        HEIGHT = h;
        WIDTH = w;
        IMAGE = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public void paint(int pixelRow, int pixelCol, int rgbValue) {
        //debug:
//        if (pixelRow < 0 || pixelRow >= HEIGHT)
//            throw new IllegalArgumentException("pixelRow (" + pixelRow + ") out of bounds: 0 <= [value] < " + HEIGHT);
//        if (pixelCol < 0 || pixelCol >= WIDTH)
//            throw new IllegalArgumentException("pixelCol (" + pixelCol + ") out of bounds: 0 <= [value] < " + WIDTH);
        IMAGE.setRGB(pixelCol, pixelRow, rgbValue);
    }
    public void clear(){
        clear(0);
    }
    public void clear(int rgbValue) {
        for (int h = 0; h < HEIGHT; ++h) {
            for (int w = 0; w < WIDTH; ++w) {
                paint(h, w, rgbValue);
            }
        }
    }

    public BufferedImage getImage() {
        return IMAGE;
    }
}
