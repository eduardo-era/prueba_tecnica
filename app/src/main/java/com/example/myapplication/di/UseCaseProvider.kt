package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.domain.GetFavoritesPokemonUseCase
import com.example.myapplication.domain.GetPokemonListUseCase
import com.example.myapplication.domain.SetFavoritePokemonUseCase

object UseCaseProvider {

    fun provideGetPokemonListUseCase(appContext: Context) = GetPokemonListUseCase(
        RepositoryProvider.provideServiceRepository(),
        RepositoryProvider.provideRealmRepository(appContext)
    )

    fun provideSetFavoritePokemonUseCase(appContext: Context) = SetFavoritePokemonUseCase(
        RepositoryProvider.provideRealmRepository(appContext)
    )

    fun provideGetFavoritesPokemonUseCase(appContext: Context) = GetFavoritesPokemonUseCase(
        RepositoryProvider.provideRealmRepository(appContext)
    )
}