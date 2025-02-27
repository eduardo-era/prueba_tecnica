package com.example.myapplication.ui.favorites

import com.example.myapplication.data.models.PokemonDetails

sealed class FavoritesUiState {
    data object Start : FavoritesUiState()
    data object Loading : FavoritesUiState()

    data class SetFavoritesList(val data: ArrayList<PokemonDetails>) : FavoritesUiState()
}