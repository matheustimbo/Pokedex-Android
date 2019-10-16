package com.example.pokedex

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.23:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun pokeService() = retrofit.create(PokeService::class.java)

}