package com.example.pokedex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.adapter.PokeAdapter
import com.example.pokedex.modal.ListPokemon
import com.example.pokedex.services.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var pokeList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokeList = findViewById(R.id.main_recyclerview)
        pokeList.layoutManager = LinearLayoutManager(this)

        val adapter = PokeAdapter(this)

        pokeList.adapter = adapter

        val call = RetrofitInitializer.pokeApi.pokeService().ListPokemon(0)

        call.enqueue(object: Callback<ListPokemon> {
            override fun onResponse(call: Call<ListPokemon>, response: Response<ListPokemon>) {
                response.body()?.let {
                    adapter.updateList(it.results)
                }
            }

            override fun onFailure(call: Call<ListPokemon>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }
}
