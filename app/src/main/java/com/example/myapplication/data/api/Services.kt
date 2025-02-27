package com.example.myapplication.data.api

import com.example.myapplication.data.api.response.PokemonResponse
import com.example.myapplication.data.api.response.PokemonDetailsService
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Services {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offSet: Int): Response<PokemonResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: Int): Response<PokemonDetailsService>
}