package com.example.okta.moviedb_iak.rest

import com.example.okta.moviedb_iak.model.detailmovie.DetailMovie
import com.example.okta.moviedb_iak.model.popularmovie.PupularMovie
import com.example.okta.moviedb_iak.model.trailermovie.TrailerMovie

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Okta on 03/02/2018.
 */

interface APIInterface {
    @GET("movie/now_playing")
    fun getPopularMovie(@Query("api_key") apikey: String,
                        @Query("language") language: String,
                        @Query("page") page: String): Call<PupularMovie>

    @GET("movie/{movie_id}")
    fun getDetailMovie(@Path("movie_id") movie_id: Int?,
                       @Query("api_key") apikey: String,
                       @Query("language") language: String): Call<DetailMovie>

    @GET("movie/{movie_id}/videos")
    fun getTrailerMovie(@Path("movie_id") movie_id: Int?,
                        @Query("api_key") apikey: String,
                        @Query("language") language: String): Call<TrailerMovie>
}
