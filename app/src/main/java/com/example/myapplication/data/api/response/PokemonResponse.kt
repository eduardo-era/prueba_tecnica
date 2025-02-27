package com.example.myapplication.data.api.response

import androidx.annotation.Keep
import com.example.myapplication.data.models.PokemonList
import com.google.gson.annotations.SerializedName


@Keep
data class PokemonResponse (
    @SerializedName("results")
    var pokemonList: ArrayList<PokemonList>? = null
)