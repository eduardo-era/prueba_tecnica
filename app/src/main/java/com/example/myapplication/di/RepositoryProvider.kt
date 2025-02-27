package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.database.dao.PokemonDetailDao
import com.example.myapplication.data.database.save.PokemonSaveRealm
import com.example.myapplication.data.repositories.realm.RealmImpl
import com.example.myapplication.data.repositories.realm.RealmRepository
import com.example.myapplication.data.repositories.services.ServiceImpl
import com.example.myapplication.data.repositories.services.ServiceRepository

object RepositoryProvider {
    fun provideServiceRepository(): ServiceRepository = with(DataProvider) {
        ServiceImpl(provideSurveyApiService())
    }

    fun provideRealmRepository(appContext: Context): RealmRepository = RealmImpl(
        PokemonDetailDao(appContext),
        PokemonSaveRealm(PokemonDetailDao(appContext))
    )
}