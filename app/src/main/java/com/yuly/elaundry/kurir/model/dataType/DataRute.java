package com.yuly.elaundry.kurir.model.dataType;

import org.mapsforge.core.model.LatLong;


public class DataRute {
    private LatLong startPoint, endPoint;
    private static DataRute datarute;

    private DataRute(LatLong startPoint, LatLong endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }


    public static DataRute getDatarute() {
        if (datarute == null) {
            datarute = new DataRute(null, null);
        }
        return datarute;
    }

    public LatLong getStartPoint() {
        return startPoint;
    }

    /**
     * @return a string of la: latitude, lo: longitude
     */
    public String getStartPointToString() {
        if (startPoint != null) {
            //            String la = "Lat:" + String.valueOf(startPoint.latitude);
            //            la = la.length() > 12 ? la.substring(0, 12) : la;
            //            String lo = "Lon:" + String.valueOf(startPoint.longitude);
            //            lo = lo.length() > 12 ? lo.substring(0, 12) : lo;
            String la = String.valueOf(startPoint.latitude);
            String lo = String.valueOf(startPoint.longitude);
            return la + "," + lo;
        }
        return null;
    }

    /**
     * @return a string of la: latitude, lo: longitude
     */
    public String getEndPointToString() {
        if (endPoint != null) {
            //            String la = "Lat:" + String.valueOf(endPoint.latitude);
            //            la = la.length() > 12 ? la.substring(0, 12) : la;
            //            String lo = "Lon:" + String.valueOf(endPoint.longitude);
            //            lo = lo.length() > 12 ? lo.substring(0, 12) : lo;
            String la = String.valueOf(endPoint.latitude);
            String lo = String.valueOf(endPoint.longitude);
            return la + "," + lo;
        }
        return null;
    }

    public void setStartPoint(LatLong startPoint) {
        this.startPoint = startPoint;
    }

    public LatLong getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(LatLong endPoint) {
        this.endPoint = endPoint;
    }
}
