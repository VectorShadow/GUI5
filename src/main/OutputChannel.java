package main;

import java.awt.*;
import java.util.LinkedList;

/**
 * A construct for handling all display sources.
 */
class OutputChannel {
    private LinkedList<Region> regionList;

    OutputChannel() {
        regionList = new LinkedList<>();
    }


    int countRegions() {
        return regionList.size();
    }

    /**
     * Update the provided canvas from all defined regions, in order of addition.
     * This allows temporary regions, such as popup menus, to be drawn over the top of permanent regions.
     */
    void paint(Canvas canvas) {
        for (Region r : regionList) {
            r.paint(canvas);
        }
    }

    /**
     * Find the region corresponding to the specified pixelIndices.
     * @param pixelIndices the x, y coordinates of the pixel to locate.
     * @return the Region in which the specified pixelIndices are located,
     * or null if no defined region contains those indices.
     */
    Region locate(Point pixelIndices) {
        for (Region r : regionList) {
            if (r.contains(pixelIndices)) return r;
        }
        return null;
    }

    /**
     * Remove the most recently added region definition from this Channel.
     */
    void popRegion() {
        regionList.removeLast();
    }

    /**
     * Add a new region definition to this Channel.
     * @param region
     */
    void pushRegion(Region region) {
        regionList.addLast(region);
    }

    /**
     * Update all region sources for this OutputChannel.
     */
    void update() {
        for (int i = 0; i < regionList.size(); ++i)
            update(i);
    }

    /**
     * Update the region source corresponding to the specified index.
     */
    void update(int regionIndex) {
        regionList.get(regionIndex).update();
    }
}
