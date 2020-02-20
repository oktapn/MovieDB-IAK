package com.example.okta.moviedb_iak.model.detailmovie

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductionCompany {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null

}
