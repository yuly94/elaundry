package com.yuly.elaundry.kurir.controller.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.widget.Toast;

import com.graphhopper.util.Helper;
import com.graphhopper.util.ProgressListener;
import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.controller.app.AppConfig;

import com.yuly.elaundry.kurir.model.peta.AndroidDownloader;
import com.yuly.elaundry.kurir.model.peta.AndroidHelper;
import com.yuly.elaundry.kurir.model.peta.DetailPetaRuteHandler;
import com.yuly.elaundry.kurir.model.peta.GHAsyncTask;
import com.yuly.elaundry.kurir.model.util.Variable;

import java.io.File;

/**
 * Created by yuly nurhidayati on 13/12/16.
 */

public class MendownloadPeta extends AppCompatActivity {

    private File mapsFolder;

    private static MendownloadPeta mendownloadPeta;

    public static MendownloadPeta getMendownloadPeta() {
        if (mendownloadPeta== null) {
            reset();
        }
        return mendownloadPeta;
    }

    /**
     * reset class, build a new instance
     */
    public static void reset() {
        mendownloadPeta = new MendownloadPeta();
    }

    public boolean checkFilePetaAda(){

    boolean greaterOrEqKitkat = Build.VERSION.SDK_INT >= 19;
    if (greaterOrEqKitkat) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            logD("Elaundry is not usable without an external storage!");
            //return;
        }

        mapsFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                Variable.getVariable().getMapDirectory());
    } else

        mapsFolder = new File(Environment.getExternalStorageDirectory(), Variable.getVariable().getMapDownloadDirectory());

    if (!mapsFolder.exists()) {

        return false;

    } else {

        return true;
    }

 }


    public void downloadPeta(final Context cont) {


        mapsFolder.mkdirs();
        final File areaFolder = new File(mapsFolder, Variable.getVariable().getCountry() + "-gh");
        if (AppConfig.downloadURL == null || areaFolder.exists()) {
            DetailPetaRuteHandler.getPetaRuteHandler().loadMap(areaFolder);
            return;
        }

       final ProgressDialog dialog = new ProgressDialog(cont);
        dialog.setIcon(R.drawable.ic_cloud_download_green_24dp);
        dialog.setMessage("Sedang mendownload peta ...");//+ AppConfig.downloadURL);
        dialog.setIndeterminate(false);
        dialog.setMax(100);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        dialog.show();

        new GHAsyncTask<Void, Integer, Object>() {
            protected Object saveDoInBackground(Void... _ignore)
                    throws Exception {

                String localFolder = Helper.pruneFileEnd(AndroidHelper.getFileName(AppConfig.downloadURL));
                localFolder = new File(mapsFolder, localFolder + "-gh").getAbsolutePath();
                //log("downloading & unzipping " + downloadURL + " to " + localFolder);
                AndroidDownloader downloader = new AndroidDownloader();
                downloader.setTimeout(30000);
                downloader.downloadAndUnzip(AppConfig.downloadURL, localFolder,
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

                   // logUser(str);
                } else {
                    //  PetaRuteHandler.getPetaRuteHandler().loadMap(areaFolder);

                    Toast.makeText(cont, "peta berhasil didownload ...", Toast.LENGTH_SHORT).show();

                   // logUser("Peta berhasil di download");
                }
            }
        }.execute();
    }


    private void log(String str, Throwable t) {
        Log.i(MendownloadPeta.class.getSimpleName(), str, t);
    }

    private void logD(String str) {
        Log.d(MendownloadPeta.class.getSimpleName(), str);
    }
}
