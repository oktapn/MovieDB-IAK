package com.example.okta.moviedb_iak.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.okta.moviedb_iak.R;
import com.example.okta.moviedb_iak.model.trailermovie.Result;

import java.util.List;

/**
 * Created by Okta on 04/02/2018.
 */

public class AdapterRVTrailerMovie extends RecyclerView.Adapter<AdapterRVTrailerMovie.CategoryViewHolder> {

    private Context context;
    private List<Result> results;

    public AdapterRVTrailerMovie(Context context) {
        this.context = context;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        return new AdapterRVTrailerMovie.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        String youtube_url = getResults().get(position).getKey();
        String name = getResults().get(position).getName();
        holder.tvTitle.setText(name);
    }

    @Override
    public int getItemCount() {
        return getResults().size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView icon;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.itemTitleTrailer);
            icon = itemView.findViewById(R.id.itemIconTrailer);

        }
    }
}
