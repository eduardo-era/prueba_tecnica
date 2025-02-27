package com.example.myapplication.data.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PokemonList(
    @SerializedName("name")
    var name: String? = null,

    @SerializedName("url")
    var url: String? = null
)