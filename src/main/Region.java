package main;

import images.ImageMatrix;
import interfaces.MatrixUpdater;
import interfaces.MouseInputHandler;
import interfaces.NullMouseInputHandler;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A canvas region with custom origin, size, and units. Regions are a low level abstraction used for defining
 * input/output space. Higher level functions, such as formatting text, should be handled elsewhere.
 * Specific region types, such as for image tiles or text tiles, should extend this class.
 */
public abstract class Region {
    final Point ORIGIN;
    final int UNIT_HEIGHT;
    final int UNIT_WIDTH;
    final int HEIGHT_IN_UNITS;
    final int WIDTH_IN_UNITS;
    final MatrixUpdater MATRIX_UPDATER;
    final MouseInputHandler MOUSE_INPUT_HANDLER;

    ImageMatrix imageMatrix;

    /**
     * This region need not support mouse input.
     */
    public Region(
            int originRow, int originColumn,
            int unitHeight, int unitWidth,
            int heightInUnits, int widthInUnits,
            MatrixUpdater matrixUpdater
    ) {
        this(
                originRow, originColumn,
                unitHeight, unitWidth,
                heightInUnits, widthInUnits,
                matrixUpdater, new NullMouseInputHandler()
        );
    }

    public Region(
            int originRow, int originColumn,
            int unitHeight, int unitWidth,
            int heightInUnits, int widthInUnits,
            MatrixUpdater matrixUpdater,
            MouseInputHandler mouseInputHandler
    ) {
        ORIGIN = new Point(originColumn, originRow);
        UNIT_HEIGHT = unitHeight;
        UNIT_WIDTH = unitWidth;
        HEIGHT_IN_UNITS = heightInUnits;
        WIDTH_IN_UNITS = widthInUnits;
        MATRIX_UPDATER = matrixUpdater;
        MOUSE_INPUT_HANDLER = mouseInputHandler;
        updateImageMatrix();
    }

    /**
     * @param externalIndex a Point specifying the x,y coordinates of a pixel in the external coordinate system.
     * @return whether this Region contains the specified external coordinates.
     */
    public boolean contains(Point externalIndex) {
        Point internalIndex = toInternal(externalIndex);
        int x = internalIndex.x;
        int y = internalIndex.y;
        return (x >= 0 && (x < WIDTH_IN_UNITS * UNIT_WIDTH) &&
                y >= 0 && (y < HEIGHT_IN_UNITS * UNIT_HEIGHT));
    }

    /**
     * Find the internal indices of the unit corresponding to the provided external pixel indices.
     * @param externalIndex a Point specifying the x,y coordinates of a pixel in the external coordinate system.
     * @return a Point specifying the x,y coordinates of a unit tile in internal unit coordinates, or null if the
     * specified external index is not within this Region.
     */
    public Point unitIndex(Point externalIndex) {
        if (!contains(externalIndex)) return null;
        Point internalIndex = toInternal(externalIndex);
        return new Point(internalIndex.x / UNIT_WIDTH, internalIndex.y / UNIT_HEIGHT);
    }

    /**
     * @return the size in units of this Region as a Point(Width, Height)
     */
    public Point size() {
        return new Point(WIDTH_IN_UNITS, HEIGHT_IN_UNITS);
    }

    /**
     * Handle a mouseEvent occurring at the given external index,
     * based on the specifications of this region's MouseInputHandler.
     */
    void handleMouseInput(Point externalIndex, MouseEvent mouseEvent) {
        Point unitIndex = unitIndex(externalIndex);
        MOUSE_INPUT_HANDLER.handleClickAt(unitIndex.x, unitIndex.y, mouseEvent);
    }

    /**
     * Use the image matrix to paint this region's content to the canvas.
     * @param canvas
     */
    void paint(Canvas canvas) {
        imageMatrix.paint(canvas, ORIGIN.x, ORIGIN.y);
    }

    /**
     * Generate a new imageMatrix based on the specifications of this region's MatrixUpdater.
     * This allows the source images to be updated without interfering with painting operations involving this Region's
     * existing ImageMatrix.
     */
    void updateImageMatrix() {
        imageMatrix = MATRIX_UPDATER.update(UNIT_HEIGHT, UNIT_WIDTH, HEIGHT_IN_UNITS, WIDTH_IN_UNITS);
    }

    /**
     * Convert an external index to an internal index(by subtracting the origin).
     * @param externalIndex a Point specifying the x,y coordinates of a pixel in the external coordinate system.
     * @return the internal index of the specified external index.
     */
    private Point toInternal(Point externalIndex) {
        return new Point(externalIndex.x - ORIGIN.x, externalIndex.y - ORIGIN.y);
    }
}
