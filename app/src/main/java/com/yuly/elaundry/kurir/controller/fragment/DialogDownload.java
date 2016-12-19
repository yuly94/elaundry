package com.yuly.elaundry.kurir.controller.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.controller.activity.MendownloadPeta;
import com.yuly.elaundry.kurir.model.util.Variable;

/**
 * Created by yuly nurhidayati on 13/12/16.
 */

public class DialogDownload {

    public static void showDownloadPetaDialog(AppCompatActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new DownloadPetaFragment().show(ft, "dialog_about");
    }

    public static class  DownloadPetaFragment  extends DialogFragment {

        public DownloadPetaFragment(){
        }

        String alert1 = "Silahkan download peta dahulu " ;
        String alert2 = "Peta Area Kediri dan sekitarnya "  ;
        String alert3 = "Ukuran file < 10 MB " ;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new android.app.AlertDialog.Builder(getActivity())
                    // set dialog icon
                    .setIcon(R.drawable.ic_mood_bad_green_24dp)
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


    public static void showDownloadUlangDialog(AppCompatActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new DownloadUlangDialog().show(ft, "dialog_about");
    }

    public static class DownloadUlangDialog extends DialogFragment {

        public DownloadUlangDialog(){
        }

        String alert1 = "Silahkan download peta kembali " ;
        String alert2 = "Peta Area Kediri dan sekitarnya "  ;
        String alert3 = "Ukuran file < 10 MB " ;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new android.app.AlertDialog.Builder(getActivity())
                    // set dialog icon
                    .setIcon(R.drawable.ic_cloud_download_green_24dp)
                    // set Dialog Title
                    .setTitle("File peta corrupt")
                    // Set Dialog Message
                    // .setMessage("Silahkan download peta dahulu")


                    .setMessage(alert1 +"\n"+ alert2 +"\n"+ alert3)

                    // positive button
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(getActivity(), "Pressed OK", Toast.LENGTH_SHORT).show();

                            boolean success = MendownloadPeta.deletePeta(Variable.getVariable().getMapsFolder());

                            if (!success) {
                                System.out.println("Deletion of directory failed!");
                            } else {
                                MendownloadPeta.getMendownloadPeta().downloadPeta(getContext());
                            }

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

}




