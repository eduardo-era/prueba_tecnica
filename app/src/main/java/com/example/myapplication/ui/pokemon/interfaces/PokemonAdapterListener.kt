package com.example.myapplication.ui.pokemon.interfaces

import com.example.myapplication.data.models.PokemonDetails

interface PokemonAdapterListener {
    fun onPokemonDetailsClick(pokemonDetails: PokemonDetails)
    fun onPokemonImageClick(pokemonDetails: PokemonDetails)
    fun onPokemonFavoriteClick(pokemonDetails: PokemonDetails, checked: Boolean)
}