package test;

import implementation.mouseinputhandler.MouseInputHandler;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TestMouseInputHandler implements MouseInputHandler {

    static Point LAST_CLICK = null;

    @Override
    public void handleEventAt(int column, int row, MouseEvent mouseEvent) {
        LAST_CLICK = new Point(column, row);
        TestDriver.gui.update(0, 0);
    }
}
