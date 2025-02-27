package com.example.myapplication.data.database.save

import com.example.myapplication.data.database.dao.PokemonDetailDao
import com.example.myapplication.data.database.models.PokemonDetailsRealm
import com.example.myapplication.data.api.response.PokemonDetailsService

class PokemonSaveRealm(private val surveyDao: PokemonDetailDao) {
    operator fun invoke(pokemon: PokemonDetailsService) {

        val surveyLocal = pokemon.id?.let { getPokemon(it) }
        if (surveyLocal == null){
            surveyDao.firstCreate(pokemon)

        }else{
            surveyLocal.let {
                it.name = pokemon.name
                it.height = pokemon.height
                it.weight = pokemon.weight
                it.sprites = pokemon.sprites?.frontDefault
                it.types = pokemon.types?.get(0)?.type?.name
                it.isFavorite = pokemon.isFavorite

                surveyDao.copyOrUpdate(it)
            }
        }
    }

    private fun getPokemon(id: Int): PokemonDetailsRealm? {
        return surveyDao.findById(id)?.let {
            PokemonDetailsRealm.deepCopy(it)
        }
    }
}