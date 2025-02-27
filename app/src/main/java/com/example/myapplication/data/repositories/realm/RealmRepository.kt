package com.example.myapplication.data.repositories.realm

import com.example.myapplication.data.DataResult
import com.example.myapplication.data.api.response.PokemonDetailsService
import com.example.myapplication.data.models.PokemonDetails

interface RealmRepository {
    suspend fun getPokemonList(limit:Int, offset:Int): ArrayList<PokemonDetails>
    suspend fun getPokemonCount(): Int
    fun savePokemonList(pokemonDetail: PokemonDetailsService)
    fun setFavorite(pokemonDetails: PokemonDetails, checked: Boolean)
    fun getFavoritesPokemon(): ArrayList<PokemonDetails>?
}