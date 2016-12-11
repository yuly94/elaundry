package com.yuly.elaundry.kurir.model.dijkstra3;

/**
 * Created by anonymous on 09/12/16.
 */

public class PathNotFoundException extends Exception {

    public PathNotFoundException() {
        super("Path from source to destination vertex was not found");
    }

    public PathNotFoundException(String msg) {
        super(msg);
    }

}