package com.yuly.elaundry.kurir.model.map;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.yuly.elaundry.kurir.model.listeners.OnDownloadingListener;


public class OnDownloading {
    private static OnDownloading onDownloading;

    public static OnDownloading getOnDownloading() {
        if (onDownloading == null) {
            onDownloading = new OnDownloading();
        }
        return onDownloading;
    }

    private OnDownloading() {
        this.downloadingProgressBar = null;
    }

    private ProgressBar downloadingProgressBar;
    private OnDownloadingListener listener;

    /**
     * can only get once, will sett til null after a get
     *
     * @return
     */
    public ProgressBar getDownloadingProgressBar() {
        ProgressBar pb = downloadingProgressBar;
        //        setDownloadingProgressBar(null);
        return pb;
    }

    public void setDownloadingProgressBar(TextView downloadStatus, ProgressBar downloadingProgressBar) {
        System.out.println("***** variable proress bar *****" + downloadingProgressBar);
        this.downloadingProgressBar = downloadingProgressBar;
        listener.progressbarReady(downloadStatus,downloadingProgressBar);
    }

    public void setListener(OnDownloadingListener listener) {
        this.listener = listener;
    }
}
