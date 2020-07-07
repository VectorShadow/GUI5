package test;

import implementation.paintinstructions.BaseLayerPaintInstruction;
import implementation.paintinstructions.DefaultPaintInstruction;
import main.Gui;
import main.GuiBuilder;

public class TestDriver {

    public static void main(String[] args) throws InterruptedException {
        Gui gui = GuiBuilder
                .buildGui()
                .setSize(256, 512)
                .addOutputChannel()
                .addRegion(
                        0, 0,
                        16, 16,
                        16, 32,
                        new TestMatrixUpdater(),
                        new BaseLayerPaintInstruction(),
                        new DefaultPaintInstruction()
                )
                .build();
        gui.update();
        gui.redraw();
        Thread.sleep(1500);
        gui.toggleFullScreenMode();
        Thread.sleep(1000);
        gui.toggleFullScreenMode();
    }
}
