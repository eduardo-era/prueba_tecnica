package com.example.myapplication.data.repositories.realm

import com.example.myapplication.data.DataResult
import com.example.myapplication.data.database.dao.PokemonDetailDao
import com.example.myapplication.data.database.save.PokemonSaveRealm
import com.example.myapplication.data.database.transform.PokemonDetailsTransform
import com.example.myapplication.data.api.response.PokemonDetailsService
import com.example.myapplication.data.models.PokemonDetails

class RealmImpl(private val pokemonDetailDao: PokemonDetailDao, private val pokemonSaveRealm: PokemonSaveRealm) : RealmRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): ArrayList<PokemonDetails> {
        val pokemonList = pokemonDetailDao.getPokemonList(limit, offset)
        val transform = PokemonDetailsTransform.transform(pokemonList)
        return transform
    }

    override suspend fun getPokemonCount(): Int {
        val count = pokemonDetailDao.countPokemons()
        return count.toInt()
    }

    override fun savePokemonList(pokemonDetail: PokemonDetailsService) {
        pokemonSaveRealm.invoke(pokemonDetail)
    }

    override fun setFavorite(pokemonDetails: PokemonDetails, checked: Boolean) {
        pokemonDetailDao.setFavorite(pokemonDetails.id, checked)
    }

    override fun getFavoritesPokemon(): ArrayList<PokemonDetails>? {
        val pokemonList = pokemonDetailDao.getFavoritesPokemon()
        val transform = pokemonList?.let { PokemonDetailsTransform.transform(it) }
        return transform
    }
}