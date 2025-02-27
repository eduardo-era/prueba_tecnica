package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.ui.favorites.FavoritesViewModel
import com.example.myapplication.ui.pokemon.PokemonViewModel

object ModuleViewModelProvider {
    fun providePokemonViewModel(appContext: Context) = with(UseCaseProvider) {
        PokemonViewModel(
            provideGetPokemonListUseCase(appContext),
            provideSetFavoritePokemonUseCase(appContext)
        )
    }

    fun provideFavoritesViewModel(appContext: Context) = with(UseCaseProvider) {
        FavoritesViewModel(
            provideGetFavoritesPokemonUseCase(appContext)
        )
    }
}