package main.swing;

import main.Gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This MouseListener maintains a reference to the Gui it is associated with, translating each Overridden method to
 * one of the constants specified by the MouseInputHandler interface, then passing the triggering event and the constant
 * to the GUI to locate an appropriate MouseInputHandler to handle the event.
 */
public class G5MouseListener implements MouseListener {

    private final Gui GUI;

    public G5MouseListener(Gui gui) {
        GUI = gui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        GUI.handleMouseInput(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
