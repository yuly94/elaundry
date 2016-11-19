package com.yuly.elaundry.adapter;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.yuly.elaundry.R;
import com.yuly.elaundry.models.PesananModels;
import com.yuly.elaundry.widgets.BubbleTextGetter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sulei on 8/12/2015.
 */
public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.GmailVH>  implements BubbleTextGetter {




    ColorGenerator generator = ColorGenerator.MATERIAL;
    OnItemClickListener clickListener;
    private long[] songIDs;

    /*int colors[] = {R.color.red, R.color.pink, R.color.purple, R.color.deep_purple,
            R.color.indigo, R.color.blue, R.color.light_blue, R.color.cyan, R.color.teal, R.color.green,
            R.color.light_green, R.color.lime, R.color.yellow, R.color.amber, R.color.orange, R.color.deep_orange};*/


    private Context context;

    //List of superHeroes
   // List<PesananModels> superHeroes;

    private Context mContext;
    private String letter;
    private List<PesananModels> dataList;
    private boolean isDarkTheme;

    public TransaksiAdapter(List<PesananModels> superHeroes, Context context){
        this.dataList = new ArrayList<PesananModels>();
        this.mContext = context;
        this.dataList = superHeroes;
    }



    @Override
    public GmailVH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_pesanan, viewGroup, false);

        isDarkTheme = PreferenceManager.getDefaultSharedPreferences(viewGroup.getContext()).getBoolean("dark_theme", false);

/*        if (!isDarkTheme){


        }*/

        return new GmailVH(mContext, view);
    }



    @Override
    public void onBindViewHolder(GmailVH gmailVH, int i) {
        // final CountryModel model = mCountryModel.get(i);
        final PesananModels pesananModel = dataList.get(i);


        gmailVH.title_doa.setText(pesananModel.getName());
        gmailVH.title_hr.setText(pesananModel.getRealName());

        letter = String.valueOf(pesananModel.getRealName().charAt(0));

        // Create a new TextDrawable for our image's background
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor());

        gmailVH.letter.setImageDrawable(drawable);
        setOnPopupMenuListener(gmailVH, i);


    }

    private void setOnPopupMenuListener(GmailVH itemHolder, final int position) {

        itemHolder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PopupMenu menu = new PopupMenu(mContext, v);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popup_song_play:
                                //    MusicPlayer.playAll(mContext, songIDs, position, -1, TimberUtils.IdType.NA, false);
                                break;
                            case R.id.popup_song_play_next:
                                long[] ids = new long[1];
                                //     ids[0] = arraylist.get(position).id;
                                //     MusicPlayer.playNext(mContext, ids, -1, TimberUtils.IdType.NA);
                                break;
                            case R.id.popup_song_goto_album:
                                //     NavigationUtils.goToAlbum(mContext, arraylist.get(position).albumId);
                                break;
                            case R.id.popup_song_goto_artist:
                                //      NavigationUtils.goToArtist(mContext, arraylist.get(position).artistId);
                                break;
                            case R.id.popup_song_addto_queue:
                                long[] id = new long[1];
                                //    id[0] = arraylist.get(position).id;
                                //     MusicPlayer.addToQueue(mContext, id, -1, TimberUtils.IdType.NA);
                                break;
                            case R.id.popup_song_addto_playlist:
                                //    AddPlaylistDialog.newInstance(arraylist.get(position)).show(((AppCompatActivity) mContext).getSupportFragmentManager(), "ADD_PLAYLIST");
                                break;
                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.popup_song);
                menu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


    public void setFilter(List<PesananModels> pesananModels){
        dataList = new ArrayList<PesananModels>();
        dataList.addAll(pesananModels);
        notifyDataSetChanged();
    }


    // Add a list of items
    public void addAll(List<PesananModels> transaksiModels){

        dataList.addAll(transaksiModels);
        notifyDataSetChanged();

    }

        /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }



    public long[] getDoaIds() {
        long[] ret = new long[getItemCount()];
        for (int i = 0; i < getItemCount(); i++) {
            ret[i] = dataList.get(i).getRank();
        }

        return ret;
    }

//String.valueOf(pesananModel.getRealName().charAt(0));


    @Override
    public String getTextToShowInBubble(final int pos) {
        if (dataList == null || dataList.size() == 0)
            return "";
        Character ch = dataList.get(pos).getRealName().charAt(0);
        if (Character.isDigit(ch)) {
            return "#";
        } else
            return Character.toString(ch);
    }
    /* Within the RecyclerView.Adapter class */



    protected class GmailVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context context;
        private TextView title_doa;
        private TextView title_hr;
        private ImageView letter;
        protected ImageView menu;




        public GmailVH(Context context,View itemView) {
            super(itemView);
            this.letter = (ImageView) itemView.findViewById(R.id.gmail_letter);
            this.title_doa = (TextView) itemView.findViewById(R.id.doa_title);
            this.title_hr = (TextView) itemView.findViewById(R.id.doa_hr);
            this.menu = (ImageView) itemView.findViewById(R.id.popup_menu);

            // Pull current config key from Activity theme attr
     //       ATE.themeView(itemView , Util.resolveString(itemView.getContext(), R.attr.ate_key));

            this.context = context;
            // Attach a click listener to the entire row view
            //     itemView.setOnClickListener(this);

            itemView.setOnClickListener(this);

            //  title = (TextView) itemView.findViewById(R.id.listitem_name);
        }

        @Override
        public void onClick(View v) {

            clickListener.onItemClick(v, getAdapterPosition(), getDoaIds() );
        }
    }





    public interface OnItemClickListener {
        public void onItemClick(View view, int position, long[] itemId);
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }



}

