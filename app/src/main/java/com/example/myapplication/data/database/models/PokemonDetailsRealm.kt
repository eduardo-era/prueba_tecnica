package com.example.myapplication.data.database.models

import androidx.annotation.Keep
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

@Keep
open class PokemonDetailsRealm : RealmObject()  {

    @PrimaryKey
    var id: Int? = null

    var name: String? = null
    var height: Int? = null
    var weight: Int? = null
    var sprites: String? = null
    var types: String? = null
    var isFavorite: Boolean? = null

    companion object {
        fun deepCopy(pokemon: PokemonDetailsRealm): PokemonDetailsRealm {
            return PokemonDetailsRealm().apply {
                id          =  pokemon.id
                name        = pokemon.name
                height      = pokemon.height
                weight      = pokemon.weight
                sprites     = pokemon.sprites
                types       = pokemon.types
                isFavorite  = pokemon.isFavorite
            }
        }
    }
}