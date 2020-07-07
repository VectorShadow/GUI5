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

    public static GuiBuilder buildGui() {
        return new GuiBuilder();
    }

    public GuiBuilder addOutputChannel() {
        gui.addOutputChannel();
        return this;
    }
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

    public Gui build() {
        return build(true);
    }

    public Gui build(boolean showScreen) {
        gui.setCurrentChannel(0);
        if (showScreen)
            gui.generateWindow();
        return gui;
    }

    public GuiBuilder setSize(int height, int width) {
        return setSize(0, height, width);
    }

    public GuiBuilder setSize(int rgbValue, int height, int width) {
        gui.generateCanvas(rgbValue, height, width);
        return this;
    }
}
