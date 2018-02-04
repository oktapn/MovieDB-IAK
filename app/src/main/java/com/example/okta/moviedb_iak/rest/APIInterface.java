package com.example.okta.moviedb_iak.rest;

import com.example.okta.moviedb_iak.model.detailmovie.DetailMovie;
import com.example.okta.moviedb_iak.model.popularmovie.PupularMovie;
import com.example.okta.moviedb_iak.model.trailermovie.TrailerMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Okta on 03/02/2018.
 */

public interface APIInterface {
    @GET("movie/now_playing")
    Call<PupularMovie> getPopularMovie(@Query("api_key") String apikey,
                                       @Query("language") String language,
                                       @Query("page") String page);

    @GET("movie/{movie_id}")
    Call<DetailMovie> getDetailMovie(@Path("movie_id") Integer movie_id,
                                     @Query("api_key") String apikey,
                                     @Query("language") String language);

    @GET("movie/{movie_id}/videos")
    Call<TrailerMovie> getTrailerMovie(@Path("movie_id") Integer movie_id,
                                       @Query("api_key") String apikey,
                                       @Query("language") String language);
}
