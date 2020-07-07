package main;

import implementation.matrixupdater.MatrixUpdater;
import implementation.mouseinputhandler.MouseInputHandler;
import implementation.mouseinputhandler.NullMouseInputHandler;
import implementation.paintinstructions.PaintInstruction;

import java.awt.*;

public class GuiBuilder {

    public Gui gui;

    private GuiBuilder() {
        gui = new Gui();
    }

    /**
     * Begin building a new Gui.
     */
    public static GuiBuilder buildGui() {
        return new GuiBuilder();
    }

    /**
     * Add a new OutputChannel to the Gui being built.
     */
    public GuiBuilder addOutputChannel() {
        gui.addOutputChannel();
        return this;
    }

    /**
     * Add a region which does not require mouse input handling.
     */
    public GuiBuilder addRegion(
            int originX, int originY,
            int unitHeight, int unitWidth,
            int regionHeight, int regionWidth,
            MatrixUpdater matrixUpdater,
            PaintInstruction... paintInstructions
    ) {
        return addRegion(
                originX, originY,
                unitHeight, unitWidth,
                regionHeight, regionWidth,
                matrixUpdater,
                new NullMouseInputHandler(),
                paintInstructions);
    }
    /**
     * Add a new Region to the most recently added OutputChannel.
     * @param originX the X coordinate in pixels at which this region begins on the canvas.
     * @param originY the Y coordinate in pixels at which this region begins on the canvas.
     * @param unitHeight the height in pixels of each unit of this region.
     * @param unitWidth the width in pixels of each unit of this region.
     * @param regionHeight the height of this region in units.
     * @param regionWidth the width of this region in units.
     * @param matrixUpdater the implementation specific matrixUpdater for writing to the region's imageMatrix.
     * @param mouseInputHandler the implementation specific mouseInputHandler for handling mouse input within this
*                               region.
     * @param paintInstructions the implementation specific paintInstructions for drawing this region's imageMatrices
     *                          to the canvas.
     * @return
     */
    public GuiBuilder addRegion(
            int originX, int originY,
            int unitHeight, int unitWidth,
            int regionHeight, int regionWidth,
            MatrixUpdater matrixUpdater,
            MouseInputHandler mouseInputHandler,
            PaintInstruction... paintInstructions
    ) {
        gui.addRegion(
                new Region(
                        new Point(originX, originY),
                        unitHeight, unitWidth,
                        regionHeight, regionWidth,
                        matrixUpdater,
                        mouseInputHandler,
                        paintInstructions
                )
        );
        return this;
    }

    /**
     * Finish building this Gui.
     */
    public Gui build() {
        return build(true);
    }

    /**
     * Finish building this Gui.
     * Specify false to postpone generating and displaying the OutputWindow.
     * This can be done later as desired with .generateWindow().
     * Note that this must be done prior to any calls to redraw().
     */
    public Gui build(boolean displayWindow) {
        gui.setCurrentChannel(0);
        if (displayWindow)
            gui.generateWindow();
        return gui;
    }

    /**
     * Set the size of the Canvas used for painting by this Gui.
     * @param height in pixels
     * @param width in pixels
     */
    public GuiBuilder setSize(int height, int width) {
        return setSizeAndColor(height, width, 0);
    }

    /**
     * Set the size of the Canvas used for painting by this Gui.
     * @param height in pixels
     * @param width in pixels
     * @param rgbValue the rgbValue of the default background color for this Gui.
     */
    public GuiBuilder setSizeAndColor(int height, int width, int rgbValue) {
        gui.generateCanvas(height, width, rgbValue);
        return this;
    }
}
