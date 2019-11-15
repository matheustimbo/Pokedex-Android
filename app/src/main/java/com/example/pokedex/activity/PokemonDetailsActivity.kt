package com.example.pokedex.activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.R
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.services.RetrofitInitializer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var pokemonImagem: ImageView
    private lateinit var pokemonNome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        pokemonNome = findViewById(R.id.pokemon_nome)
        pokemonImagem = findViewById(R.id.pokemon_imagem)

        carregarPokemon(intent.getIntExtra("pokemon_position", 0) + 1)
    }

    fun carregarPokemon(pokemonNumber: Int) {
        val call = RetrofitInitializer.pokeApi.pokeService().pokemon(pokemonNumber.toString())

        call.enqueue(object: Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let {
                    pokemonNome.text = it.name
                    Picasso.get().load(it.sprites.front_default).into(pokemonImagem)

                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }
}
