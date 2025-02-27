package com.example.myapplication.domain

import com.example.myapplication.common.Constants.ALL_POKEMON_LIMIT
import com.example.myapplication.common.Constants.POKEMON_LIMIT
import com.example.myapplication.data.DataResult
import com.example.myapplication.data.api.response.PokemonDetailsService
import com.example.myapplication.data.models.PokemonDetails
import com.example.myapplication.data.repositories.realm.RealmRepository
import com.example.myapplication.data.repositories.services.ServiceRepository
import com.example.myapplication.utils.getNumberPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonListUseCase(
    private val provideRepository: ServiceRepository,
    private val provideRealmRepository: RealmRepository
) {

    private var offsetStatic = POKEMON_LIMIT

    operator fun invoke(isUpdate: Boolean): Flow<DataResult<ArrayList<PokemonDetails>, Exception>> = flow {
        emit(DataResult.Loading)
        try {
            when(isUpdate) {
                true -> {
                    getUpdatePokemonList { pokemonList ->
                        offsetStatic += POKEMON_LIMIT
                        emit(DataResult.Success(pokemonList))
                    }
                }

                false -> {
                    getPokemonList { pokemonList ->
                        offsetStatic = POKEMON_LIMIT
                        emit(DataResult.Success(pokemonList))
                    }
                }
            }

        } catch (e: Exception) {
            emit(DataResult.Error(e))
        }
    }

    private suspend fun getUpdatePokemonList(callback: suspend (ArrayList<PokemonDetails>) -> Unit) {
        if (offsetStatic <= ALL_POKEMON_LIMIT) {
            val localPokemon = provideRealmRepository.getPokemonCount()
            if (localPokemon <= offsetStatic) {
                getPokemonListFromService(offsetStatic)
                callback(getPokemonFromRealm(offsetStatic, offsetStatic + POKEMON_LIMIT))

            } else {
                callback(getPokemonFromRealm(offsetStatic, offsetStatic + POKEMON_LIMIT))
            }
        }
    }

    private suspend fun getPokemonList(callback: suspend (ArrayList<PokemonDetails>) -> Unit) {
        val localPokemon = provideRealmRepository.getPokemonCount()
        when (localPokemon) {
            0 -> {
                getPokemonListFromService(0)
                callback(getPokemonFromRealm(0, POKEMON_LIMIT))
            }
            else -> {
                callback(getPokemonFromRealm(0, POKEMON_LIMIT))
            }
        }
    }

    private suspend fun getPokemonListFromService(offset: Int) {
        when (val pokemonList = provideRepository.getPokemonList(POKEMON_LIMIT, offset)) {
            is DataResult.Success -> {
                pokemonList.data.forEach { pokemon ->
                    val numberPokemon = pokemon.url?.getNumberPokemon()
                    numberPokemon?.let {
                        when (val dataDetail = provideRepository.getPokemonDetail(it)) {
                            is DataResult.Success -> {
                                savePokemonDetail(dataDetail.data)
                            }
                            is DataResult.Error -> {}
                            DataResult.Loading -> {}
                        }
                    }
                }
            }
            is DataResult.Error -> {}
            DataResult.Loading -> {}
        }
    }

    private fun savePokemonDetail(pokemonList: PokemonDetailsService) {
        provideRealmRepository.savePokemonList(pokemonList)
    }

    private suspend fun getPokemonFromRealm(limit: Int, offset: Int): ArrayList<PokemonDetails> {
        return provideRealmRepository.getPokemonList(limit, offset)
    }
}