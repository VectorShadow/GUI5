package implementation.mouseinputhandler;

import java.awt.event.MouseEvent;

public class NullMouseInputHandler implements MouseInputHandler {

    @Override
    public void handleEventAt(int column, int row, MouseEvent mouseEvent) {}
}
