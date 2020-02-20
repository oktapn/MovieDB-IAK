package com.example.okta.moviedb_iak.activity

import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.example.okta.moviedb_iak.R
import com.example.okta.moviedb_iak.adapter.AdapterRVTrailerMovie
import com.example.okta.moviedb_iak.model.detailmovie.DetailMovie
import com.example.okta.moviedb_iak.model.trailermovie.TrailerMovie
import com.example.okta.moviedb_iak.rest.APIClient
import com.example.okta.moviedb_iak.rest.APIInterface

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    lateinit var tvTitle: TextView
    lateinit var tvYear: TextView
    lateinit var tvDuration: TextView
    lateinit var tvRating: TextView
    lateinit var tvDescription: TextView
    lateinit var ivImage: ImageView
    lateinit var rvtrailer: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupView()
        val intent = intent
        val bd = intent.extras
        val id_movie = bd!!.get("id_detail") as Int
        getDetailMovie(id_movie, "d5f2a6ecff6d09de0c4abf1b69ea0689", "en-US")
        setTrailerMovir(id_movie, "d5f2a6ecff6d09de0c4abf1b69ea0689", "en-US")

    }

    private fun setTrailerMovir(movie_id: Int?, Apikey: String, language: String) {
        val apiInterface = APIClient.createService<APIInterface>(APIInterface::class.java)
        val trailerMovieCall = apiInterface.getTrailerMovie(movie_id, Apikey, language)
        trailerMovieCall.enqueue(object : Callback<TrailerMovie> {
            override fun onResponse(call: Call<TrailerMovie>, response: Response<TrailerMovie>) {
                try {
                    val results = response.body()!!.results
                    val adapterRVTrailerMovie = AdapterRVTrailerMovie(this@DetailActivity)
                    adapterRVTrailerMovie.results = results
                    rvtrailer.layoutManager = LinearLayoutManager(this@DetailActivity)
                    rvtrailer.adapter = adapterRVTrailerMovie
                } catch (e: NullPointerException) {
                    Log.e("PopularMovie", "onResponse", e)
                }

            }

            override fun onFailure(call: Call<TrailerMovie>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupView() {
        tvTitle = findViewById(R.id.TitleDetail)
        tvYear = findViewById(R.id.YearDetail)
        tvDuration = findViewById(R.id.DurationDetail)
        tvRating = findViewById(R.id.RatingDetail)
        ivImage = findViewById(R.id.ImageDetail)
        tvDescription = findViewById(R.id.DescriptionDetail)
        rvtrailer = findViewById(R.id.rvTrailer)
    }

    private fun getDetailMovie(movie_id: Int?, Apikey: String, language: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Proses Mengambil Data")
        progressDialog.show()

        val apiInterface = APIClient.createService<APIInterface>(APIInterface::class.java)
        val detailMovieCall = apiInterface.getDetailMovie(movie_id, Apikey, language)
        detailMovieCall.enqueue(object : Callback<DetailMovie> {
            override fun onResponse(call: Call<DetailMovie>, response: Response<DetailMovie>) {
                try {
                    progressDialog.dismiss()
                    tvTitle.text = response.body()!!.title
                    val release = response.body()!!.releaseDate
                    val year = release?.substring(0, 4)
                    tvYear.setText(year)
                    tvDuration.text = response.body()!!.runtime!!.toString() + "min"
                    tvRating.text = response.body()!!.voteAverage.toString() + "/10"
                    Glide.with(this@DetailActivity).load("http://image.tmdb.org/t/p/w185/" + response.body()!!.posterPath).into(ivImage)
                    val overview = response.body()!!.overview
                    tvDescription.text = overview
                } catch (e: NullPointerException) {
                    progressDialog.dismiss()
                    Log.e("PopularMovie", "onResponse", e)
                }

            }

            override fun onFailure(call: Call<DetailMovie>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    companion object {

        fun watchYoutubeVideo(context: Context, id: String) {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
            val webIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=$id"))
            try {
                context.startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                context.startActivity(webIntent)
            }

        }
    }
}
