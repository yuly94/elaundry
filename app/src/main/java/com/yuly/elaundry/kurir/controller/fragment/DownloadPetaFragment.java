package com.yuly.elaundry.kurir.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.controller.activity.MendownloadPeta;

/**
 * Created by yuly on 13/12/16.
 */


public class DownloadPetaFragment  extends DialogFragment {

    String alert1 = "Silahkan download peta dahulu " ;
    String alert2 = "Peta Area Kediri dan sekitarnya "  ;
    String alert3 = "Ukuran file < 10 MB " ;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // set dialog icon
                .setIcon(R.drawable.ic_cloud_download_green_24dp)
                // set Dialog Title
                .setTitle("File peta tidak ditemukan")
                // Set Dialog Message
               // .setMessage("Silahkan download peta dahulu")


                .setMessage(alert1 +"\n"+ alert2 +"\n"+ alert3)

        // positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity(), "Pressed OK", Toast.LENGTH_SHORT).show();

                        MendownloadPeta.getMendownloadPeta().downloadPeta(getContext());

                    }
                })
                // negative button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
                    }
                }).create();
    }
}