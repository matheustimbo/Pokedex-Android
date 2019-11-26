package com.example.pokedex.activity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailsActivity : AppCompatActivity(), FragmentStatus.OnFragmentInteractionListener, FragmentMoves.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
    }

    private var pokemonId = 0
    private lateinit var pokemonImagem: ImageView
    private lateinit var pokemonNome: TextView
    private lateinit var pokemonType: ChipGroup
    private lateinit var tabs: TabLayout
    private lateinit var status: FragmentStatus
    private lateinit var viewPage: ViewPager
    private lateinit var viewpageadapter: ViewPageAdapter
    private lateinit var constraintLayout: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        pokemonId = intent.getIntExtra("pokemon_position", 0) + 1
        pokemonNome = findViewById(R.id.pokemon_nome)
        pokemonImagem = findViewById(R.id.pokemon_imagem)
        pokemonType = findViewById(R.id.pokemon_types)
        constraintLayout = findViewById(R.id.constraintLayout_details)

        carregarPokemon(pokemonId)

        tabs = findViewById(R.id.details_poketab)
        viewPage = findViewById(R.id.viewpage_details)

        viewpageadapter = ViewPageAdapter(supportFragmentManager, pokemonId)
        viewPage.adapter = viewpageadapter


//        viewPage.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {
//            }
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//            }
//
//            override fun onPageSelected(position: Int) {
//                System.out.println(position)
//
//            }
//
//        })

        tabs.setupWithViewPager(viewPage)

    }

    fun carregarPokemon(pokemonNumber: Int) {
        val call = RetrofitInitializer.pokeApi.pokeService().pokemon(pokemonNumber.toString())

        call.enqueue(object: Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let {
                    pokemonNome.text = it.name
                    Picasso.get().load(it.sprites.front_default).into(pokemonImagem)

                    if(it.types.size !== 1) {
                        constraintLayout.setBackgroundResource(Type.valueOf(it.types[1].type.name.toUpperCase()).getColor())
                    } else {
                        constraintLayout.setBackgroundResource(Type.valueOf(it.types[0].type.name.toUpperCase()).getColor())
                    }

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
        type.text = info.name
//        val image = ImageView(this)


//        image.setImageResource(Type.valueOf(info.name.toUpperCase()).getImagem())
//        image.scaleX = .1F
//        image.scaleY = .1F
//
//        content.addView(image)
        content.addView(type)

        pokemonType.addView(content)
    }
}
