package com.example.okta.moviedb_iak.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.okta.moviedb_iak.R
import com.example.okta.moviedb_iak.activity.DetailActivity
import com.example.okta.moviedb_iak.adapter.AdapterRVTrailerMovie.CategoryViewHolder
import com.example.okta.moviedb_iak.model.trailermovie.Result

/**
 * Created by Okta on 04/02/2018.
 */

class AdapterRVTrailerMovie(private val context: Context) : RecyclerView.Adapter<CategoryViewHolder>() {
    var results: List<Result>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trailer, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val youtube_url = results!![position].key
        val name = results!![position].name
        holder.tvTitle.text = name
        holder.itemView.setOnClickListener {
            val id = results!![position].key.toString()
            DetailActivity.watchYoutubeVideo(context, id)
        }
    }

    override fun getItemCount(): Int {
        return results!!.size
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var icon: ImageView

        init {
            tvTitle = itemView.findViewById(R.id.itemTitleTrailer)
            icon = itemView.findViewById(R.id.itemIconTrailer)

        }
    }
}
