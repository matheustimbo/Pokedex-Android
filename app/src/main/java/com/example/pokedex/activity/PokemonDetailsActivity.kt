package com.example.pokedex.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginEnd
import androidx.core.view.marginRight
import com.example.pokedex.R
import com.example.pokedex.modal.Name_Url
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.services.RetrofitInitializer
import com.example.pokedex.util.Type
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var pokemonImagem: ImageView
    private lateinit var pokemonNome: TextView
    private lateinit var pokemonType: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        pokemonNome = findViewById(R.id.pokemon_nome)
        pokemonImagem = findViewById(R.id.pokemon_imagem)
        pokemonType = findViewById(R.id.list_type)

        carregarPokemon(intent.getIntExtra("pokemon_position", 0) + 1)
    }

    fun carregarPokemon(pokemonNumber: Int) {
        val call = RetrofitInitializer.pokeApi.pokeService().pokemon(pokemonNumber.toString())

        call.enqueue(object: Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let {
                    pokemonNome.text = it.name
                    Picasso.get().load(it.sprites.front_default).into(pokemonImagem)

                    for (type in it.types) {
                        createType(type.type)
                    }
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

    @SuppressLint("DefaultLocale")
    fun createType(info: Name_Url) {
        val content = ConstraintLayout(this)
        val type = TextView(this)
        val image = ImageView(this)

        type.text = info.name

        image.setImageResource(Type.valueOf(info.name.toUpperCase()).getImagem())
        image.scaleX = .1F
        image.scaleY = .1F

        content.addView(image)
        content.addView(type)

        pokemonType.addView(content)
    }
}
