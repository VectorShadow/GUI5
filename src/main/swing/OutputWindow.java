package main.swing;

import main.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * The primary display window for the GUI.
 */
//todo - support manual window resizing - this needs to be supported by OutputPanel as well.
public class OutputWindow extends JFrame {

    private OutputPanel outputPanel = null;


    //todo - methods for setting all static fields:
    private static String iconImagePath = "";
    private static String frameTitle = "";

    private static KeyListener keyListener = null;
    private static MouseListener mouseListener = null;
    private static WindowListener windowListener = null;

    /**
     * Generate a new OutputWindow based on the provided fullScreenMode.
     */
    public OutputWindow(boolean fullScreenMode) {
        addComponentListener(
                new ComponentAdapter() {
                    public void componentResized(ComponentEvent e) {
                        /**
                         * When we resize this Window, we want to update the displayed image.
                         * In particular, we need to know the new contentPane size and resize the outputPanel to match.
                         * This will invoke the outputPanel's overridden setSize method which rescales the image
                         * and repaints the component.
                         */
                        Dimension target = getContentPane().getSize();
                        outputPanel.setPreferredSize(target);
                        outputPanel.setSize(target);
                        setContentPane(outputPanel);
                    }
                }
                );
        outputPanel = new OutputPanel(fullScreenMode);
        setContentPane(outputPanel);
        setUndecorated(fullScreenMode);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        if (keyListener != null) addKeyListener(keyListener);
        addMouseListener(mouseListener = new G5MouseListener());
        //todo - it might behoove us to do do the mouseListener entirely at the Gui level,
        // where it has access to MouseInputHandlers.
        if (windowListener != null) addWindowListener(windowListener);
        if (fullScreenMode) {
            setExtendedState(Frame.MAXIMIZED_BOTH);
        } else {
            setExtendedState(Frame.NORMAL);
            setLocation(centeredOrigin());
            if (!iconImagePath.equals(""))
                setIconImage(new ImageIcon(iconImagePath).getImage());
            setTitle(frameTitle);
        }
    }

    public void refresh(BufferedImage bufferedImage) {
        outputPanel.setImage(bufferedImage);
        outputPanel.repaint();
    }

    private Point centeredOrigin() {
        Dimension monitorDimension = Gui.getMonitorDimension();
        return new Point(
                ((int)(monitorDimension.getWidth() - getWidth())) / 2,
                ((int)(monitorDimension.getHeight() - getHeight())) / 2
        );
    }
}
