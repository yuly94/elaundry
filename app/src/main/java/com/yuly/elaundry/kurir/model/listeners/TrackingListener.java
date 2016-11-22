package com.yuly.elaundry.kurir.model.listeners;

import com.jjoe64.graphview.series.DataPoint;
import com.yuly.elaundry.kurir.model.map.Tracking;


public interface TrackingListener {
    /**
     * @param distance new distance passed
     */
    void updateDistance(Double distance);

    /**
     * @param avgSpeed new avg speed
     */
    void updateAvgSpeed(Double avgSpeed);

    /**
     * @param maxSpeed new max speed
     */
    void updateMaxSpeed(Double maxSpeed);

    /**
     * return data when {@link Tracking#requestDistanceGraphSeries()}  called
     *
     * @param dataPoints
     */
    void updateDistanceGraphSeries(DataPoint[][] dataPoints);

    /**
     * used to add new speed and distance DataPoint to DistanceGraphSeries
     *
     * @param speed
     * @param distance
     */
    void addDistanceGraphSeriesPoint(DataPoint speed, DataPoint distance);
}
