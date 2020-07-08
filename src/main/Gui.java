package main;

import main.swing.G5MouseListener;
import main.swing.OutputWindow;
import mis.MapwiseImageScaler;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * The primary object for handling display, updates, and other management tasks.
 */
public class Gui {

    //Remember the size of the frame and pane. On startup, default to half the size of the monitor.
    private static Dimension lastWindowSize = getDefaultWindowSize();

    private Canvas canvas = null;

    private int currentChannel = -1;

    private boolean fullScreenMode = false;

    private OutputWindow outputWindow = null;

    private ArrayList<OutputChannel> outputChannels = new ArrayList<>();

    Gui() {}

    public static Dimension getMonitorDimension() {
        DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        return new Dimension(dm.getWidth(), dm.getHeight());
    }

    public static Dimension getLastWindowSize() {
        return lastWindowSize;
    }

    private static Dimension getDefaultWindowSize() {
        return new Dimension(
                (int)(Gui.getMonitorDimension().width / Math.sqrt(2.0)),
                (int)(Gui.getMonitorDimension().height / Math.sqrt(2.0))
        );
    }

    /**
     * Add a new OutputChannel to this GUI.
     */
    void addOutputChannel() {
        outputChannels.add(new OutputChannel());
        if (currentChannel < 0) //if this is the first channel we are adding,
            currentChannel = 0; //set the current channel - otherwise do not change the current channel when adding
    }

    /**
     * Add a new Region to the current OutputChannel.
     */
    void addRegion(Region region) {
        requireChannel();
        OutputChannel oc = getChannel(currentChannel);
        oc.pushRegion(region);
    }

    /**
     * Generate a canvas with the specified height, width, and background rgbValue.
     */
    void generateCanvas(int h, int w, int rgb) {
        canvas = new Canvas(h, w, rgb);
    }

    /**
     * Clean up the old output window if extant, then generate a new one based on the current fullscreen mode.
     */
    void generateWindow() {
        if (outputWindow != null) {
            if (fullScreenMode)
                lastWindowSize = outputWindow.getSize(); //remember the current windowed mode size
            outputWindow.dispose();
        }
        outputWindow = new OutputWindow(fullScreenMode);
        outputWindow.addMouseListener(new G5MouseListener(this));
    }

    void addKeyListener(KeyListener keyListener) {
        requireWindow();
        outputWindow.addKeyListener(keyListener);
    }

    void addWindowListener(WindowListener windowListener) {
        requireWindow();
        outputWindow.addWindowListener(windowListener);
    }

    /**
     * Pass a mouseEvent from a G5MouseListener to a region specific MouseInputHandler.
     * @param mouseEvent the mouseEvent received by the listener
     */
    public void handleMouseInput(MouseEvent mouseEvent) {
        Point windowCoordinates = mouseEvent.getPoint();
        Point canvasCoordinates =
                MapwiseImageScaler.mapTargetToSource(
                        windowCoordinates,
                        canvas.HEIGHT,
                        canvas.WIDTH,
                        outputWindow.getPanelSize().height,
                        outputWindow.getPanelSize().width
                );
        Region region = getChannel().locate(canvasCoordinates);
        if (region != null)
            region.handleMouseInput(canvasCoordinates, mouseEvent);
    }

    /**
     * Redraw the window image by using the current channel to repaint the canvas, then refreshing the output window
     * with the resulting canvas image.
     */
    public void redraw() {
        requireCanvas();
        requireWindow();
        getChannel().paint(canvas);
        outputWindow.refresh(canvas.getImage());
    }

    public void setCurrentChannel(int channel) {
        currentChannel = channel;
    }

    /**
     * Toggle between full screen and windowed modes.
     */
    public void toggleFullScreenMode() {
        fullScreenMode = !fullScreenMode;
        generateWindow();
        redraw();
    }

    /**
     * Update all Regions in all OutputChannels.
     */
    public void update() {
        for (int i = 0; i < outputChannels.size(); ++i)
            update(i);
    }

    /**
     * Update all Regions in the OutputChannel corresponding to the specified index.
     */
    public void update(int channelIndex) {
        getChannel(channelIndex).update();
    }

    /**
     * Update the region corresponding to the specified regionIndex within the OutputChannel corresponding to the
     * specified channelIndex.
     */
    public void update(int channelIndex, int regionIndex) {
        getChannel(channelIndex).update(regionIndex);
    }

    private OutputChannel getChannel() {
        return getChannel(currentChannel);
    }
    private OutputChannel getChannel(int index) {
        return outputChannels.get(index);
    }

    private void requireCanvas() {
        if (canvas == null)
            throw new IllegalStateException("Size undefined - use .setSize() first");
    }

    private void requireChannel() {
        if (currentChannel < 0)
            throw new IllegalStateException("No OutputChannel found - use .addOutputChannel() first");
    }

    private void requireWindow() {
        if (outputWindow == null)
            throw new IllegalStateException("OutputWindow not found - use .generateWindow() first");
    }

}
