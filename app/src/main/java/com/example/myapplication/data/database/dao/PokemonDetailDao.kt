package com.example.myapplication.data.database.dao

import android.content.Context
import com.example.myapplication.data.api.response.PokemonDetailsService
import com.example.myapplication.data.database.RealmDaoBase
import com.example.myapplication.data.database.RealmProvider
import com.example.myapplication.data.database.models.PokemonDetailsRealm
import io.realm.RealmResults

class PokemonDetailDao(var context: Context) : RealmDaoBase<PokemonDetailsRealm>(RealmProvider.getInstanceByCurrentUser(context)) {

    fun findById(id: Int): PokemonDetailsRealm? {
        return db.where(PokemonDetailsRealm::class.java)
            .equalTo("id", id)
            .findFirst()
    }

    fun getPokemonList(startIndex: Int, endIndex: Int): MutableList<PokemonDetailsRealm> {
        return db.where(PokemonDetailsRealm::class.java)
            .findAll()
            .subList(startIndex, endIndex)
    }

    fun countPokemons(): Long {
        return db.where(PokemonDetailsRealm::class.java).count()
    }

    fun getFavoritesPokemon(): RealmResults<PokemonDetailsRealm>? {
        return db.where(PokemonDetailsRealm::class.java)
            .equalTo("isFavorite", true)
            .findAll()
    }

    fun firstCreate(pokemon: PokemonDetailsService) {
        try {
            db.beginTransaction()

            val localPokemon = db.createObject(PokemonDetailsRealm::class.java, pokemon.id)

            localPokemon.name = pokemon.name
            localPokemon.height = pokemon.height
            localPokemon.weight = pokemon.weight
            localPokemon.sprites = pokemon.sprites?.frontDefault
            localPokemon.types = pokemon.types?.get(0)?.type?.name
            localPokemon.isFavorite = false

            db.commitTransaction()

        } catch (e: Throwable) {

            if (db.isInTransaction) {
                db.cancelTransaction()
            }
            throw e
        }
    }

    fun setFavorite(id: Int?, checked: Boolean) {
        try {
            db.beginTransaction()
            db.where(PokemonDetailsRealm::class.java)
                .equalTo("id", id)
                .findFirst()?.isFavorite = checked
            db.commitTransaction()

        } catch (e: Throwable) {
            if (db.isInTransaction) {
                db.cancelTransaction()
            }
            throw e
        }
    }


}