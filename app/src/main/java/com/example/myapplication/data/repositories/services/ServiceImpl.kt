package com.example.myapplication.data.repositories.services

import com.example.myapplication.data.DataResult
import com.example.myapplication.data.api.Services
import com.example.myapplication.data.api.response.PokemonDetailsService
import com.example.myapplication.data.models.PokemonList
import com.example.myapplication.data.performNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ServiceImpl(private val apiService: Services) : ServiceRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int): DataResult<ArrayList<PokemonList>, Exception> = withContext(
        Dispatchers.IO) {
        performNetworkCall({ apiService.getPokemonList(limit, offset) },
            { response ->
                response?.pokemonList
            }
        )
    }

    override suspend fun getPokemonDetail(id: Int): DataResult<PokemonDetailsService, Exception> = withContext(
        Dispatchers.IO) {
        performNetworkCall({ apiService.getPokemonDetail(id) },
            { response ->
                response
            }
        )
    }
}