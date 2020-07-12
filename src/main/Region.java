package main;

import implementation.matrixupdater.MatrixUpdater;
import implementation.mouseinputhandler.MouseInputHandler;
import implementation.paintinstructions.PaintInstruction;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A canvas region with custom origin, size, and units. Regions are a low level abstraction used for defining
 * input/output space. Higher level functions, such as formatting text, should be handled elsewhere.
 * Specific region types, such as for image tiles or text tiles, should extend this class.
 */
class Region {
    final Point ORIGIN;
    final int UNIT_HEIGHT;
    final int UNIT_WIDTH;
    final int HEIGHT_IN_UNITS;
    final int WIDTH_IN_UNITS;
    private final MouseInputHandler MOUSE_INPUT_HANDLER;

    private final LayerList LAYER_LIST;

    Region(
            Point origin,
            int unitHeight, int unitWidth,
            int heightInUnits, int widthInUnits,
            MatrixUpdater matrixUpdater,
            MouseInputHandler mouseInputHandler,
            PaintInstruction... paintInstructions
    ) {
        ORIGIN = origin;
        UNIT_HEIGHT = unitHeight;
        UNIT_WIDTH = unitWidth;
        HEIGHT_IN_UNITS = heightInUnits;
        WIDTH_IN_UNITS = widthInUnits;
        LAYER_LIST = new LayerList(
                UNIT_HEIGHT, UNIT_WIDTH,
                HEIGHT_IN_UNITS, WIDTH_IN_UNITS,
                matrixUpdater, paintInstructions
        );
        MOUSE_INPUT_HANDLER = mouseInputHandler;
    }

    /**
     * @param externalIndex a Point specifying the x,y coordinates of a pixel in the external coordinate system.
     * @return whether this Region contains the specified external coordinates.
     */
    boolean contains(Point externalIndex) {
        Point internalIndex = toInternal(externalIndex);
        int x = internalIndex.x;
        int y = internalIndex.y;
        return (x >= 0 && (x < WIDTH_IN_UNITS * UNIT_WIDTH) &&
                y >= 0 && (y < HEIGHT_IN_UNITS * UNIT_HEIGHT));
    }

    /**
     * Handle a mouseEvent occurring at the given external index,
     * based on the specifications of this region's MouseInputHandler.
     */
    void handleMouseInput(Point externalIndex, MouseEvent mouseEvent) {
        Point unitIndex = unitIndex(externalIndex);
        if (unitIndex == null)
            throw new IllegalArgumentException("Attempted to handle mouse input for an event outside the Region.");
        MOUSE_INPUT_HANDLER.handleEventAt(unitIndex.x, unitIndex.y, mouseEvent);
    }

    /**
     * Use the image matrix to paint this region's content to the canvas.
     */
    void paint(Canvas canvas) {
        LAYER_LIST.paint(canvas, ORIGIN);
    }

    /**
     * @return the size in units of this Region as a Point(Width, Height)
     */
    Point size() {
        return new Point(WIDTH_IN_UNITS, HEIGHT_IN_UNITS);
    }

    /**
     * Update all draw layers for this region.
     */
    void update() {
        LAYER_LIST.update();
    }

    /**
     * Update the specified draw layer for this region.
     */
    void update(int layerIndex) {
        LAYER_LIST.update(layerIndex);
    }

    /**
     * Convert an external index to an internal index(by subtracting the origin).
     * @param externalIndex a Point specifying the x,y coordinates of a pixel in the external coordinate system.
     * @return the internal index of the specified external index.
     */
    private Point toInternal(Point externalIndex) {
        return new Point(externalIndex.x - ORIGIN.x, externalIndex.y - ORIGIN.y);
    }

    /**
     * Find the internal indices of the unit corresponding to the provided external pixel indices.
     * @param externalIndex a Point specifying the x,y coordinates of a pixel in the external coordinate system.
     * @return a Point specifying the x,y coordinates of a unit tile in internal unit coordinates, or null if the
     * specified external index is not within this Region.
     */
    private Point unitIndex(Point externalIndex) {
        if (!contains(externalIndex)) return null;
        Point internalIndex = toInternal(externalIndex);
        return new Point(internalIndex.x / UNIT_WIDTH, internalIndex.y / UNIT_HEIGHT);
    }
}
