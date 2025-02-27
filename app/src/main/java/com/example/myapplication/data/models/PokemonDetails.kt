package com.example.myapplication.data.models

import androidx.annotation.Keep

@Keep
data class PokemonDetails(
    var id: Int? = null,
    var name: String? = null,
    var height: Int? = null,
    var weight: Int? = null,
    var sprites: String? = null,
    var types: String? = null,
    var isFavorite: Boolean? = null
)