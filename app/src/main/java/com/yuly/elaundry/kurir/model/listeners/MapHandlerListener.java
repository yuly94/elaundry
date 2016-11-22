package com.yuly.elaundry.kurir.model.listeners;

import org.mapsforge.core.model.LatLong;


public interface MapHandlerListener {
    /**
     * when use press on the screen to get a location form map
     *
     * @param latLong
     */
    void onPressLocation(LatLong latLong);

    /**
     * calculate path calculating (running) true NOT running or finished false
     *
     * @param shortestPathRunning
     */
    void pathCalculating(boolean shortestPathRunning);
}
