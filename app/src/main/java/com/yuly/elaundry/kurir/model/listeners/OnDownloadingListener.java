package com.yuly.elaundry.kurir.model.listeners;

import android.widget.ProgressBar;
import android.widget.TextView;


public interface OnDownloadingListener {
    void progressbarReady(TextView downloadStatus, ProgressBar progressBar);
}
