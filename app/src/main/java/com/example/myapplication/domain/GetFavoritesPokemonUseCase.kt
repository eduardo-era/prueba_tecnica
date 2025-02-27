package com.example.myapplication.domain

import com.example.myapplication.data.DataResult
import com.example.myapplication.data.models.PokemonDetails
import com.example.myapplication.data.repositories.realm.RealmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFavoritesPokemonUseCase(private val provideRealmRepository: RealmRepository) {

    operator fun invoke(): Flow<DataResult<ArrayList<PokemonDetails>, Exception>> = flow {
        emit(DataResult.Loading)
        try {
            val pokemonList = provideRealmRepository.getFavoritesPokemon()
            if (pokemonList != null) {
                emit(DataResult.Success(pokemonList))

            } else {
                emit(DataResult.Error(Exception()))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e))
        }
    }
}