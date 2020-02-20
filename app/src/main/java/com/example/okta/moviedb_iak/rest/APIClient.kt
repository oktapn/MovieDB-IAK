package com.example.okta.moviedb_iak.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Okta on 01/02/2018.
 */

object APIClient {
    var BASE_URL = "https://api.themoviedb.org/3/"
    private val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    private var retrofit = builder.build()

    private val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient = OkHttpClient.Builder()

    fun <S> createService(
            serviceClass: Class<S>): S {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging)
            builder.client(httpClient.build())
            retrofit = builder.build()
        }

        return retrofit.create(serviceClass)
    }
}
