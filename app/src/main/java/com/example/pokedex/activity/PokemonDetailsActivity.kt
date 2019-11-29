package com.example.pokedex.activity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginEnd
import androidx.core.view.marginRight
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.example.pokedex.R
import com.example.pokedex.adapter.ViewPageAdapter
import com.example.pokedex.fragments.FragmentEvolution
import com.example.pokedex.fragments.FragmentMoves
import com.example.pokedex.fragments.FragmentStatus
import com.example.pokedex.modal.Name_Url
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.services.RetrofitInitializer
import com.example.pokedex.util.Type
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailsActivity : AppCompatActivity(), FragmentStatus.OnFragmentInteractionListener, FragmentMoves.OnFragmentInteractionListener, FragmentEvolution.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
    }

    private var pokemonId = 0
    private lateinit var pokemonImagem: ImageView
    private lateinit var pokemonNome: TextView
    private lateinit var pokeId: TextView
    private lateinit var tabs: TabLayout
    private lateinit var status: FragmentStatus
    private lateinit var viewPage: ViewPager
    private lateinit var viewpageadapter: ViewPageAdapter
    private lateinit var constraintLayout: ConstraintLayout


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        pokemonId = intent.getIntExtra("pokemon_position", 0) + 1
        pokemonNome = findViewById(R.id.pokemon_nome)
        pokemonImagem = findViewById(R.id.pokemon_imagem)
        constraintLayout = findViewById(R.id.constraintLayout_details)
        pokeId = findViewById(R.id.poke_id)
        pokeId.text = pokemonNumber(pokemonId)

        carregarPokemon(pokemonId)

        tabs = findViewById(R.id.details_poketab)
        viewPage = findViewById(R.id.viewpage_details)

        viewpageadapter = ViewPageAdapter(supportFragmentManager, pokemonId)
        viewPage.adapter = viewpageadapter

        tabs.setupWithViewPager(viewPage)

    }

    fun carregarPokemon(pokemonNumber: Int) {
        val call = RetrofitInitializer.pokeApi.pokeService().pokemon(pokemonNumber.toString())

        call.enqueue(object: Callback<Pokemon> {
            @SuppressLint("DefaultLocale")
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let {
                    pokemonNome.text = it.name
                    Picasso.get().load(it.sprites.front_default).into(pokemonImagem)

                    if(it.types.size != 1) {
                        constraintLayout.setBackgroundResource(Type.valueOf(it.types[1].type.name.toUpperCase()).getColor())
                    } else {
                        constraintLayout.setBackgroundResource(Type.valueOf(it.types[0].type.name.toUpperCase()).getColor())
                    }

                    it.types.forEach { type ->
                        findViewById<LinearLayout>(Type.valueOf(type.type.name.toUpperCase()).getLinearLayout()).visibility = View.VISIBLE

                    }
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

    fun pokemonNumber(number: Int): String {
        if (number < 10) {
            return "#00$number"
        } else if (number < 100) {
            return "#0$number"
        } else {
            return "#$number"
        }
    }
}
