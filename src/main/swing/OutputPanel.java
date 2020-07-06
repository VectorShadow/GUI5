package main.swing;

import main.Gui;
import mis.MapwiseImageScaler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Panel on which the Canvas image is rendered.
 */
class OutputPanel extends JPanel {

    private BufferedImage originalImage;
    private BufferedImage scaledImage;

    public OutputPanel(boolean fullScreenMode) {
        if (fullScreenMode) {
            Dimension monitorDimension = Gui.getMonitorDimension();
            setPreferredSize(monitorDimension);
            setMinimumSize(monitorDimension);
            setMaximumSize(monitorDimension);
        } else {
            setPreferredSize(Gui.getLastWindowSize());
        }
        setSize(getPreferredSize());
        setOpaque(true);
        setVisible(true);
        setImage(
                new BufferedImage(
                        getWidth(),
                        getHeight(),
                        BufferedImage.TYPE_INT_ARGB
                )
        );
    }
    @Override
    protected void paintComponent(Graphics g) {//updates the displayed image.
        super.paintComponent(g);
        if (scaledImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(scaledImage, 0, 0, null);
        }
    }

    /**
     * Re-scale the source image whenever it is resized.
     */
    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        scaleImage();
        repaint();
    }

    /**
     * Set the image displayed on this panel after scaling it to fit.
     */
    public void setImage(BufferedImage bi) {
        originalImage = bi;
        scaleImage();
    }

    private void scaleImage() {
        if (originalImage != null)
            scaledImage = MapwiseImageScaler.scale(originalImage, getHeight(), getWidth());
    }
}
