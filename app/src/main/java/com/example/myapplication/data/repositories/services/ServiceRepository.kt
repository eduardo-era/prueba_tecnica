package com.example.myapplication.data.repositories.services

import com.example.myapplication.data.DataResult
import com.example.myapplication.data.api.response.PokemonDetailsService
import com.example.myapplication.data.models.PokemonList

interface ServiceRepository {
    suspend fun getPokemonList(limit:Int, offset:Int): DataResult<ArrayList<PokemonList>, Exception>
    suspend fun getPokemonDetail(id:Int): DataResult<PokemonDetailsService, Exception>
}