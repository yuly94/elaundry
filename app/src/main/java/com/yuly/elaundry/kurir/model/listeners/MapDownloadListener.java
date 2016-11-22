package com.yuly.elaundry.kurir.model.listeners;


public interface MapDownloadListener {
    /**
     * a download is started
     */
    void downloadStart();

    /**
     * a download activity is finished
     */
    void downloadFinished(String mapName);

    void progressUpdate(Integer value);
}
