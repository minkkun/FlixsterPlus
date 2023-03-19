package com.example.flixterplus

import com.google.gson.annotations.SerializedName

/**
 * The Model for storing a single book from the API
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the gson library.
 */
class Movie (
    Mtitle: String = "",
    Mdesc: String = "",
    Mimage: String =""
        ) {

    @JvmField
    @SerializedName("original_title")
    var title: String? = Mtitle


    @SerializedName("poster_path")
    var movieImageUrl: String? = Mimage

    @SerializedName("overview")
    var description: String? = Mdesc

}