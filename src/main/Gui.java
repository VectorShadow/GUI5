package main;

import java.util.ArrayList;

/**
 * The primary object for handling display, updates, and other management tasks.
 */
//todo - display size handling, JFrame, JPanel/fullscreen, etc.
//todo - external image file convention - extensions should indicate subimage size, e.g. "./images-24x24.png"
public class Gui {

    private static Gui instance = null;

    private Canvas canvas = null;

    private int currentChannel = -1;

    private ArrayList<OutputChannel> outputChannels = new ArrayList<>();

    private Gui() {}

    public static Gui getInstance() {
        if (instance == null)
            instance = new Gui();
        return instance;
    }

    public void generateCanvas(int h, int w) {
        canvas = new Canvas(h, w);
    }

    /**
     * Add a new OutputChannel to this GUI.
     * @return the index of the added OutputChannel.
     */
    public int addOutputChannel() {
        outputChannels.add(new OutputChannel());
        if (currentChannel < 0) //if this is the first channel we are adding,
            currentChannel = 0; //set the current channel - otherwise do not change the current channel when adding
        return outputChannels.size() - 1;
    }

    /**
     * Add a new Region to the current OutputChannel.
     * @param region a pre-defined Region to add to the current OutputChannel.
     * @return the index of the added Region.
     */
    public int addRegion(Region region) {
        return addRegion(region, currentChannel);
    }

    /**
     * Add a new Region to the current OutputChannel.
     * @param region a pre-defined Region to add to the current OutputChannel.
     * @param outputChannelIndex the index of the OutputChannel to which the region will be added.
     * @return the index of the added Region.
     */
    public int addRegion(Region region, int outputChannelIndex) {
        requireChannel();
        OutputChannel oc = getChannel(outputChannelIndex);
        oc.pushRegion(region);
        return oc.countRegions() - 1;
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

    private OutputChannel getChannel(int index) {
        return outputChannels.get(index);
    }

    private void requireCanvas() {
        if (canvas == null)
            throw new IllegalStateException("Canvas not found - use .generateCanvas() first");
    }

    private void requireChannel() {
        if (currentChannel < 0)
            throw new IllegalStateException("No OutputChannel found - use .addOutputChannel() first");
    }
}
