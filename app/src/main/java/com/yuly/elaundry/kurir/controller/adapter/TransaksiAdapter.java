package com.yuly.elaundry.kurir.controller.adapter;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.model.geterseter.PesananModels;
import com.yuly.elaundry.kurir.view.widgets.BubbleTextGetter;


import java.util.ArrayList;
import java.util.List;


public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.PemesananVH>  implements BubbleTextGetter {

    ColorGenerator generator = ColorGenerator.MATERIAL;
    OnItemClickListener clickListener;
    private long[] songIDs;


    /*int colors[] = {R.color.red, R.color.pink, R.color.purple, R.color.deep_purple,
            R.color.indigo, R.color.blue, R.color.light_blue, R.color.cyan, R.color.teal, R.color.green,
            R.color.light_green, R.color.lime, R.color.yellow, R.color.amber, R.color.orange, R.color.deep_orange};*/


    private Context context;

    //List of listPesanan
    private List<PesananModels> listPesanan;

    private Context mContext;
    private String hurufDepan;
    private List<PesananModels> pesanandataList;
    private boolean isDarkTheme;
    private int noidku;

    public TransaksiAdapter(List<PesananModels> listPesanan, Context context){
       // this.pesanandataList = new ArrayList<PesananModels>();
        this.mContext = context;
        this.pesanandataList = listPesanan;
    }



    @Override
    public PemesananVH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_pesanan, viewGroup, false);

        isDarkTheme = PreferenceManager.getDefaultSharedPreferences(viewGroup.getContext()).getBoolean("dark_theme", false);

/*        if (!isDarkTheme){


        }*/

        return new PemesananVH(mContext, view);
    }



    @Override
    public void onBindViewHolder(PemesananVH pesananVH, int posisi) {
        // final CountryModel model = mCountryModel.get(i);
        final PesananModels pesananModel = pesanandataList.get(posisi);


      //  pesananVH.judul_no.setText(pesananModel.getNoId());
      //  pesananVH.no_pemesanan.setText(pesananModel.getNoId());
        pesananVH.judul_pemesanan.setText(pesananModel.getName());
        pesananVH.judul_detail.setText(pesananModel.getRealName());

        hurufDepan = String.valueOf(pesananModel.getRealName().charAt(0));

        // Create a new TextDrawable for our image's background
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(hurufDepan, generator.getRandomColor());

        pesananVH.hurufDepan.setImageDrawable(drawable);

      //  Post current = Post.get(position);


        // add this line
        pesananVH.noid_ku = pesananModel.getNoId();

        // add this line
        int noidku = pesananModel.getNoId();

        setOnPopupMenuListener(pesananVH, noidku);

    }

    private void setOnPopupMenuListener(PemesananVH itemHolder, final int position) {

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

    @Override
    public int getItemCount() {
        return pesanandataList == null ? 0 : pesanandataList.size();
    }


    public void setFilter(List<PesananModels> pesananModels){
        pesanandataList = new ArrayList<PesananModels>();
        pesanandataList.addAll(pesananModels);
        notifyDataSetChanged();
    }


    // Add a list of items
    public void addAll(List<PesananModels> transaksiModels){

        pesanandataList.addAll(transaksiModels);
        notifyDataSetChanged();

    }

        /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        pesanandataList.clear();
        notifyDataSetChanged();
    }



    public long[] getDoaIds() {
        long[] ret = new long[getItemCount()];
        for (int i = 0; i < getItemCount(); i++) {
            ret[i] = pesanandataList.get(i).getRank();
        }

        return ret;
    }

//String.valueOf(pesananModel.getRealName().charAt(0));


    @Override
    public String getTextToShowInBubble(final int pos) {
        if (pesanandataList == null || pesanandataList.size() == 0)
            return "";
        Character ch = pesanandataList.get(pos).getRealName().charAt(0);
        if (Character.isDigit(ch)) {
            return "#";
        } else
            return Character.toString(ch);
    }
    /* Within the RecyclerView.Adapter class */



    protected class PemesananVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context context;
        private TextView judul_pemesanan;
        private TextView judul_detail;
        private ImageView hurufDepan;
        protected ImageView menu;
        private int noid_ku;


        public PemesananVH(Context context,View itemView) {
            super(itemView);
            this.hurufDepan = (ImageView) itemView.findViewById(R.id.gmail_letter);

            this.judul_pemesanan = (TextView) itemView.findViewById(R.id.doa_title);
            this.judul_detail = (TextView) itemView.findViewById(R.id.doa_hr);
            this.menu = (ImageView) itemView.findViewById(R.id.popup_menu);

            // Pull current config key from Activity theme attr
     //       ATE.themeView(itemView , Util.resolveString(itemView.getContext(), R.attr.ate_key));

            this.context = context;
            // Attach a click listener to the entire row view
            //     itemView.setOnClickListener(this);

           // itemView.setOnClickListener(this);

            //  title = (TextView) itemView.findViewById(R.id.listitem_name);
        }

        public void onClick(View v) {
             Toast.makeText(v.getContext() , noid_ku , Toast.LENGTH_SHORT).show();
          //  Intent intent = new Intent(itemView.getContext() , DetailActivity.class);
            // here pass id through intent
          //  intent.putExtra("postId" , id);
          //  itemView.getContext().startActivity(intent);
        }
    }



    public interface OnItemClickListener {
        public void onItemClick(View view, int position, long[] itemId);
    }

    public interface RecyclerViewClickListener
    {

        public void recyclerViewListClicked(View v, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }



}

