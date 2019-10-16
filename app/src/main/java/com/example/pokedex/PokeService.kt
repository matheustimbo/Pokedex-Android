package com.example.pokedex

import android.provider.ContactsContract
import retrofit2.Call
import retrofit2.http.GET

interface PokeService {
    @GET("results")
    fun pokemonList(): Call<List<ContactsContract.CommonDataKinds.Note>>
}