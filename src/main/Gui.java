package main;

import main.swing.G5MouseListener;
import main.swing.OutputWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * The primary object for handling display, updates, and other management tasks.
 * Guis should be constructed via the GuiBuilder class, and interacted with via this class.
 *
 * setCurrentChannel(int channel):
 * This method sets the current channel used by redraw() to the OutputChannel corresponding to the specified channel
 * index. OutputChannel indices correspond to the order in which channels are added via GuiBuilder, beginning with 0.
 *
 * toggleFullScreenMode():
 * This method switches the Gui between windowed and full screen modes. It does this by generating a new swing frame
 * and repopulating it with all relevant data from the current frame, including listeners.
 *
 * update():
 * These methods update the ImageMatrices which provide the source images for redrawing the image on the swing frame.
 * Called without arguments, it updates, in sequence, each layer within each region within each channel.
 * It can be narrowed in scope by providing up to three int parameters. The first corresponds to a single specific
 * channel, the second to a single specific region within that channel, and the third to a single specific layer
 * within that region.
 */
public class Gui {

    //Remember the size of the frame and pane. On startup, default to half the size of the monitor.
    private static Dimension lastWindowSize = getDefaultWindowSize();

    private Canvas canvas = null;

    private int currentChannel = -1;

    private boolean fullScreenMode = false;

    private ArrayList<OutputChannel> outputChannels = new ArrayList<>();

    private OutputWindow outputWindow = null;

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
        currentChannel = outputChannels.size() - 1; //set the current channel to the one we just added.
    }

    /**
     * Add a new Region to the current OutputChannel.
     */
    void addRegion(Region region) {
        requireChannel();
        validateRegion(region);
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

    void setTitle(String title) {
        requireWindow();
        outputWindow.setFrameTitle(title);
    }

    /**
     * Pass a mouseEvent from a G5MouseListener to a region specific MouseInputHandler.
     * @param mouseEvent the mouseEvent received by the listener
     */
    public void handleMouseInput(MouseEvent mouseEvent) {
        Point windowCoordinates = mouseEvent.getPoint();
        double heightRatio = (double)canvas.HEIGHT / outputWindow.getPanelSize().getHeight();
        double widthRatio = (double)canvas.WIDTH / outputWindow.getPanelSize().getWidth();
        int canvasX = (int)Math.floor((double)windowCoordinates.x * widthRatio);
        int canvasY = (int)Math.floor((double)windowCoordinates.y * heightRatio);
        Point canvasCoordinates = new Point(canvasX, canvasY);
        Region region = getChannel().locate(canvasCoordinates);
        if (region != null)
            region.handleMouseInput(canvasCoordinates, mouseEvent);
    }

    /**
     * Redraw the window image by using the current channel to repaint the canvas, then refreshing the output window
     * with the resulting canvas image.
     */
    void redraw() {
        requireCanvas();
        requireWindow();
        canvas.clear();
        getChannel().paint(canvas);
        outputWindow.refresh(canvas.getImage());
    }

    /**
     * Set the channel used for drawing the screen to the OutputChannel corresponding to the specified index.
     */
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
        requireChannel();
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

    private void validateRegion(Region region) {
        requireCanvas();
        int x = region.ORIGIN.x;
        int y = region.ORIGIN.y;
        int X = x + (region.WIDTH_IN_UNITS * region.UNIT_WIDTH);
        int Y = y + (region.HEIGHT_IN_UNITS * region.UNIT_HEIGHT);
        if (x < 0)
            throw new IllegalStateException("Invalid region origin - x(" + x + ") < 0.");
        if (y < 0)
            throw new IllegalStateException("Invalid region origin - y(" + y + ") < 0.");
        if (X > canvas.WIDTH)
            throw new IllegalStateException("Invalid region size - X(" + X + ") > " + canvas.WIDTH);
        if (Y > canvas.HEIGHT)
            throw new IllegalStateException("Invalid region size - Y(" + Y + ") > " + canvas.HEIGHT);
    }

}
