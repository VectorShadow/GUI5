package interfaces;

import java.awt.event.MouseEvent;

public class NullMouseInputHandler implements MouseInputHandler {
    @Override
    public void handleClickAt(int column, int row, MouseEvent mouseEvent) {}
}
