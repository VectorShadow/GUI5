package interfaces;

import java.awt.event.MouseEvent;

/**
 * Implementation specific interface for handling specific mouse input.
 * Implementations should make use of this interface to handle their specific needs - for example, an inventory
 * screen might attempt to equip or consume an item stored at the click location, while a game map screen might
 * generate a movement or attack or examine command.
 */
public interface MouseInputHandler {
    void handleClickAt(int column, int row, MouseEvent mouseEvent);
}
