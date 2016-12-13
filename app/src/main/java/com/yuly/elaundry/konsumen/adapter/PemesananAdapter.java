package com.yuly.elaundry.konsumen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.yuly.elaundry.konsumen.R;
import com.yuly.elaundry.konsumen.models.TransaksiModel;
import com.yuly.elaundry.konsumen.widgets.BubbleTextGetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anonymous on 25/11/16.
 */

public class PemesananAdapter extends RecyclerView.Adapter<PemesananAdapter.MyViewHolder> implements BubbleTextGetter {

    private List<TransaksiModel> transaksiList;
    ColorGenerator generator = ColorGenerator.MATERIAL;
    private String hurufDepan;
    private Context mContext;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPaket, tvHarga, alamat, tanggal;
        private ImageView hurufDepan, menu;


        public MyViewHolder(View view) {
            super(view);

            hurufDepan = (ImageView) view.findViewById(R.id.gmail_letter);

            tvPaket = (TextView) view.findViewById(R.id.tv_paket);
            tvHarga = (TextView) view.findViewById(R.id.pemesanan_harga);
            alamat = (TextView) view.findViewById(R.id.pemesanan_alamat);
            tanggal = (TextView) view.findViewById(R.id.pemesanan_tanggal);
            menu = (ImageView) view.findViewById(R.id.popup_menu);

        }
    }


    public PemesananAdapter(List<TransaksiModel> transaksiList, Context context) {

        this.mContext = context;
        this.transaksiList = transaksiList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_pemesanan, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TransaksiModel transaksi = transaksiList.get(position);

        holder.tvPaket.setText(transaksi.getPaket());
        holder.tvHarga.setText(transaksi.getHarga());
        holder.alamat.setText(transaksi.getAlamat());
        holder.tanggal.setText(transaksi.getTanggal());

        hurufDepan = String.valueOf((transaksi.getAlamat().charAt(0)));

        // Create a new TextDrawable for our image's background
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(hurufDepan, generator.getRandomColor());

        holder.hurufDepan.setImageDrawable(drawable);

        setOnPopupMenuListener(holder, position);
    }

    @Override
    public int getItemCount() {
        return transaksiList.size();
    }

    private void setOnPopupMenuListener(MyViewHolder itemHolder, final int position) {

        itemHolder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PopupMenu menu = new PopupMenu(mContext, v);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.pemesanan_detail:
                                //    MusicPlayer.playAll(mContext, songIDs, position, -1, TimberUtils.IdType.NA, false);
                                break;
                            case R.id.pemesanan_edit:
                                long[] ids = new long[1];
                                //     ids[0] = arraylist.get(position).id;
                                //     MusicPlayer.playNext(mContext, ids, -1, TimberUtils.IdType.NA);
                                break;
                            case R.id.pemesanan_delete:
                                //     NavigationUtils.goToAlbum(mContext, arraylist.get(position).albumId);

                                Log.d("POSISI", String.valueOf(position));
                                break;
                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.menu_pemesanan);
                menu.show();
            }
        });
    }



    public void setFilter(List<TransaksiModel> transaksi){
        transaksiList = new ArrayList<TransaksiModel>();
        transaksiList.addAll(transaksi);
        notifyDataSetChanged();
    }


    // Add a list of items
    public void addAll(List<TransaksiModel> transaksi){

        transaksiList.addAll(transaksi);
        notifyDataSetChanged();

    }

        /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        transaksiList.clear();
        notifyDataSetChanged();
    }


    @Override
    public String getTextToShowInBubble(final int pos) {
        if (transaksiList == null || transaksiList.size() == 0)
            return "";
        Character ch = transaksiList.get(pos).getAlamat().charAt(0);
        if (Character.isDigit(ch)) {
            return "#";
        } else
            return Character.toString(ch);
    }
    /* Within the RecyclerView.Adapter class */


}