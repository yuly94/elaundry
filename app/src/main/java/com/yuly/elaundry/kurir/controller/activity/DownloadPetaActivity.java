package com.yuly.elaundry.kurir.controller.activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.graphhopper.util.Helper;
import com.graphhopper.util.ProgressListener;

import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.model.peta.AndroidDownloader;
import com.yuly.elaundry.kurir.model.peta.AndroidHelper;
import com.yuly.elaundry.kurir.model.peta.GHAsyncTask;
import com.yuly.elaundry.kurir.model.peta.DetailPetaRuteHandler;
import com.yuly.elaundry.kurir.model.util.Variable;



import java.io.File;

public class DownloadPetaActivity extends AppCompatActivity {

    private String downloadURL ="http://elaundry.pe.hu/assets/maps/indonesia_jawatimur_kediringanjuk.ghz";
    private File mapsFolder = Variable.getVariable().getMapsFolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_peta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                downloadPeta();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void downloadPeta() {

/*
        boolean greaterOrEqKitkat = Build.VERSION.SDK_INT >= 19;
        if (greaterOrEqKitkat) {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                logUser("Elaundry is not usable without an external storage!");
                return;
            }
            mapsFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    Variable.getVariable().getMapDirectory());
        } else
            mapsFolder = new File(Environment.getExternalStorageDirectory(), Variable.getVariable().getMapDownloadDirectory());
*/


        mapsFolder.mkdirs();

        final File areaFolder = new File(mapsFolder, Variable.getVariable().getCountry() + "-gh");
        if (downloadURL == null || areaFolder.exists()) {
            DetailPetaRuteHandler.getPetaRuteHandler().loadMap(areaFolder);
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Downloading and uncompressing " + downloadURL);
        dialog.setIndeterminate(false);
        dialog.setMax(100);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();

        new GHAsyncTask<Void, Integer, Object>() {
            protected Object saveDoInBackground(Void... _ignore)
                    throws Exception {

                String localFolder = Helper.pruneFileEnd(AndroidHelper.getFileName(downloadURL));
                localFolder = new File(mapsFolder, localFolder + "-gh").getAbsolutePath();
                log("downloading & unzipping " + downloadURL + " to " + localFolder);
                AndroidDownloader downloader = new AndroidDownloader();
                downloader.setTimeout(30000);
                downloader.downloadAndUnzip(downloadURL, localFolder,
                        new ProgressListener() {
                            @Override
                            public void update(long val) {
                                publishProgress((int) val);
                            }
                        });
                return null;
            }

            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                dialog.setProgress(values[0]);
            }

            protected void onPostExecute(Object _ignore) {
                dialog.dismiss();
                if (hasError()) {
                    String str = "An error happened while retrieving maps:" + getErrorMessage();
                    log(str, getError());
                    logUser(str);
                } else {
                  //  PetaRuteHandler.getPetaRuteHandler().loadMap(areaFolder);

                    finish();
                }
            }
        }.execute();
    }

    public static boolean deletePeta(File fileOrDirectory)
    {
        if (fileOrDirectory.isDirectory())
        {
            for (File child : fileOrDirectory.listFiles())
            {
                boolean success = deletePeta(child);

                if (!success) {
                    return false;
                }
            }
        }

        //fileOrDirectory.delete();
        return fileOrDirectory.delete();
    }


    private void log(String str) {
        Log.i(this.getClass().getSimpleName(), "-------" + str);
    }


    private void logUser(String str) {
        log(str);
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
    private void log(String str, Throwable t) {
        Log.i("GH", str, t);
    }
}
