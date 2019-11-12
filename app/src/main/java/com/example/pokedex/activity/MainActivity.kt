package com.example.pokedex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.adapter.PokeAdapter
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.services.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var pokeList: RecyclerView
    private lateinit var pokemon: List<Pokemon>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        pokeList = findViewById(R.id.main_recyclerview)
        pokeList.layoutManager = LinearLayoutManager(this)




        val adapter = PokeAdapter(this, populetePokemon(1))

        pokeList.adapter = adapter
    }

    fun populetePokemon(index: Int): List<Pokemon> {

        pokemon = listOf(
            getPokemon(index),
            getPokemon(index + 1),
            getPokemon(index + 2),
            getPokemon(index + 3),
            getPokemon(index + 4),
            getPokemon(index + 5),
            getPokemon(index + 6),
            getPokemon(index + 7),
            getPokemon(index + 8),
            getPokemon(index + 9)
        )

        return pokemon
    }

    fun getPokemon(index: Int): Pokemon {
        val call = RetrofitInitializer.pokeApi.pokeService().pokemon(index)
        lateinit var poke: Pokemon

        call.enqueue(object: Callback<Pokemon?> {
            override fun onResponse(call: Call<Pokemon?>?, response: Response<Pokemon?>?) {
                response?.body()?.let {
                    poke = it
                }
            }

            override fun onFailure(call: Call<Pokemon?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })

        return poke
    }
}
