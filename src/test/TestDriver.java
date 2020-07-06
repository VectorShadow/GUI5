package test;

import implementation.matrixupdater.EmptyMatrixUpdater;
import implementation.paintinstructions.BaseLayerPaintInstruction;
import implementation.paintinstructions.DefaultPaintInstruction;
import main.Gui;
import main.Region;

public class TestDriver {

    public static void main(String[] args) throws InterruptedException {
        //todo - we should probably have a builder class to streamline the below:
        Gui gui = Gui.getInstance();
        gui.generateCanvas(256, 512);
        gui.addOutputChannel();
        gui.addRegion(
                new Region(
                        0,
                        0,
                        16,
                        16,
                        16,
                        32,
                        new TestMatrixUpdater(),
                        new BaseLayerPaintInstruction(),
                        new DefaultPaintInstruction())
        );
        gui.generateWindow();
        gui.update();
        gui.redraw();
        Thread.sleep(250);
        gui.toggleFullScreenMode();
        Thread.sleep(2500);
        gui.toggleFullScreenMode();
    }
}
