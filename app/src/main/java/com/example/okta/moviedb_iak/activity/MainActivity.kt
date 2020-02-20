package com.example.okta.moviedb_iak.activity

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast

import com.example.okta.moviedb_iak.R
import com.example.okta.moviedb_iak.adapter.AdapterRVPopularMovie
import com.example.okta.moviedb_iak.model.popularmovie.PupularMovie
import com.example.okta.moviedb_iak.rest.APIClient
import com.example.okta.moviedb_iak.rest.APIInterface

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var rvPopularMovie: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPopularMovie = findViewById(R.id.RVPopularMovie)

        getPopularMovie("d5f2a6ecff6d09de0c4abf1b69ea0689", "en-US", "1")
    }

    private fun getPopularMovie(ApiKey: String, language: String, Page: String) {
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Proses Mengambil Data")
        progressDialog.show()
        val apiInterface = APIClient.createService(APIInterface::class.java)
        val pupularMovieCall = apiInterface.getPopularMovie(ApiKey, language, Page)
        pupularMovieCall.enqueue(object : Callback<PupularMovie> {
            override fun onResponse(call: Call<PupularMovie>, response: Response<PupularMovie>) {
                try {
                    progressDialog.dismiss()
                    val results = response.body()!!.results
                    val adapterRVPopularMovie = AdapterRVPopularMovie(this@MainActivity)
                    adapterRVPopularMovie.listPopularMovie = results
                    rvPopularMovie.layoutManager = GridLayoutManager(this@MainActivity, 2)
                    rvPopularMovie.adapter = adapterRVPopularMovie
                } catch (e: NullPointerException) {
                    progressDialog.dismiss()
                    Log.e("PopularMovie", "onResponse", e)
                }

            }

            override fun onFailure(call: Call<PupularMovie>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
