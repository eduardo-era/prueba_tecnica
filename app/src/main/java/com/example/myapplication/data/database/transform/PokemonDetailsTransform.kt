package com.example.myapplication.data.database.transform

import com.example.myapplication.data.database.models.PokemonDetailsRealm
import com.example.myapplication.data.models.PokemonDetails

class PokemonDetailsTransform {
    companion object {
        fun transform(pokemonDetailsRealm: MutableList<PokemonDetailsRealm>): ArrayList<PokemonDetails> {

            val arrayReturn = ArrayList<PokemonDetails>()

            pokemonDetailsRealm.forEach { pokemonDetailsR ->

                val pokemonDetails = PokemonDetails()

                pokemonDetails.id = pokemonDetailsR.id
                pokemonDetails.name = pokemonDetailsR.name
                pokemonDetails.height = pokemonDetailsR.height
                pokemonDetails.weight = pokemonDetailsR.weight
                pokemonDetails.sprites = pokemonDetailsR.sprites
                pokemonDetails.types = pokemonDetailsR.types
                pokemonDetails.isFavorite = pokemonDetailsR.isFavorite

                arrayReturn.add(pokemonDetails)
            }

            return arrayReturn
        }
    }
}