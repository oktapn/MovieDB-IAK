package com.example.okta.moviedb_iak.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.okta.moviedb_iak.R;
import com.example.okta.moviedb_iak.model.popularmovie.PupularMovie;
import com.example.okta.moviedb_iak.model.popularmovie.Result;

import java.util.List;

/**
 * Created by Okta on 03/02/2018.
 */

public class AdapterRVPopularMovie extends RecyclerView.Adapter<AdapterRVPopularMovie.CategoryViewHolder> {

    private Context context;
    private List<Result> ListPopularMovie;

    public List<Result> getListPopularMovie() {
        return ListPopularMovie;
    }

    public AdapterRVPopularMovie(Context context) {
        this.context = context;
    }

    public void setListPopularMovie(List<Result> listPopularMovie) {
        ListPopularMovie = listPopularMovie;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        String imageUri = String.valueOf("http://image.tmdb.org/t/p/w185/" + getListPopularMovie().get(position).getPosterPath());
        String title = String.valueOf(getListPopularMovie().get(position).getTitle());
        holder.tvTitle.setText(title);
        Glide.with(context)
                .load(imageUri)
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return getListPopularMovie().size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView icon;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.itemTitle);
            icon = itemView.findViewById(R.id.itemIcon);

        }
    }
}
