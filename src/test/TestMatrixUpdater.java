package test;

import images.ImageMatrix;
import images.ImageSource;
import images.TextImageSource;
import implementation.matrixupdater.MatrixUpdater;

import java.awt.*;

public class TestMatrixUpdater extends MatrixUpdater {

    @Override
    protected ImageMatrix doUpdate(int currentLayer) {
        ImageMatrix imageMatrix = ImageMatrix.emptyCopy(layers[currentLayer]);
        switch (currentLayer) {
            case 0:
                break;
            case 1:
                ImageSource testSource = new TextImageSource(Color.RED, Color.LIGHT_GRAY, '!');
                imageMatrix.set(1, 1, testSource);
                imageMatrix.set(1, imageMatrix.getMatrixWidth() - 2, testSource);
                imageMatrix.set(imageMatrix.getMatrixHeight() - 2, 1, testSource);
                imageMatrix.set(imageMatrix.getMatrixHeight() - 2, imageMatrix.getMatrixWidth() - 2, testSource);
                imageMatrix.set(3, 7, new TextImageSource(Color.BLACK, Color.GREEN, 'T'));
                imageMatrix.set(3, 8, new TextImageSource(Color.BLACK, Color.GREEN, 'E'));
                imageMatrix.set(3, 9, new TextImageSource(Color.BLACK, Color.GREEN, 'S'));
                imageMatrix.set(3, 10, new TextImageSource(Color.BLACK, Color.GREEN, 'T'));
                Point p = TestMouseInputHandler.LAST_CLICK;
                if (p != null)
                    imageMatrix.set(p.y, p.x, new TextImageSource(Color.BLACK, Color.YELLOW, 'X'));
                break;
        }
        return imageMatrix;
    }
}
