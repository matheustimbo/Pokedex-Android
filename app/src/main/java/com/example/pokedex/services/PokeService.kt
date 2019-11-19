package com.example.pokedex.services

import com.example.pokedex.modal.ListPokemon
import com.example.pokedex.modal.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {
    @GET("pokemon")
    fun ListPokemon(@Query("offset") offset: Int = 0, @Query("limit") limit: Int = 20): Call<ListPokemon>

    @GET("pokemon/{pokemon}")
    fun pokemon(@Path("pokemon") pokemon: String): Call<Pokemon>
}