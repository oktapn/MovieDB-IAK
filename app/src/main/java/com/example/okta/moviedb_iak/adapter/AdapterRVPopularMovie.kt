package com.example.okta.moviedb_iak.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.example.okta.moviedb_iak.R
import com.example.okta.moviedb_iak.activity.DetailActivity
import com.example.okta.moviedb_iak.model.popularmovie.Result

/**
 * Created by Okta on 03/02/2018.
 */

class AdapterRVPopularMovie(private val context: Context) : RecyclerView.Adapter<AdapterRVPopularMovie.CategoryViewHolder>() {
    var listPopularMovie: List<Result>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val imageUri = "http://image.tmdb.org/t/p/w185/" + listPopularMovie!![position].posterPath
        val title = listPopularMovie!![position].title.toString()
        holder.tvTitle.text = title
        Glide.with(context)
                .load(imageUri)
                .into(holder.icon)
        holder.itemView.setOnClickListener {
            val detailIntent = Intent(context, DetailActivity::class.java)
            val id = listPopularMovie!![position].id
            val overview = listPopularMovie!![position].overview
            detailIntent.putExtra("id_detail", id)
            detailIntent.putExtra("overview", overview)
            context.startActivity(detailIntent)
        }
    }

    override fun getItemCount(): Int {
        return listPopularMovie!!.size
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var icon: ImageView

        init {
            tvTitle = itemView.findViewById(R.id.itemTitle)
            icon = itemView.findViewById(R.id.itemIcon)

        }
    }
}
