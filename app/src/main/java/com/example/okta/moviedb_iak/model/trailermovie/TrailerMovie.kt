package com.example.okta.moviedb_iak.model.trailermovie

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TrailerMovie {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null

}
