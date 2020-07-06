package main.swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//todo - lots of work here probably: we need to find where the mouse is, translate that to regional coordinates,
// and report those coordinates along with the mouseEvent to the appropriate MouseInputHandler
public class G5MouseListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        //todo - we need the getX and getY values
        System.out.println("x: " + e.getX() + " y: " + e.getY() + " xOnScreen: " + e.getXOnScreen() + " yOnScreen: " +e.getYOnScreen());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
