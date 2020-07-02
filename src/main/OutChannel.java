package main;

import java.awt.*;
import java.util.LinkedList;

/**
 * A construct for handling all display sources.
 */
public class OutChannel {
    LinkedList<Region> regionList;

    public OutChannel() {
        regionList = new LinkedList<>();
    }

    /**
     * Add a new region definition to this Channel.
     * @param region
     */
    public void pushRegion(Region region) {
        regionList.addLast(region);
    }

    /**
     * Remove the most recently added region definition from this Channel.
     */
    public void popRegion() {
        regionList.removeLast();
    }

    /**
     * Find the region corresponding to the specified pixelIndices.
     * @param pixelIndices the x, y coordinates of the pixel to locate.
     * @return the Region in which the specified pixelIndices are located,
     * or null if no defined region contains those indices.
     */
    public Region locate(Point pixelIndices) {
        for (Region r : regionList) {
            if (r.contains(pixelIndices)) return r;
        }
        return null;
    }

    /**
     * Update the provided canvas from all defined regions, in order of addition.
     * This allows temporary regions, such as popup menus, to be drawn over the top of permanent regions.
     * @param canvas
     */
    public void paint(Canvas canvas) {
        for (Region r : regionList) {
            r.paint(canvas);
        }
    }
}
