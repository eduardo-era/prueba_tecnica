package com.example.myapplication.data.api.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PokemonDetailsService (
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("height")
    var height: Int? = null,

    @SerializedName("weight")
    var weight: Int? = null,

    @SerializedName("sprites")
    var sprites: PokemonDetailsSprites? = null,

    @SerializedName("types")
    var types: ArrayList<PokemonDetailsTypes>? = null,

    var isFavorite: Boolean? = null
)

@Keep
data class PokemonDetailsSprites (
    @SerializedName("front_default")
    var frontDefault: String? = null
)

@Keep
data class PokemonDetailsTypes (
    @SerializedName("type")
    var type: PokemonDetailsTypesType? = null
)

@Keep
data class PokemonDetailsTypesType (
    @SerializedName("name")
    var name: String? = null
)