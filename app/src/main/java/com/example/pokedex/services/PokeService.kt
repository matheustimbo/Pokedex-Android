package com.example.pokedex.services

import com.example.pokedex.modal.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {
//    @GET("/pokemon/{pokemon}")
//    fun pokemon(@Path("pokemon") pokemon: String): Call<Pokemon>

    @GET("/posts/1")
    fun pokemon(): Call<Pokemon>
}