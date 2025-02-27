package com.example.myapplication.di

import com.example.myapplication.data.api.Services
import com.example.myapplication.data.database.RealmDatabase

object DataProvider {

    fun provideSurveyApiService(): Services =
        ApiServiceProvider.buildApiService(
            Services::class.java,
            "https://pokeapi.co/api/v2/",
            10
        )
}