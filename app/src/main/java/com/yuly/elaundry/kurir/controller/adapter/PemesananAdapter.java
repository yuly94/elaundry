package com.yuly.elaundry.kurir.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.model.geterseter.Movie;
import com.yuly.elaundry.kurir.model.geterseter.PesananModels;
import com.yuly.elaundry.kurir.view.widgets.BubbleTextGetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anonymous on 25/11/16.
 */

public class PemesananAdapter extends RecyclerView.Adapter<PemesananAdapter.MyViewHolder> implements BubbleTextGetter {

    private List<Movie> moviesList;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public PemesananAdapter(List<Movie> moviesList, Context context) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }



    public void setFilter(List<Movie> movie){
        moviesList = new ArrayList<Movie>();
        moviesList.addAll(movie);
        notifyDataSetChanged();
    }


    // Add a list of items
    public void addAll(List<Movie> movie){

        moviesList.addAll(movie);
        notifyDataSetChanged();

    }

        /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        moviesList.clear();
        notifyDataSetChanged();
    }


    @Override
    public String getTextToShowInBubble(final int pos) {
        if (moviesList == null || moviesList.size() == 0)
            return "";
        Character ch = moviesList.get(pos).getTitle().charAt(0);
        if (Character.isDigit(ch)) {
            return "#";
        } else
            return Character.toString(ch);
    }
    /* Within the RecyclerView.Adapter class */


}
