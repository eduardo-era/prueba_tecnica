package com.example.myapplication.domain

import com.example.myapplication.data.models.PokemonDetails
import com.example.myapplication.data.repositories.realm.RealmRepository

class SetFavoritePokemonUseCase(private val provideRealmRepository: RealmRepository) {

    operator fun invoke(pokemonDetails: PokemonDetails, checked: Boolean) {
        provideRealmRepository.setFavorite(pokemonDetails, checked)
    }
}