package com.example.myapplication.ui.pokemon

import com.example.myapplication.data.models.PokemonDetails
import java.util.ArrayList

sealed class PokemonUiState {
    data object Start: PokemonUiState()
    data object Loading: PokemonUiState()
    data object HideLoading : PokemonUiState()

    data class SetPokemonList(val data: ArrayList<PokemonDetails>) : PokemonUiState()
    data class UpdatePokemonList(val data: ArrayList<PokemonDetails>) : PokemonUiState()
    data class Error(val update: Boolean) : PokemonUiState()
}