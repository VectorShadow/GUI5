package main;

import implementation.matrixupdater.MatrixUpdater;
import implementation.mouseinputhandler.MouseInputHandler;
import implementation.mouseinputhandler.NullMouseInputHandler;
import implementation.paintinstructions.PaintInstruction;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;

/**
 * Build a new Gui step by step.
 *
 * buildGui():
 * [REQUIRED]
 * Initiate the build for a new Gui.
 *
 * (The following methods are listed in recommended call order. Some orders are absolute - for example, setSize must
 * be called before addRegion, because the Gui requires a Canvas object before it can validate new Regions.)
 *
 * setSize(height, width):
 * [REQUIRED]
 * Specify the size of the Canvas object used to generate the displayed image. This corresponds to the size at which
 * the images are drawn - the display size is dynamic and dependant on window size and whether the Gui is in fullscreen
 * mode.
 *  -setSizeAndColor(height, width, rgbValue):
 *  [ALTERNATIVE]
 *  As above, and additionally specify the default background color of the image. This defaults to Black, and can be
 *  used by PaintInstructions to selectively override specific pixels as desired.
 *
 * addOutputChannel():
 * [REQUIRED(>0)]
 * Add a new OutputChannel to the Gui. OutputChannel indices correspond to the order in which they are added, beginning
 * with 0. Note that at least one OutputChannel must be added for a Gui to be functional.
 *
 * addRegion(...):
 * [RECOMMENDED(>0 per OutputChannel)]
 * Add a new Region to the most recently added OutputChannel. Region indices correspond to the order in which they are
 * added, beginning with 0. Failure to add Regions to a channel will not cause an error, but an OutputChannel must have
 * one or more Regions or it will display only a blank screen when selected.
 * This method takes a number of arguments. The first pair of ints correspond to the (x, y) coordinate specifying the
 * origin pixel of the region on the Canvas. This is the point from which the contents of the Region will be drawn.
 * The second pair of ints correspond to the height and width of an individual image tile within the region.
 * The final pair of ints correspond to the height and width of the region itself, in terms of tiles.
 * Gui will validate each added Region to ensure it fits within the Canvas. Beyond this, it does not attempt to
 * enforce any constraints on Region size or location. Gui layout and design is the responsibility of the end user.
 * The remaining arguments correspond to implementation specific overrides of required interfaces:
 *  MatrixUpdater:
 *  [REQUIRED]
 *  Specifies how the underlying ImageMatrices should be created when Gui.update() is called.
 *  MouseInputHandler:
 *  [OPTIONAL]
 *  Specifies how mouse input within the region should be handled.
 *  PaintInstruction:
 *  [REQUIRED(>0)]
 *  Specifies how each ImageMatrix layer should be interpreted and painted onto the Canvas.
 *  The number of PaintInstructions provided directly corresponds to the number of draw layers the Region has.
 *
 * addKeyListener(keyListener) and addWindowListener(windowListener):
 * [OPTIONAL]
 * Attach the provided listener to the Gui's swing window, allowing the user to read and handle keyboard input or
 * override window behavior as desired. These listeners persist for the lifetime of the Gui.
 *
 * build()
 * [REQUIRED]
 * Set the current channel to the first OutputChannel added(index 0), create and show the swing window, and start the
 * RedrawScheduler thread. Redrawing defaults to 50 frames per second, but can be specified by providing an int argument
 * corresponding to the desired frames per second, or a long argument specifying milliseconds between redraw calls -
 * for example: build(60) or build(25L).
 */
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
        gui.generateWindow();
        return this;
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

    public GuiBuilder addKeyListener(KeyListener keyListener) {
        gui.addKeyListener(keyListener);
        return this;
    }

    public GuiBuilder addWindowListener(WindowListener windowListener) {
        gui.addWindowListener(windowListener);
        return this;
    }

    /**
     * Finish building this Gui.
     */
    public Gui build() {
        return build(50);
    }

    /**
     * Finish building the Gui, scheduling redrawing at the specified frames per second.
     */
    public Gui build(int redrawFramesPerSecond) {
        return build((long)(1000 / redrawFramesPerSecond));
    }

    /**
     * Finish building the Gui, scheduling redrawing at the specified interval in milliseconds.
     */
    public Gui build(long redrawIntervalInMilliseconds) {
        gui.setCurrentChannel(0);
        new Thread(new RedrawScheduler(gui, redrawIntervalInMilliseconds)).start();
        return gui;
    }
}
